package com.rybakigor.document.instantiator;

import com.rybakigor.document.instantiator.model.AuthorItemDto;
import com.rybakigor.document.instantiator.model.BookDetailDto;
import com.rybakigor.document.instantiator.model.Foo;
import com.rybakigor.document.instantiator.util.ObjectInitializer;
import com.rybakigor.document.instantiator.util.ObjectInitializerImpl;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by igorek2312 on 21.09.16.
 */
public class ObjectInitializerTest {

    @Test
    public void testInitializeObject() throws InstantiationException, IllegalAccessException, JAXBException {
        ObjectInitializer initializer = new ObjectInitializerImpl();
        BookDetailDto dto = new BookDetailDto();
        initializer.initializeObject(dto);

        assertNotNull(dto.getId());
        assertEquals(dto.getName(), "name");
        assertNotNull(dto.getPages());
        assertNotNull(dto.getYear());
        assertNotNull(dto.getMainAuthor());
        assertEquals(dto.getGenres().size(), 2);
        assertEquals(dto.getAuthorItems().size(), 2);
        AuthorItemDto author = (AuthorItemDto) dto.getAuthorItems().get(1);
        assertNotNull(author.getId());
        assertEquals(author.getName(), "name");
        assertEquals(author.getSurname(), "surname");
        assertNotNull(author.isRegistered());
        Foo foo=new Foo();
        initializer.initializeObject(foo);
        Arrays.stream(foo.getLongs()).forEach(Assert::assertNotNull);
        /*DocumentConventer conventer=new JacksonDocumentConventer();
        System.out.println(conventer.convertFromPojo(foo));*/
    }
}
