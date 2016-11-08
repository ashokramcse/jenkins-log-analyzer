package com.ashokram.jenkins.pojo;

import java.io.InputStream;
import java.io.Serializable;

public class ExcelTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;

    private String fullName;

    private InputStream fileInputStream;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

}
