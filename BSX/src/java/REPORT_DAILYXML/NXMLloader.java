/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REPORT_DAILYXML;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 *
 * @author William
 */
public class NXMLloader {
    
    private String error;

    public String getError() {
        return error;
    }

    private void setError(String error) {
        this.error = error;
    }
    
    public boolean validate(String schemaFile, String xmlFile) {
        try {
            // parse an XML document into a DOM tree
            Source source = new StreamSource(xmlFile);

            // create a SchemaFactory capable of understanding WXS schemas
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // load a WXS schema, represented by a Schema instance
            Source schFile = new StreamSource(new File(schemaFile));
            Schema schema = factory.newSchema(schFile);

            // create a Validator instance, which can be used to validate an instance document
            Validator validator = schema.newValidator();

            // validate the DOM tree
            validator.validate(source);
            return true;
            
        } catch (IOException ex) {
            setError(ex.toString());
            return false;
        } catch (SAXException ex) {
            setError(ex.toString());
            return false;
        }
    }

    
}
