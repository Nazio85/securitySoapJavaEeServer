
package ru.skpari.core.services.commission;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.2.5-jbossorg-1
 * 2018-10-19T17:54:42.301+03:00
 * Generated source version: 3.2.5-jbossorg-1
 */

@WebFault(name = "getFault", targetNamespace = "http://skpari.ru/core/messages/commission/")
public class GetFaultMessage extends Exception {

    private ru.skpari.core.messages.commission.GetFault getFault;

    public GetFaultMessage() {
        super();
    }

    public GetFaultMessage(String message) {
        super(message);
    }

    public GetFaultMessage(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public GetFaultMessage(String message, ru.skpari.core.messages.commission.GetFault getFault) {
        super(message);
        this.getFault = getFault;
    }

    public GetFaultMessage(String message, ru.skpari.core.messages.commission.GetFault getFault, java.lang.Throwable cause) {
        super(message, cause);
        this.getFault = getFault;
    }

    public ru.skpari.core.messages.commission.GetFault getFaultInfo() {
        return this.getFault;
    }
}
