package com.ashokram.jenkins.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ashokram.jenkins.helper.JenkinsServletHelper;

public class JenkinsStaticStore {

    private static final Log log = LogFactory.getLog(JenkinsServletHelper.class);

    public static Map<String, Object> RESULT_MAP = new LinkedHashMap<String, Object>();

    public static Map<String, String> ERROR_STACK = new LinkedHashMap<String, String>();

    static {
        ClassLoader classLoader = JenkinsStaticStore.class.getClassLoader();
        File file = new File(classLoader.getResource(JenkinsConstants.ERROR_FILE_NAME).getFile());
        try {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.startsWith("#")) {
                        String[] property = line.split("=");
                        ERROR_STACK.put(property[0], property[1]);
                    }
                }
            }
        } catch (IOException e) {
            log.error("The errors are not loaded into the store.", e);
        }
    }

    public static synchronized void clearCache() {
        if (JenkinsStaticStore.RESULT_MAP != null && JenkinsStaticStore.RESULT_MAP.size() > 0) {
            RESULT_MAP = new HashMap<String, Object>();
        }
    }
}
