package com.rybakigor.document.instantiator.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rybakigor.document.instantiator.util.DocumentConventer;
import com.rybakigor.document.instantiator.util.JaxbDocumentConventer;

/**
 * Created by igorek2312 on 20.09.16.
 */
public class InstantiateXmlAction extends InstantiateDocumentAction {
    @Override
    protected String getOnSuccessMessage() {
        return "XML copied to clipboard";
    }

    @Override
    protected DocumentConventer getDocumentConventer() {
        return new JaxbDocumentConventer();
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        super.actionPerformed(e);
    }

}
