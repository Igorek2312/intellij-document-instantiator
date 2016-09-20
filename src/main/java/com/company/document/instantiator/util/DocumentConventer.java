package com.company.document.instantiator.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.xml.bind.JAXBException;

/**
 * Created by igorek2312 on 17.09.16.
 */
public interface DocumentConventer {
    String convertFromPojo(Object o) throws JAXBException, JsonProcessingException;
}
