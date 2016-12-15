package com.rybakigor.document.instantiator.config;

import com.google.inject.Inject;
import com.rybakigor.document.instantiator.model.BookDetailDto;
import com.rybakigor.document.instantiator.util.DocumentConventer;

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
