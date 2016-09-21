package com.company.document.instantiator.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by igorek2312 on 19.09.16.
 */
public class CompilationFile {
    private String className;
    private Set<String> dependedFilesPaths = new HashSet<>();
    private Set<String> dependedClassFilesPaths = new HashSet<>();

    public CompilationFile(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Set<String> getDependedFilesPaths() {
        return dependedFilesPaths;
    }

    public void setDependedFilesPaths(Set<String> dependedFilesPaths) {
        this.dependedFilesPaths = dependedFilesPaths;
    }

    public Set<String> getDependedRootsPaths() {
        return dependedClassFilesPaths;
    }

    public void setDependedClassFilesPaths(Set<String> dependedClassFilesPaths) {
        this.dependedClassFilesPaths = dependedClassFilesPaths;
    }
}
