package com.rybakigor.document.instantiator.model;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packageDependencies.DependenciesBuilder;
import com.intellij.packageDependencies.ForwardDependenciesBuilder;
import com.intellij.psi.PsiFile;
import com.sun.javafx.PlatformUtil;

import java.util.*;


/**
 * Created by igorek2312 on 19.09.16.
 */
public class CompilationFile {
    private String className;
    private Set<String> dependedFilesPaths = new HashSet<>();
    private Set<String> dependedClassFilesPaths = new HashSet<>();
    private Set<String> dependedJars = new HashSet<>();

    private static String getDelimiter() {
        return PlatformUtil.isWindows() ? ";" : ":";
    }

    public String[] toCompilerArgs() {
        String delimiter = getDelimiter();

        List<String> args = new ArrayList<>();

        Optional<String> jarsArgs = dependedJars.stream()
                .reduce((s1, s2) -> s1 + delimiter + s2);

        jarsArgs.ifPresent(jars -> {
            args.add("-cp");
            args.add("." + delimiter + jars);
        });

        args.addAll(dependedFilesPaths);

        return args.toArray(new String[args.size()]);
    }

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

    public Set<String> getDependedJars() {
        return dependedJars;
    }


    public static CompilationFile newInstance(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        ProjectFileIndex prIndex = ProjectRootManager.getInstance(project).getFileIndex();

        PsiFile currentPsiFile = e.getData(CommonDataKeys.PSI_FILE);
        VirtualFile currentFile = currentPsiFile.getVirtualFile();
        VirtualFile sourceRoot = prIndex.getSourceRootForFile(currentFile);
        String packageName = prIndex.getPackageNameByDirectory(currentFile.getParent());
        String className = (packageName.isEmpty() ? "" : (packageName + ".")) + currentFile.getNameWithoutExtension();

        CompilationFile compilationFile = new CompilationFile(className);

        compilationFile.getDependedRootsPaths().add(sourceRoot.getPath());

        DependenciesBuilder.DependencyProcessor dependencyProcessor = (psiElement, psiElement1) -> {
            if (psiElement1.getContainingFile() != null) {
                VirtualFile v = psiElement1.getContainingFile().getVirtualFile();
                if (v.getExtension().equals("java")) {
                    compilationFile.getDependedFilesPaths().add(v.getPath());
                } else if (v.getExtension().equals("class")) {
                    // compilationFile.getDependedFilesPaths().add(v.getPath());
                    String classFileRoot = prIndex.getClassRootForFile(v).getPath();
                    compilationFile.getDependedRootsPaths().add(classFileRoot);

                    if (classFileRoot.endsWith(".jar!/")) {
                        compilationFile.getDependedJars().add(
                                classFileRoot.replace(".jar!/", ".jar")
                        );
                    }
                }
            }
        };

        ForwardDependenciesBuilder.analyzeFileDependencies(currentPsiFile, dependencyProcessor);

        return compilationFile;
    }
}
