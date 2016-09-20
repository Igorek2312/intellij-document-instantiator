package com.company.document.instantiator;

import com.company.document.instantiator.model.AuthorItemDto;
import com.company.document.instantiator.model.BookDetailDto;
import com.company.document.instantiator.util.JacksonDocumentConventer;
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

    private Object sampleInitializedPojo(){
        BookDetailDto bookDto=new BookDetailDto();
        bookDto.setId(1L);
        bookDto.setName("Java8");
        bookDto.setPages(150);
        bookDto.setYear(2016);
        AuthorItemDto authorDto1=new AuthorItemDto();
        authorDto1.setId(2L);
        authorDto1.setName("AuthorName1");
        authorDto1.setSurname("AuthorSurname1");
        authorDto1.setRegistered(true);
        AuthorItemDto authorDto2=new AuthorItemDto();
        authorDto2.setId(3L);
        authorDto2.setName("AuthorName2");
        authorDto2.setSurname("AuthorSurname2");
        authorDto2.setRegistered(false);
        bookDto.getAuthorItems().add(authorDto1);
        bookDto.getAuthorItems().add(authorDto2);
        bookDto.setMainAuthor(authorDto1);
        return bookDto;
    }

    private String jsonForSample() throws IOException, URISyntaxException {
        URL jsonUrl = Thread.currentThread().getContextClassLoader().getResource("json/bookDto.json");
        byte[] bytes = Files.readAllBytes(Paths.get(jsonUrl.toURI()));
        String json = new String(bytes, Charset.forName("UTF-8"));
        return json;
    }

    @Test
    public void testConvertToJson() throws JAXBException, IOException, URISyntaxException {
        JacksonDocumentConventer conventer=new JacksonDocumentConventer();
        Object dto = sampleInitializedPojo();
        String sampleJson = jsonForSample();
        String json = conventer.convertFromPojo(dto);
        assertEquals(json.trim(),sampleJson.trim());
    }
}
