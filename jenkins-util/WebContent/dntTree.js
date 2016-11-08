treeJSON = d3.json("testcase.json", function(error, treeData) {

	var totalNodes = 0;
	var maxLabelLength = 0;
	var selectedNode = null;
	var draggingNode = null;
	var panSpeed = 200;
	var panBoundary = 20;
	var i = 0;
	var duration = 750;
	var root;

	var viewerWidth = $(document).width();
	var viewerHeight = $(document).height();

	var tree = d3.layout.tree().size([ viewerHeight, viewerWidth ]);

	var diagonal = d3.svg.diagonal().projection(function(d) {
		return [ d.y, d.x ];
	});

	function visit(parent, visitFn, childrenFn) {
		if (!parent)
			return;

		visitFn(parent);

		var children = childrenFn(parent);
		if (children) {
			var count = children.length;
			for (var i = 0; i < count; i++) {
				visit(children[i], visitFn, childrenFn);
			}
		}
	}

	visit(treeData, function(d) {
		totalNodes++;
		maxLabelLength = Math.max(d.name.length, maxLabelLength);

	}, function(d) {
		return d.children && d.children.length > 0 ? d.children : null;
	});

	function sortTree() {
		tree.sort(function(a, b) {
			return b.name.toLowerCase() < a.name.toLowerCase() ? 1 : -1;
		});
	}
	sortTree();

	function pan(domNode, direction) {
		var speed = panSpeed;
		if (panTimer) {
			clearTimeout(panTimer);
			translateCoords = d3.transform(svgGroup.attr("transform"));
			if (direction == 'left' || direction == 'right') {
				translateX = direction == 'left' ? translateCoords.translate[0]
						+ speed : translateCoords.translate[0] - speed;
				translateY = translateCoords.translate[1];
			} else if (direction == 'up' || direction == 'down') {
				translateX = translateCoords.translate[0];
				translateY = direction == 'up' ? translateCoords.translate[1]
						+ speed : translateCoords.translate[1] - speed;
			}
			scaleX = translateCoords.scale[0];
			scaleY = translateCoords.scale[1];
			scale = zoomListener.scale();
			svgGroup.transition().attr(
					"transform",
					"translate(" + translateX + "," + translateY + ")scale("
							+ scale + ")");
			d3.select(domNode).select('g.node').attr("transform",
					"translate(" + translateX + "," + translateY + ")");
			zoomListener.scale(zoomListener.scale());
			zoomListener.translate([ translateX, translateY ]);
			panTimer = setTimeout(function() {
				pan(domNode, speed, direction);
			}, 50);
		}
	}

	function zoom() {
		svgGroup.attr("transform", "translate(" + d3.event.translate
				+ ")scale(" + d3.event.scale + ")");
	}

	var zoomListener = d3.behavior.zoom().scaleExtent([ 0.1, 3 ]).on("zoom",
			zoom);

	function initiateDrag(d, domNode) {
		draggingNode = d;
		d3.select(domNode).select('.ghostCircle')
				.attr('pointer-events', 'none');
		d3.selectAll('.ghostCircle').attr('class', 'ghostCircle show');
		d3.select(domNode).attr('class', 'node activeDrag');

		svgGroup.selectAll("g.node").sort(function(a, b) {
			if (a.id != draggingNode.id)
				return 1;
			else
				return -1;
		});
		if (nodes.length > 1) {
			links = tree.links(nodes);
			nodePaths = svgGroup.selectAll("path.link").data(links,
					function(d) {
						return d.target.id;
					}).remove();
			nodesExit = svgGroup.selectAll("g.node").data(nodes, function(d) {
				return d.id;
			}).filter(function(d, i) {
				if (d.id == draggingNode.id) {
					return false;
				}
				return true;
			}).remove();
		}

		parentLink = tree.links(tree.nodes(draggingNode.parent));
		svgGroup.selectAll('path.link').filter(function(d, i) {
			if (d.target.id == draggingNode.id) {
				return true;
			}
			return false;
		}).remove();

		dragStarted = null;
	}

	var baseSvg = d3.select("#tree-container").append("svg").attr("width",
			viewerWidth).attr("height", viewerHeight).attr("class", "overlay")
			.call(zoomListener);

	dragListener = d3.behavior.drag().on("dragstart", function(d) {
		if (d == root) {
			return;
		}
		dragStarted = true;
		nodes = tree.nodes(d);
		d3.event.sourceEvent.stopPropagation();
	}).on("drag", function(d) {
		if (d == root) {
			return;
		}
		if (dragStarted) {
			domNode = this;
			initiateDrag(d, domNode);
		}

		relCoords = d3.mouse($('svg').get(0));
		if (relCoords[0] < panBoundary) {
			panTimer = true;
			pan(this, 'left');
		} else if (relCoords[0] > ($('svg').width() - panBoundary)) {

			panTimer = true;
			pan(this, 'right');
		} else if (relCoords[1] < panBoundary) {
			panTimer = true;
			pan(this, 'up');
		} else if (relCoords[1] > ($('svg').height() - panBoundary)) {
			panTimer = true;
			pan(this, 'down');
		} else {
			try {
				clearTimeout(panTimer);
			} catch (e) {

			}
		}

		d.x0 += d3.event.dy;
		d.y0 += d3.event.dx;
		var node = d3.select(this);
		node.attr("transform", "translate(" + d.y0 + "," + d.x0 + ")");
		updateTempConnector();
	}).on(
			"dragend",
			function(d) {
				if (d == root) {
					return;
				}
				domNode = this;
				if (selectedNode) {
					var index = draggingNode.parent.children
							.indexOf(draggingNode);
					if (index > -1) {
						draggingNode.parent.children.splice(index, 1);
					}
					if (typeof selectedNode.children !== 'undefined'
							|| typeof selectedNode._children !== 'undefined') {
						if (typeof selectedNode.children !== 'undefined') {
							selectedNode.children.push(draggingNode);
						} else {
							selectedNode._children.push(draggingNode);
						}
					} else {
						selectedNode.children = [];
						selectedNode.children.push(draggingNode);
					}
					expand(selectedNode);
					sortTree();
					endDrag();
				} else {
					endDrag();
				}
			});

	function endDrag() {
		selectedNode = null;
		d3.selectAll('.ghostCircle').attr('class', 'ghostCircle');
		d3.select(domNode).attr('class', 'node');
		d3.select(domNode).select('.ghostCircle').attr('pointer-events', '');
		updateTempConnector();
		if (draggingNode !== null) {
			update(root);
			centerNode(draggingNode);
			draggingNode = null;
		}
	}

	function collapse(d) {
		if (d.children) {
			d._children = d.children;
			d._children.forEach(collapse);
			d.children = null;
		}
	}

	function expand(d) {
		if (d._children) {
			d.children = d._children;
			d.children.forEach(expand);
			d._children = null;
		}
	}

	var overCircle = function(d) {
		selectedNode = d;
		updateTempConnector();
	};
	var outCircle = function(d) {
		selectedNode = null;
		updateTempConnector();
	};

	var updateTempConnector = function() {
		var data = [];
		if (draggingNode !== null && selectedNode !== null) {
			data = [ {
				source : {
					x : selectedNode.y0,
					y : selectedNode.x0
				},
				target : {
					x : draggingNode.y0,
					y : draggingNode.x0
				}
			} ];
		}
		var link = svgGroup.selectAll(".templink").data(data);

		link.enter().append("path").attr("class", "templink").attr("d",
				d3.svg.diagonal()).attr('pointer-events', 'none');

		link.attr("d", d3.svg.diagonal());

		link.exit().remove();
	};

	function centerNode(source) {
		scale = zoomListener.scale();
		x = -source.y0;
		y = -source.x0;
		x = x * scale + viewerWidth / 2;
		y = y * scale + viewerHeight / 2;
		d3.select('g').transition().duration(duration).attr("transform",
				"translate(" + x + "," + y + ")scale(" + scale + ")");
		zoomListener.scale(scale);
		zoomListener.translate([ x, y ]);
	}

	function toggleChildren(d) {
		if (d.children) {
			d._children = d.children;
			d.children = null;
		} else if (d._children) {
			d.children = d._children;
			d._children = null;
		}
		return d;
	}

	function click(d) {
		if (d3.event.defaultPrevented)
			return; // click suppressed
		d = toggleChildren(d);
		update(d);
		centerNode(d);
	}

	function update(source) {
		var levelWidth = [ 1 ];
		var childCount = function(level, n) {

			if (n.children && n.children.length > 0) {
				if (levelWidth.length <= level + 1)
					levelWidth.push(0);

				levelWidth[level + 1] += n.children.length;
				n.children.forEach(function(d) {
					childCount(level + 1, d);
				});
			}
		};
		childCount(0, root);
		var newHeight = d3.max(levelWidth) * 25;
		tree = tree.size([ newHeight, viewerWidth ]);

		var nodes = tree.nodes(root).reverse(), links = tree.links(nodes);

		nodes.forEach(function(d) {
			d.y = (d.depth * (maxLabelLength * 10));
		});

		node = svgGroup.selectAll("g.node").data(nodes, function(d) {
			return d.id || (d.id = ++i);
		});

		var nodeEnter = node.enter().append("g").call(dragListener).attr(
				"class", "node").attr("transform", function(d) {
			return "translate(" + source.y0 + "," + source.x0 + ")";
		}).on('click', click);

		nodeEnter.append("circle").attr('class', 'nodeCircle').attr("r", 0)
				.style("fill", function(d) {
					return d._children ? "lightsteelblue" : "#fff";
				});

		nodeEnter.append("text").attr("x", function(d) {
			return d.children || d._children ? -10 : 10;
		}).attr("dy", ".35em").attr('class', 'nodeText').attr("text-anchor",
				function(d) {
					return d.children || d._children ? "end" : "start";
				}).text(function(d) {
			return d.name;
		}).style("fill-opacity", 0);

		nodeEnter.append("circle").attr('class', 'ghostCircle').attr("r", 30)
				.attr("opacity", 0.2).style("fill", "red").attr(
						'pointer-events', 'mouseover').on("mouseover",
						function(node) {
							overCircle(node);
						}).on("mouseout", function(node) {
					outCircle(node);
				});

		node.select('text').attr("x", function(d) {
			return d.children || d._children ? -10 : 10;
		}).attr("text-anchor", function(d) {
			return d.children || d._children ? "end" : "start";
		}).text(function(d) {
			return d.name;
		});

		node.select("circle.nodeCircle").attr("r", 4.5).style("fill",
				function(d) {
					return d._children ? "lightsteelblue" : "#fff";
				});

		var nodeUpdate = node.transition().duration(duration).attr("transform",
				function(d) {
					return "translate(" + d.y + "," + d.x + ")";
				});

		nodeUpdate.select("text").style("fill-opacity", 1);

		var nodeExit = node.exit().transition().duration(duration).attr(
				"transform", function(d) {
					return "translate(" + source.y + "," + source.x + ")";
				}).remove();

		nodeExit.select("circle").attr("r", 0);

		nodeExit.select("text").style("fill-opacity", 0);

		var link = svgGroup.selectAll("path.link").data(links, function(d) {
			return d.target.id;
		});

		link.enter().insert("path", "g").attr("class", "link").attr("d",
				function(d) {
					var o = {
						x : source.x0,
						y : source.y0
					};
					return diagonal({
						source : o,
						target : o
					});
				});

		link.transition().duration(duration).attr("d", diagonal);

		link.exit().transition().duration(duration).attr("d", function(d) {
			var o = {
				x : source.x,
				y : source.y
			};
			return diagonal({
				source : o,
				target : o
			});
		}).remove();

		nodes.forEach(function(d) {
			d.x0 = d.x;
			d.y0 = d.y;
		});
	}

	var svgGroup = baseSvg.append("g");

	root = treeData;
	root.x0 = viewerHeight / 2;
	root.y0 = 0;

	update(root);
	centerNode(root);
});