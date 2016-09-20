package com.company.document.instantiator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by igorek2312 on 17.09.16.
 */
public class JacksonDocumentConventer implements DocumentConventer {

    @Override
    public String convertFromPojo(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        return json;
    }
}
