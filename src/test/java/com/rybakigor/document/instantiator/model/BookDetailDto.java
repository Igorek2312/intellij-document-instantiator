package com.rybakigor.document.instantiator.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igorek2312 on 14.09.16.
 */
@XmlRootElement
public class BookDetailDto {
    private Long id;

    private String name;

    private Integer pages;

    private Integer year;


    private AuthorItemDto mainAuthor;

    @XmlElement
    private List<AuthorItemDto> authorItems =new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List getAuthorItems() {
        return authorItems;
    }

    public void setAuthorItems(List<AuthorItemDto> authorItems) {
        this.authorItems = authorItems;
    }

    public AuthorItemDto getMainAuthor() {
        return mainAuthor;
    }

    public void setMainAuthor(AuthorItemDto mainAuthor) {
        this.mainAuthor = mainAuthor;
    }
}
