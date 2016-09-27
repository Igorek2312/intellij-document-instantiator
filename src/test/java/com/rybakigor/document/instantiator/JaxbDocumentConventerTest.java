package com.rybakigor.document.instantiator;

import com.rybakigor.document.instantiator.config.DocumentConventerConfig;
import com.rybakigor.document.instantiator.config.InitializedBookDetailDtoProvider;
import com.rybakigor.document.instantiator.model.BookDetailDto;
import com.rybakigor.document.instantiator.util.DocumentConventer;
import com.rybakigor.document.instantiator.util.JaxbDocumentConventer;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

/**
 * Created by igorek2312 on 19.09.16.
 */
public class JaxbDocumentConventerTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(DocumentConventer.class).to(JaxbDocumentConventer.class);
                bind(BookDetailDto.class).toProvider(InitializedBookDetailDtoProvider.class);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        injector = null;
    }

    private String xmlForSample() throws IOException, URISyntaxException {
        URL url = Resources.getResource("xml/bookDto.xml");
        String xml = Resources.toString(url, Charset.forName("UTF-8"));
        return xml;
    }

    @Test
    public void testConvertToXml() throws JAXBException, IOException, URISyntaxException {
        DocumentConventerConfig config = injector.getInstance(DocumentConventerConfig.class);
        DocumentConventer conventer = config.getDocumentConventer();
        BookDetailDto dto = config.getInitializeBookDetailDto();
        String sampleXml = xmlForSample();
        String xml = conventer.convertFromPojo(dto);
        assertEquals(sampleXml.trim(), xml.trim());

    }

}
