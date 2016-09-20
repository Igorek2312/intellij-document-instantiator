package com.company.document.instantiator.model;

/**
 * Created by igorek2312 on 19.09.16.
 */
public class CompilationFile {
    private String sourceRootPath;
    private String projectFilePath;
    private String className;
    private String workingPackagePath;

    public CompilationFile(String sourceRootPath, String projectFilePath, String className, String workingPackagePath) {
        this.sourceRootPath = sourceRootPath;
        this.projectFilePath = projectFilePath;
        this.className = className;
        this.workingPackagePath = workingPackagePath;
    }

    public String getSourceRootPath() {
        return sourceRootPath;
    }

    public void setSourceRootPath(String sourceRootPath) {
        this.sourceRootPath = sourceRootPath;
    }

    public String getProjectFilePath() {
        return projectFilePath;
    }

    public void setProjectFilePath(String projectFilePath) {
        this.projectFilePath = projectFilePath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getWorkingPackagePath() {
        return workingPackagePath;
    }

    public void setWorkingPackagePath(String workingPackagePath) {
        this.workingPackagePath = workingPackagePath;
    }
}
