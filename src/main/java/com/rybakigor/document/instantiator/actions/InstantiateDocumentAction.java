package com.rybakigor.document.instantiator.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.rybakigor.document.instantiator.model.CompilationFile;
import com.rybakigor.document.instantiator.util.*;

import javax.xml.bind.JAXBException;
import java.util.Optional;

/**
 * Created by igorek2312 on 20.09.16.
 */
public abstract class InstantiateDocumentAction extends AnAction {

    protected abstract String getOnSuccessMessage();

    protected abstract DocumentConventer getDocumentConventer();

    @Override
    public void update(AnActionEvent e) {
        Optional<Boolean> isJavaFile = Optional.ofNullable(e)
                .map(event -> event.getData(CommonDataKeys.VIRTUAL_FILE))
                .map(VirtualFile::getExtension)
                .map(extension -> extension.equalsIgnoreCase("java"));

        Boolean enabledAndVisible = isJavaFile
                .orElse(false);

        Optional.ofNullable(e)
                .ifPresent(event -> e.getPresentation().setEnabledAndVisible(enabledAndVisible));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        ApplicationManager.getApplication().saveAll();
        Project project = e.getData(PlatformDataKeys.PROJECT);
        try (ClasspathLoader classpathLoader = new JavaClasspathLoader(CompilationFile.newInstance(e))) {
            Object instance = classpathLoader.compileAndInstantiate();
            ObjectInitializer objectInitializer = new ObjectInitializerImpl();
            objectInitializer.initializeObject(instance);
            String document = getDocumentConventer().convertFromPojo(instance);
            ClipboardUtil.getInstance().copyToClipBoard(document);
            Messages.showMessageDialog(project, getOnSuccessMessage(), "Inforamtion", Messages.getInformationIcon());
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
