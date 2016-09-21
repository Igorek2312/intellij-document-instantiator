package com.company.document.instantiator.config;

import com.company.document.instantiator.model.BookDetailDto;
import com.company.document.instantiator.util.DocumentConventer;
import com.google.inject.Inject;

/**
 * Created by igorek2312 on 21.09.16.
 */
public class DocumentConventerConfig {

    @Inject
    private DocumentConventer documentConventer;

    @Inject
    private BookDetailDto initializeBookDetailDto;

    public DocumentConventer getDocumentConventer() {
        return documentConventer;
    }

    public BookDetailDto getInitializeBookDetailDto() {
        return initializeBookDetailDto;
    }
}
