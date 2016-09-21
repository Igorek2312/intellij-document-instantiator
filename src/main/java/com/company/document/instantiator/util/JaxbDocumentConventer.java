package com.company.document.instantiator.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Created by igorek2312 on 19.09.16.
 */
public class JaxbDocumentConventer implements DocumentConventer {
    @Override
    public String convertFromPojo(Object o) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.marshal(o, sw);
        String xml = sw.toString();
        return xml;
    }
}
