package com.company.document.instantiator;

import com.company.document.instantiator.config.DocumentConventerConfig;
import com.company.document.instantiator.config.InitializedBookDetailDtoProvider;
import com.company.document.instantiator.model.BookDetailDto;
import com.company.document.instantiator.util.DocumentConventer;
import com.company.document.instantiator.util.JacksonDocumentConventer;
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
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by igorek2312 on 19.09.16.
 */
public class JacksonDocumentConventerTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(DocumentConventer.class).to(JacksonDocumentConventer.class);
                bind(BookDetailDto.class).toProvider(InitializedBookDetailDtoProvider.class);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        injector = null;
    }

    private String jsonForSample() throws IOException, URISyntaxException {
        URL jsonUrl = Thread.currentThread().getContextClassLoader().getResource("json/bookDto.json");
        byte[] bytes = Files.readAllBytes(Paths.get(jsonUrl.toURI()));
        String json = new String(bytes, Charset.forName("UTF-8"));
        return json;
    }

    @Test
    public void testConvertToJson() throws JAXBException, IOException, URISyntaxException {
        DocumentConventerConfig config = injector.getInstance(DocumentConventerConfig.class);
        DocumentConventer conventer=config.getDocumentConventer();
        Object dto =config.getInitializeBookDetailDto();
        String sampleJson = jsonForSample();
        String json = conventer.convertFromPojo(dto);
        assertEquals(json.trim(),sampleJson.trim());
    }
}
