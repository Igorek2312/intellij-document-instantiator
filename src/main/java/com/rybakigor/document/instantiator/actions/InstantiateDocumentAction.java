package com.rybakigor.document.instantiator.actions;

import com.rybakigor.document.instantiator.model.CompilationFile;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packageDependencies.DependenciesBuilder;
import com.intellij.packageDependencies.ForwardDependenciesBuilder;
import com.intellij.psi.PsiFile;
import com.rybakigor.document.instantiator.util.*;

import javax.xml.bind.JAXBException;
import java.util.Optional;

/**
 * Created by igorek2312 on 20.09.16.
 */
public abstract class InstantiateDocumentAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        Optional.of(e.getData(CommonDataKeys.VIRTUAL_FILE))
                .map(VirtualFile::getExtension)
                .map(extension -> extension.equals("java"))
                .ifPresent(present -> e.getPresentation()
                        .setEnabledAndVisible(present));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        ApplicationManager.getApplication().saveAll();
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
                    compilationFile.getDependedFilesPaths().add(
                            v.getParent().getPath() + "/" + v.getNameWithoutExtension()
                    );
                } else if (v.getExtension().equals("class")) {
                    String classFileRoot = prIndex.getClassRootForFile(v).getPath();
                    compilationFile.getDependedRootsPaths().add(
                            classFileRoot
                    );
                }
            }
        };

        ForwardDependenciesBuilder.analyzeFileDependencies(currentPsiFile, dependencyProcessor);

        String successMessage = null;
        DocumentConventer documentConventer = null;
        if (this instanceof InstantiateJsonAction) {
            successMessage = "JSON copied to clipboard";
            documentConventer = new JacksonDocumentConventer();
        } else if (this instanceof InstantiateXmlAction) {
            successMessage = "XML copied to clipboard";
            documentConventer = new JaxbDocumentConventer();
        }

        try (ClasspathLoader classpathLoader = new JavaClasspathLoader(compilationFile)) {
            Object instance = classpathLoader.compileAndInstantiate();
            ObjectInitializer objectInitializer = new ObjectInitializerImpl();
            objectInitializer.initializeObject(instance);
            String document = documentConventer.convertFromPojo(instance);
            ClipboardUtil.getInstance().copyToClipBoard(document);
            Messages.showMessageDialog(project, successMessage, "Inforamtion", Messages.getInformationIcon());
        } catch (InstantiationException e1) {
            Messages.showMessageDialog(project, "The class: " + e1.getMessage() + " cannot be instantiated.", "Error", Messages.getErrorIcon());
            e1.printStackTrace();
        } catch (JAXBException e1) {
            if (e1.getLinkedException() != null)
                Messages.showMessageDialog(project, e1.getLinkedException().getMessage(), "Error", Messages.getErrorIcon());
            else
                Messages.showMessageDialog(project, e1.getMessage(), "Error", Messages.getErrorIcon());
            e1.printStackTrace();
        } catch (Exception e1) {
            Messages.showMessageDialog(project, e1.getMessage(), "Error", Messages.getErrorIcon());
            e1.printStackTrace();
        }
    }
}
