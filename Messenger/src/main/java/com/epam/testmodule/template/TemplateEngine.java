package com.epam.testmodule.template;
import com.epam.testmodule.Client;
import lombok.Data;

/**
 * The type Template engine.
 */
@Data
public class TemplateEngine {

    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        if (template.getEvent()==null){
            throw new NullPointerException("Please set event");
        }
        if(template.getDate()==null){
            throw new NullPointerException("Please set date");
        }
        if(client.getMail()==null){
            throw new NullPointerException("Please set the recipient's email address");
        }
        return String.format("Hello, friend. Our team would like to invite you to the %s event," +
                " which will take place on %s. The recipient's email address is %s."
                , template.getEvent(), template.getDate(), client.getMail());
    }
}
