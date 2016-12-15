package com.rybakigor.document.instantiator.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rybakigor.document.instantiator.util.DocumentConventer;
import com.rybakigor.document.instantiator.util.JacksonDocumentConventer;

/**
 * Created by igorek2312 on 17.09.16.
 */
public class InstantiateJsonAction extends InstantiateDocumentAction {

    @Override
    protected String getOnSuccessMessage() {
        return "JSON copied to clipboard";
    }

    @Override
    protected DocumentConventer getDocumentConventer() {
        return new JacksonDocumentConventer();
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        super.actionPerformed(e);
    }

}
