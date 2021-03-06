//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.17 at 01:41:25 PM CEST 
//


package webService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for optimizerCheck.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="optimizerCheck">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="min"/>
 *     &lt;enumeration value="max"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "optimizerCheck")
@XmlEnum
public enum OptimizerCheck {

    @XmlEnumValue("min")
    MIN("min"),
    @XmlEnumValue("max")
    MAX("max");
    private final String value;

    OptimizerCheck(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OptimizerCheck fromValue(String v) {
        for (OptimizerCheck c: OptimizerCheck.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
