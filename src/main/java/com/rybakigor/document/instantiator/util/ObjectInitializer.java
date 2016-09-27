package com.rybakigor.document.instantiator.util;

/**
 * Created by igorek2312 on 17.09.16.
 */
public interface ObjectInitializer {
    void initializeObject(Object o) throws IllegalAccessException, InstantiationException;
}
