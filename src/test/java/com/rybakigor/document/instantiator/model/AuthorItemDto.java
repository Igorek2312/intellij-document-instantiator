package com.rybakigor.document.instantiator.model;

/**
 * Created by igorek2312 on 14.09.16.
 */
public class AuthorItemDto {
    private Long id;

    private String name;

    private String surname;

    private Boolean registered;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
