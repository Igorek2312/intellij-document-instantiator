package com.rybakigor.document.instantiator.config;/*
 * Created by IntelliJ IDEA.
 * User: igorek2312
 * Date: 21.09.16
 * Time: 11:45
 */

import com.rybakigor.document.instantiator.model.AuthorItemDto;
import com.rybakigor.document.instantiator.model.BookDetailDto;
import com.google.inject.Provider;
import com.rybakigor.document.instantiator.model.Genre;

public class InitializedBookDetailDtoProvider implements Provider<BookDetailDto> {
    public BookDetailDto get() {
        BookDetailDto bookDto = new BookDetailDto();
        bookDto.setId(1L);
        bookDto.setName("Java8");
        bookDto.setPages(150);
        bookDto.setYear(2016);
        bookDto.getGenres().add(Genre.NonFiction);
        bookDto.getGenres().add(Genre.Comedy);
        AuthorItemDto authorDto1 = new AuthorItemDto();
        authorDto1.setId(2L);
        authorDto1.setName("AuthorName1");
        authorDto1.setSurname("AuthorSurname1");
        authorDto1.setRegistered(true);
        AuthorItemDto authorDto2 = new AuthorItemDto();
        authorDto2.setId(3L);
        authorDto2.setName("AuthorName2");
        authorDto2.setSurname("AuthorSurname2");
        authorDto2.setRegistered(false);
        bookDto.getAuthorItems().add(authorDto1);
        bookDto.getAuthorItems().add(authorDto2);
        bookDto.setMainAuthor(authorDto1);
        return bookDto;
    }
}
