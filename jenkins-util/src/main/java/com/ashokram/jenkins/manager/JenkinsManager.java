package com.ashokram.jenkins.manager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ashokram.jenkins.constants.JenkinsConstants;
import com.ashokram.jenkins.constants.JenkinsStaticStore;
import com.ashokram.jenkins.constants.JsonBuilder;
import com.ashokram.jenkins.pojo.JsonDataChildTO;
import com.ashokram.jenkins.pojo.JsonDataChildrenTO;
import com.ashokram.jenkins.pojo.JsonDataImediateChildrenTO;
import com.ashokram.jenkins.pojo.JsonDataTO;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.MainView;

/**
 * The Class JenkinsManager.
 *
 * @author Ashok Ram. G
 * @since 1.0
 */
public class JenkinsManager
{

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(JenkinsManager.class);

	/**
	 * Gets the jenkins details.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param url
	 *            the url
	 * @param type
	 *            the type
	 * @return the jenkins details
	 * @throws Exception
	 *             the exception
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	public void getJenkinsDetails(String userName, String password, String url, String type) throws Exception {
		if (JenkinsConstants.JOB.equals(type)) {
			processClient(url, userName, password);
		} else if (JenkinsConstants.DASHBOARD.equals(type)) {
			Map<String, Job> jobs = getJobs(url, userName, password);
			for (Map.Entry<String, Job> job : jobs.entrySet()) {
				Job jb = job.getValue();
				processClient(jb.getUrl(), userName, password);
			}
		}
	}

	/**
	 * Gets the json data.
	 *
	 * @return the json data
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	@SuppressWarnings("unchecked")
	public JsonDataTO getJsonData() {
		JsonDataTO jsonDataTO = new JsonDataTO();
		if (JenkinsStaticStore.RESULT_MAP != null && JenkinsStaticStore.RESULT_MAP.size() > 0) {
			JsonBuilder jsonBuilder = new JsonBuilder();
			jsonDataTO.setName(JenkinsConstants.JsonDataConstants.ROOT_NODE_NAME);
			for (Entry<String, Object> resultMap : JenkinsStaticStore.RESULT_MAP.entrySet()) {
				JsonDataChildrenTO jsonDataChildrenTO = new JsonDataChildrenTO();
				if (resultMap.getValue() instanceof Set) {
					jsonDataChildrenTO.setName(resultMap.getKey());
					Set<String> testCases = (Set<String>) resultMap.getValue();
					if (null != testCases && testCases.size() > 0) {
						for (String testCase : testCases) {
							JsonDataImediateChildrenTO jsonDataImediateChildrenTO = new JsonDataImediateChildrenTO();
							jsonDataImediateChildrenTO.setName(testCase);
							jsonDataImediateChildrenTO.setSize(jsonBuilder.getSize());
							jsonDataChildrenTO.setChild(jsonDataImediateChildrenTO);
						}
					}
				} else if (resultMap.getValue() instanceof Map) {
					jsonDataChildrenTO.setName(resultMap.getKey());
					Map<String, Set<String>> resultReasonMap = (Map<String, Set<String>>) resultMap.getValue();
					for (Entry<String, Set<String>> resultReasons : resultReasonMap.entrySet()) {
						JsonDataImediateChildrenTO jsonDataImediateChildrenTO = new JsonDataImediateChildrenTO();
						jsonDataImediateChildrenTO.setName(resultReasons.getKey());
						for (String testCase : resultReasons.getValue()) {
							JsonDataChildTO jsonDataChildTO = new JsonDataChildTO();
							jsonDataChildTO.setName(testCase);
							jsonDataChildTO.setSize(jsonBuilder.getSize());
							jsonDataImediateChildrenTO.setChild(jsonDataChildTO);
						}
						jsonDataChildrenTO.setChild(jsonDataImediateChildrenTO);
					}
				}
				jsonDataTO.setChild(jsonDataChildrenTO);
			}
		}
		return jsonDataTO;
	}

	/**
	 * Gets the jobs.
	 *
	 * @param url
	 *            the url
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @return the jobs
	 * @throws URISyntaxException
	 *             the URI syntax exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private Map<String, Job> getJobs(String url, String userName, String password) throws URISyntaxException, IOException {
		JenkinsHttpClient client = new JenkinsHttpClient(new URI(url), userName, password);
		List<Job> jobs = client.get("/", MainView.class).getJobs();
		return Maps.uniqueIndex(jobs, new Function<Job, String>() {
			@Override
			public String apply(Job job) {
				job.setClient(client);
				return job.getName();
			}
		});
	}

	/**
	 * Validate and process.
	 *
	 * @param type
	 *            the type
	 * @param jobURL
	 *            the job URL
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @return the map
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	public Map<String, Object> validateAndProcess(String type, String jobURL, String userName, String password) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		if (JenkinsConstants.JOB.equals(type)) {
			try {
				JenkinsHttpClient client = new JenkinsHttpClient(new URI(jobURL), userName, password);
				client.get(jobURL, JobWithDetails.class);
			} catch (Exception e) {
				log.error("Error in validating the jenkins link", e);
				errorMap.put(jobURL, e.getStackTrace());
			}
		} else if (JenkinsConstants.DASHBOARD.equals(type)) {
			try {
				getJobs(jobURL, userName, password);
			} catch (Exception e) {
				log.error("Error in validating the jenkins link", e);
				errorMap.put(jobURL, e.getStackTrace());
			}
		}
		return errorMap;
	}

	/**
	 * Process client.
	 *
	 * @param jobURL
	 *            the job URL
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @throws Exception
	 *             the exception
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private void processClient(String jobURL, String userName, String password) throws Exception {
		JenkinsHttpClient client = new JenkinsHttpClient(new URI(jobURL), userName, password);
		JobWithDetails details = client.get(jobURL, JobWithDetails.class);
		if (details.getDownstreamProjects() != null && details.getDownstreamProjects().size() > 0) {
			for (Job job : details.getDownstreamProjects()) {
				processClient(job.getUrl(), userName, password);
			}
		}
		digTestAndFillMap(details, jobURL, userName, password);
	}

	/**
	 * Dig test and fill map.
	 *
	 * @param details
	 *            the details
	 * @param jobURL
	 *            the job URL
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @throws Exception
	 *             the exception
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	@SuppressWarnings({ "serial", "unchecked" })
	private void digTestAndFillMap(JobWithDetails details, String jobURL, String userName, String password) throws Exception {
		Build build = details.getLastBuild();
		boolean isDetailsEnabled = true;
		String url = jobURL;
		try {
			JenkinsHttpClient client = new JenkinsHttpClient(new URI(url), userName, password);
			url += "/lastCompletedBuild/testngreports";
			String jsonClient = client.get(url);
			Map<String, Object> resultMap = new Gson().fromJson(jsonClient, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			List<Object> listOfPackages = (List<Object>) resultMap.get("package");
			for (Object detailsOfModules : listOfPackages) {
				Map<String, Object> moduleDetails = (Map<String, Object>) detailsOfModules;
				jsonClient = client.get(url.concat("/").concat(String.valueOf(moduleDetails.get("name"))));
				resultMap = new Gson().fromJson(jsonClient, new TypeToken<HashMap<String, Object>>() {
				}.getType());
				List<Object> listOfClasses = (List<Object>) resultMap.get("classs");
				for (Object detailsOfClasses : listOfClasses) {
					Map<String, Object> classDetails = (Map<String, Object>) detailsOfClasses;
					jsonClient = client.get(url.concat("/").concat(String.valueOf(moduleDetails.get("name"))).concat("/").concat(String.valueOf(classDetails.get("name"))));
					resultMap = new Gson().fromJson(jsonClient, new TypeToken<HashMap<String, Object>>() {
					}.getType());
					List<Object> listOfTestCases = (List<Object>) resultMap.get("test-method");
					for (Object detailsOfTestCase : listOfTestCases) {
						Map<String, Object> testCase = (Map<String, Object>) detailsOfTestCase;
						jsonClient = client.get(url.concat("/").concat(String.valueOf(moduleDetails.get("name"))).concat("/").concat(String.valueOf(classDetails.get("name")).concat("/").concat(String.valueOf(testCase.get("name")))));
						resultMap = new Gson().fromJson(jsonClient, new TypeToken<HashMap<String, Object>>() {
						}.getType());
						fillMapForTestJobs(resultMap);
					}
				}
			}
			isDetailsEnabled = false;
		} catch (Exception e) {
			// Suppress Exception
		}
		if (isDetailsEnabled) {
			try {
				url = jobURL;
				url += "/lastCompletedBuild/testReport";
				JenkinsHttpClient client = new JenkinsHttpClient(new URI(url), userName, password);
				String output = client.get(url);
				Map<String, Object> retMap = new Gson().fromJson(output, new TypeToken<HashMap<String, Object>>() {
				}.getType());
				List<Object> childReorts = (List<Object>) retMap.get("childReports");
				for (Object buildDetailsForTest : childReorts) {
					Map<String, Object> childAndResults = (Map<String, Object>) buildDetailsForTest;
					Map<String, Object> child = (Map<String, Object>) childAndResults.get("child");
					url = String.valueOf(child.get("url")).concat("/testReport");
					output = client.get(url);
					retMap = new Gson().fromJson(output, new TypeToken<HashMap<String, Object>>() {
					}.getType());
					List<Object> suites = (List<Object>) retMap.get("suites");
					for (Object suite : suites) {
						Map<String, Object> childOfSuites = (Map<String, Object>) suite;
						List<Object> cases = (List<Object>) childOfSuites.get("cases");
						for (Object cazes : cases) {
							Map<String, Object> caze = (Map<String, Object>) cazes;
							fillMapForTestNGJobs(caze);
						}
					}
				}
				isDetailsEnabled = false;
			} catch (Exception e) {
				// Suppress Exception
			}
		}
		if (isDetailsEnabled) {
			BuildWithDetails buildWithDetails = build.details();
			fillMap(buildWithDetails);
		}
	}

	/**
	 * Fill map.
	 *
	 * @param buildWithDetails
	 *            the build with details
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	private void fillMap(BuildWithDetails buildWithDetails) {
		if (JenkinsStaticStore.RESULT_MAP != null) {
			String status = null;
			if (buildWithDetails.getResult() != null) {
				switch (buildWithDetails.getResult()) {
					case FAILURE: {
						status = BuildResult.FAILURE.name();
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Map<String, Set<String>> failureTestCases = new HashMap<String, Set<String>>();
							JenkinsStaticStore.RESULT_MAP.put(status, failureTestCases);
						}
						String failureReason = getFailureReason(buildWithDetails);
						Map<String, Set<String>> failureTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
						if (!failureTestCases.containsKey(failureReason)) {
							failureTestCases.put(failureReason, new HashSet<String>());
						}
						Set<String> faliureTestCasesList = (Set<String>) failureTestCases.get(failureReason);
						faliureTestCasesList.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
					case UNSTABLE: {
						status = BuildResult.UNSTABLE.name();
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Map<String, Set<String>> unstableTestCases = new HashMap<String, Set<String>>();
							JenkinsStaticStore.RESULT_MAP.put(status, unstableTestCases);
						}
						String unstableReason = getFailureReason(buildWithDetails);
						Map<String, Set<String>> unstableTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
						if (!unstableTestCases.containsKey(unstableReason)) {
							unstableTestCases.put(unstableReason, new HashSet<String>());
						}
						Set<String> unstableTestCasesList = (Set<String>) unstableTestCases.get(unstableReason);
						unstableTestCasesList.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
					case REBUILDING: {
						status = BuildResult.REBUILDING.name();
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Set<String> rebuildingtestCases = new HashSet<String>();
							JenkinsStaticStore.RESULT_MAP.put(status, rebuildingtestCases);
						}
						Set<String> rebuildingtestCases = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
						rebuildingtestCases.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
					case BUILDING: {
						status = BuildResult.BUILDING.name();
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Set<String> buildingTestCases = new HashSet<String>();
							JenkinsStaticStore.RESULT_MAP.put(status, buildingTestCases);
						}
						Set<String> buildingTestCases = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
						buildingTestCases.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
					case ABORTED: {
						status = BuildResult.ABORTED.name();
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Map<String, Set<String>> abortedTestCases = new HashMap<String, Set<String>>();
							Set<String> abortedTestCasesList = new HashSet<String>();
							abortedTestCases.put(JenkinsConstants.JVM_CRASH, abortedTestCasesList);
							JenkinsStaticStore.RESULT_MAP.put(status, abortedTestCases);
						}
						Map<String, Set<String>> abortedTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
						Set<String> abortedTestCasesList = (Set<String>) abortedTestCases.get(JenkinsConstants.JVM_CRASH);
						abortedTestCasesList.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
					case SUCCESS: {
						status = BuildResult.SUCCESS.name();
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Set<String> testCaseName = new HashSet<String>();
							JenkinsStaticStore.RESULT_MAP.put(status, testCaseName);
						}
						Set<String> testCaseName = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
						testCaseName.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
					case UNKNOWN: {
						status = BuildResult.UNKNOWN.name();
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Set<String> unknownTestCases = new HashSet<String>();
							JenkinsStaticStore.RESULT_MAP.put(status, unknownTestCases);
						}
						Set<String> unknownTestCases = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
						unknownTestCases.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
					case NOT_BUILT: {
						status = BuildResult.NOT_BUILT.name();
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Set<String> notBuiltTestCases = new HashSet<String>();
							JenkinsStaticStore.RESULT_MAP.put(status, notBuiltTestCases);
						}
						Set<String> notBuiltTestCases = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
						notBuiltTestCases.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
					default: {
						status = JenkinsConstants.DEFAULT_REASON;
						if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
							Set<String> otherTestCases = new HashSet<String>();
							JenkinsStaticStore.RESULT_MAP.put(status, otherTestCases);
						}
						Set<String> otherTestCases = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
						otherTestCases.add(buildWithDetails.getFullDisplayName());
						log.info(buildWithDetails.getFullDisplayName());
						break;
					}
				}
			} else {
				status = JenkinsConstants.DEFAULT_REASON;
				if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
					Set<String> otherTestCases = new HashSet<String>();
					JenkinsStaticStore.RESULT_MAP.put(status, otherTestCases);
				}
				Set<String> otherTestCases = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
				otherTestCases.add(buildWithDetails.getFullDisplayName());
				log.info(buildWithDetails.getFullDisplayName());
			}
		}
	}

	/**
	 * Fill map for test jobs.
	 *
	 * @param resultMap
	 *            the result map
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	private void fillMapForTestJobs(Map<String, Object> resultMap) {
		if (JenkinsStaticStore.RESULT_MAP != null) {
			String status = null;
			switch (String.valueOf(resultMap.get("status"))) {
				case "FAIL": {
					status = BuildResult.FAILURE.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Map<String, Set<String>> failureTestCases = new HashMap<String, Set<String>>();
						JenkinsStaticStore.RESULT_MAP.put(status, failureTestCases);
					}
					String failureReason = getFailureStack(String.valueOf(resultMap.get("exception")));
					Map<String, Set<String>> failureTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
					if (!failureTestCases.containsKey(failureReason)) {
						failureTestCases.put(failureReason, new HashSet<String>());
					}
					Set<String> faliureTestCasesList = (Set<String>) failureTestCases.get(failureReason);
					faliureTestCasesList.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				case "FAILED": {
					status = BuildResult.FAILURE.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Map<String, Set<String>> failureTestCases = new HashMap<String, Set<String>>();
						JenkinsStaticStore.RESULT_MAP.put(status, failureTestCases);
					}
					String failureReason = getFailureStack(String.valueOf(resultMap.get("exception")));
					Map<String, Set<String>> failureTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
					if (!failureTestCases.containsKey(failureReason)) {
						failureTestCases.put(failureReason, new HashSet<String>());
					}
					Set<String> faliureTestCasesList = (Set<String>) failureTestCases.get(failureReason);
					faliureTestCasesList.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				case "SKIPPED": {
					status = BuildResult.UNSTABLE.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Map<String, Set<String>> failureTestCases = new HashMap<String, Set<String>>();
						JenkinsStaticStore.RESULT_MAP.put(status, failureTestCases);
					}
					String failureReason = getFailureStack(String.valueOf(resultMap.get("exception")));
					Map<String, Set<String>> failureTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
					if (!failureTestCases.containsKey(failureReason)) {
						failureTestCases.put(failureReason, new HashSet<String>());
					}
					Set<String> faliureTestCasesList = (Set<String>) failureTestCases.get(failureReason);
					faliureTestCasesList.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				case "PASS": {
					status = BuildResult.SUCCESS.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Set<String> testCaseName = new HashSet<String>();
						JenkinsStaticStore.RESULT_MAP.put(status, testCaseName);
					}
					Set<String> testCaseName = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
					testCaseName.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				case "FIXED": {
					status = BuildResult.SUCCESS.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Set<String> testCaseName = new HashSet<String>();
						JenkinsStaticStore.RESULT_MAP.put(status, testCaseName);
					}
					Set<String> testCaseName = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
					testCaseName.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				default: {
					status = JenkinsConstants.DEFAULT_REASON;
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Set<String> otherTestCases = new HashSet<String>();
						JenkinsStaticStore.RESULT_MAP.put(status, otherTestCases);
					}
					Set<String> otherTestCases = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
					otherTestCases.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
			}
		}
	}

	/**
	 * Fill map for test NG jobs.
	 *
	 * @param resultMap
	 *            the result map
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	private void fillMapForTestNGJobs(Map<String, Object> resultMap) {
		if (JenkinsStaticStore.RESULT_MAP != null) {
			String status = null;
			switch (String.valueOf(resultMap.get("status"))) {
				case "FAIL": {
					status = BuildResult.FAILURE.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Map<String, Set<String>> failureTestCases = new HashMap<String, Set<String>>();
						JenkinsStaticStore.RESULT_MAP.put(status, failureTestCases);
					}
					String failureReason = getFailureStack(String.valueOf(resultMap.get("errorStackTrace")));
					Map<String, Set<String>> failureTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
					if (!failureTestCases.containsKey(failureReason)) {
						failureTestCases.put(failureReason, new HashSet<String>());
					}
					Set<String> faliureTestCasesList = (Set<String>) failureTestCases.get(failureReason);
					faliureTestCasesList.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				case "FAILED": {
					status = BuildResult.FAILURE.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Map<String, Set<String>> failureTestCases = new HashMap<String, Set<String>>();
						JenkinsStaticStore.RESULT_MAP.put(status, failureTestCases);
					}
					String failureReason = getFailureStack(String.valueOf(resultMap.get("errorStackTrace")));
					Map<String, Set<String>> failureTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
					if (!failureTestCases.containsKey(failureReason)) {
						failureTestCases.put(failureReason, new HashSet<String>());
					}
					Set<String> faliureTestCasesList = (Set<String>) failureTestCases.get(failureReason);
					faliureTestCasesList.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				case "SKIPPED": {
					status = BuildResult.UNSTABLE.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Map<String, Set<String>> failureTestCases = new HashMap<String, Set<String>>();
						JenkinsStaticStore.RESULT_MAP.put(status, failureTestCases);
					}
					String failureReason = getFailureStack(String.valueOf(resultMap.get("errorStackTrace")));
					Map<String, Set<String>> failureTestCases = (Map<String, Set<String>>) JenkinsStaticStore.RESULT_MAP.get(status);
					if (!failureTestCases.containsKey(failureReason)) {
						failureTestCases.put(failureReason, new HashSet<String>());
					}
					Set<String> faliureTestCasesList = (Set<String>) failureTestCases.get(failureReason);
					faliureTestCasesList.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				case "PASS": {
					status = BuildResult.SUCCESS.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Set<String> testCaseName = new HashSet<String>();
						JenkinsStaticStore.RESULT_MAP.put(status, testCaseName);
					}
					Set<String> testCaseName = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
					testCaseName.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				case "FIXED": {
					status = BuildResult.SUCCESS.name();
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Set<String> testCaseName = new HashSet<String>();
						JenkinsStaticStore.RESULT_MAP.put(status, testCaseName);
					}
					Set<String> testCaseName = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
					testCaseName.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
				default: {
					status = JenkinsConstants.DEFAULT_REASON;
					if (!JenkinsStaticStore.RESULT_MAP.containsKey(status)) {
						Set<String> otherTestCases = new HashSet<String>();
						JenkinsStaticStore.RESULT_MAP.put(status, otherTestCases);
					}
					Set<String> otherTestCases = (Set<String>) JenkinsStaticStore.RESULT_MAP.get(status);
					otherTestCases.add(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					log.info(getClassNameFromFullyQualifiedClassName(String.valueOf(resultMap.get("className"))).concat(".").concat(String.valueOf(resultMap.get("name"))));
					break;
				}
			}
		}
	}

	/**
	 * Gets the failure reason.
	 *
	 * @param buildWithDetails
	 *            the build with details
	 * @return the failure reason
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private String getFailureReason(BuildWithDetails buildWithDetails) {
		String failureStackTrace = null;
		try {
			failureStackTrace = IOUtils.toString(buildWithDetails.getClient().getFile(new URI(buildWithDetails.getUrl() + "/console")));
		} catch (Exception e) {
			log.error("Error in getting Stream for failure test cases." + buildWithDetails.getFullDisplayName());
		}
		return getFailureStack(failureStackTrace);
	}

	/**
	 * Gets the failure stack.
	 *
	 * @param failureStackTrace
	 *            the failure stack trace
	 * @return the failure stack
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private String getFailureStack(String failureStackTrace) {
		String reason = JenkinsConstants.DEFAULT_REASON;
		if (failureStackTrace == null) {
			return JenkinsConstants.DEFAULT_REASON;
		} else {
			try {
				if (JenkinsStaticStore.ERROR_STACK != null && JenkinsStaticStore.ERROR_STACK.size() > 0) {
					reason = getErrorLabel(failureStackTrace);
					if (reason == null) {
						return getFailureStack(failureStackTrace.substring(failureStackTrace.indexOf(JenkinsConstants.FAILURE) + 8, failureStackTrace.indexOf(JenkinsConstants.ERROR)));
					}
				} else {
					return JenkinsConstants.DEFAULT_REASON;
				}
			} catch (Exception e) {
				return JenkinsConstants.DEFAULT_REASON;
			}
		}
		return reason;
	}

	/**
	 * Gets the error label.
	 *
	 * @param failureStackTrace
	 *            the failure stack trace
	 * @return the error label
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private String getErrorLabel(String failureStackTrace) {
		for (Map.Entry<String, String> error : JenkinsStaticStore.ERROR_STACK.entrySet()) {
			if (failureStackTrace.contains(error.getValue())) {
				return error.getKey();
			}
		}
		return null;
	}

	/**
	 * Gets the class name from fully qualified class name.
	 *
	 * @param classNameWithFullyQualifiedClassName
	 *            the class name with fully qualified class name
	 * @return the class name from fully qualified class name
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private String getClassNameFromFullyQualifiedClassName(String classNameWithFullyQualifiedClassName) {
		if (StringUtils.isEmpty(classNameWithFullyQualifiedClassName)) {
			return "";
		}
		return classNameWithFullyQualifiedClassName.substring(classNameWithFullyQualifiedClassName.lastIndexOf(".") + 1);
	}
}