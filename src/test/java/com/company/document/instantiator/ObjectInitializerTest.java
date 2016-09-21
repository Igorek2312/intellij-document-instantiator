package com.company.document.instantiator;

import com.company.document.instantiator.model.AuthorItemDto;
import com.company.document.instantiator.model.BookDetailDto;
import com.company.document.instantiator.util.ObjectInitializer;
import com.company.document.instantiator.util.ObjectInitializerImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by igorek2312 on 21.09.16.
 */
public class ObjectInitializerTest {

    @Test
    public void testInitializeObject() throws InstantiationException, IllegalAccessException {
        ObjectInitializer initialize = new ObjectInitializerImpl();
        BookDetailDto dto = new BookDetailDto();
        initialize.initializeObject(dto);

        assertNotNull(dto.getId());
        assertEquals(dto.getName(), "name");
        assertNotNull(dto.getPages());
        assertNotNull(dto.getYear());
        assertNotNull(dto.getMainAuthor());
        assertEquals(dto.getAuthorItems().size(), 2);
        AuthorItemDto author = (AuthorItemDto) dto.getAuthorItems().get(1);
        assertNotNull(author.getId());
        assertEquals(author.getName(), "name");
        assertEquals(author.getSurname(), "surname");
        assertNotNull(author.isRegistered());
    }
}
