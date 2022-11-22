package com.epam.testmodule;

import com.epam.testmodule.template.Template;
import com.epam.testmodule.template.TemplateEngine;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MessengerTest {

    @Test
    public void sendMessage_WhenCorrectData_ShouldWorkCorrect() {
        //given
        MailServer mailServerMock = Mockito.mock(MailServer.class);
        TemplateEngine templateEngineMock = Mockito.mock(TemplateEngine.class);
        Messenger messenger = new Messenger(mailServerMock, templateEngineMock);
        Template template = new Template();
        Client client = new Client();
        client.setMail("client@gmail.com");
        template.setEventName("JAVA DAY");
        template.setDate("10.10.2023");
        when(templateEngineMock.generateMessage(template, client)).thenReturn("Hello, friend. Our team would like to invite you to the JAVA DAY event," +
                " which will take place on 10.10.2023. The recipient's email address is client@gmail.com.");
        //when
        messenger.sendMessage(client, template);
        //assert
        verify(templateEngineMock).generateMessage(template, client);
        verify(mailServerMock).send("client@gmail.com", "Hello, friend. Our team would like to invite you to the JAVA DAY event," +
                " which will take place on 10.10.2023. The recipient's email address is client@gmail.com.");


    }

    @Test
    public void sendDefaultMessage_WhenSendDefaultMessage_CheckMethodAndSendMassage() {
        //given
        Template template = new Template();
        Client client = new Client();
        client.setMail("client@gmail.com");
        template.setDate("10.10.2023");
        template.setEventName("JAVA DAY");
        MailServer mailServer = Mockito.mock(MailServer.class);
        TemplateEngine templateEngine = Mockito.mock(TemplateEngine.class);
        Messenger messengerSpy = Mockito.spy(new Messenger(mailServer,templateEngine));
        client.setMail("client@gmail.com");
        template.setEventName("JAVA DAY");
        template.setDate("10.10.2023");
        //when
        messengerSpy.sendDefaultMessage();
        //assert
        Mockito.verify(messengerSpy, Mockito.times(1)).sendDefaultMessage();
        Mockito.verify(messengerSpy,Mockito.times(1)).sendMessage(client,template);
        verify(messengerSpy).sendDefaultMessage();
        verify(messengerSpy).sendMessage(client,template);

    }
}
