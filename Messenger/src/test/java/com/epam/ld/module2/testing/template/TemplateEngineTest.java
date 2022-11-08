package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


class TemplateEngineTest {

    @Test
    void generateMessage_WhenValueEventInTemplateIsNotProvided_shouldGenerateNullPointerException() {
        Template template = new Template();
        Client client = new Client();
        client.setMail("clientmail@gmail.com");
        template.setDate("22.12.2023");
        TemplateEngine templateEngine = new TemplateEngine();
        Assertions.assertThrows(NullPointerException.class,()->{
           templateEngine.generateMessage(template,client);
        });
    }
    @Test
    void generateMessage_WhenValueDateInTemplateIsNotProvided_shouldGenerateNullPointerException() {
        Template template = new Template();
        Client client = new Client();
        template.setEventName("JAVA EVENT");
        client.setMail("clientmail@gmail.com");
        TemplateEngine templateEngine = new TemplateEngine();
        Assertions.assertThrows(NullPointerException.class,()->{
            templateEngine.generateMessage(template,client);
        });
    }
    @Test
    void generateMessage_WhenValueMailInClientIsNotProvided_shouldGenerateNullPointerException() {
        Template template = new Template();
        Client client = new Client();
        template.setEventName("JAVA EVENT");
        template.setDate("22.12.2023");
        TemplateEngine templateEngine = new TemplateEngine();
        Assertions.assertThrows(NullPointerException.class,()->{
            templateEngine.generateMessage(template,client);
        });
    }
    @Test
    @Tag("UnitTest")
    void generateMessage_WhenClientAndTemplateHasCorrectData_shouldGenerateCorrectMassage(){
        Template template = new Template();
        Client client = new Client();
        TemplateEngine templateEngine = new TemplateEngine();
        client.setMail("clientmail@gmail.com");
        template.setEventName("JAVA EVENT");
        template.setDate("22.12.2023");
        String extractedMessage = templateEngine.generateMessage(template,client);
        String expectedMessage = "Hello, friend. Our team would like to invite you to the JAVA EVENT event," +
                " which will take place on 22.12.2023. The recipient's email address is clientmail@gmail.com.";
        Assertions.assertEquals(expectedMessage,extractedMessage);
    }

}