package com.ashokram.jenkins.constants;

import org.apache.commons.lang3.StringUtils;

public class JsonBuilder {

    public int getSize() {
        int size = 0;
        do {
            size = (int) (Math.random() * (999) + Math.random());
        } while (size == 0);
        return size;
    }

    public String cleanJson(String json) {
        do {
            json = json.replaceAll(",\"size\":0", StringUtils.EMPTY);
        } while (json.contains(",\"size\":0"));
        return json;
    }
}
