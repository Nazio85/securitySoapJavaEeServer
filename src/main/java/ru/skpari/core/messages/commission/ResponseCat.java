
package ru.skpari.core.messages.commission;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.skpari.core.domain.commission.CatType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cat" type="{http://skpari.ru/core/domain/commission/}CatType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cat"
})
@XmlRootElement(name = "responseCat")
public class ResponseCat {

    @XmlElement(name = "Cat", required = true)
    protected CatType cat;

    /**
     * Gets the value of the cat property.
     * 
     * @return
     *     possible object is
     *     {@link CatType }
     *     
     */
    public CatType getCat() {
        return cat;
    }

    /**
     * Sets the value of the cat property.
     * 
     * @param value
     *     allowed object is
     *     {@link CatType }
     *     
     */
    public void setCat(CatType value) {
        this.cat = value;
    }

}
