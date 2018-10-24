
package ru.skpari.core.messages.commission;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.skpari.core.messages.commission package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.skpari.core.messages.commission
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCat }
     * 
     */
    public GetCat createGetCat() {
        return new GetCat();
    }

    /**
     * Create an instance of {@link ResponseCat }
     * 
     */
    public ResponseCat createResponseCat() {
        return new ResponseCat();
    }

    /**
     * Create an instance of {@link GetFault }
     * 
     */
    public GetFault createGetFault() {
        return new GetFault();
    }

}
