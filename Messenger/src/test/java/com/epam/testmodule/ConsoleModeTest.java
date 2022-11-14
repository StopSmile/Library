package com.epam.testmodule;

import com.epam.testmodule.template.Template;
import com.epam.testmodule.template.TemplateEngine;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsoleModeTest {

    @Test
    public void getEventNameFromConsole_ShouldReturnCorrectStringEventName() {
        //given
        ConsoleMode consoleMode = new ConsoleMode();
        String input = "JAVA DAY";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        //assert
        assertEquals("JAVA DAY", consoleMode.getEventNameFromConsole());

    }

    @Test
    public void getEventDateFromConsole_ShouldReturnCorrectStringEventDate() {
        //given
        ConsoleMode consoleMode = new ConsoleMode();
        String input = "10.10.2023";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        //assert
        assertEquals("10.10.2023", consoleMode.getEventDateFromConsole());
    }

    @Test
    public void getClientEmailFromConsole_ShouldReturnCorrectStringClientEmail() {
        //given
        ConsoleMode consoleMode = new ConsoleMode();
        String input = "client@gmail.com";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        //assert
        assertEquals("client@gmail.com", consoleMode.getClientEmailFromConsole());
    }

    @Test
    public void start() {
        //given
        MailServer mailServer = Mockito.mock(MailServer.class);
        TemplateEngine templateEngine = Mockito.mock(TemplateEngine.class);
        ConsoleMode consoleModeSpy = Mockito.spy(new ConsoleMode());
        Messenger messengerSpy = Mockito.spy(new Messenger(mailServer, templateEngine));
        Template template = new Template();
        Client client = new Client();
        doReturn("JAVA DAY").when(consoleModeSpy).getEventNameFromConsole();
        doReturn("10.10.2023").when(consoleModeSpy).getEventDateFromConsole();
        doReturn("client@gmail.com").when(consoleModeSpy).getClientEmailFromConsole();

        //when
        consoleModeSpy.start();
        template.setEventName(doReturn("JAVA DAY").when(consoleModeSpy).getEventNameFromConsole());
        template.setDate(doReturn("10.10.2023").when(consoleModeSpy).getEventDateFromConsole());
        client.setMail(doReturn("client@gmail.com").when(consoleModeSpy).getClientEmailFromConsole());
        messengerSpy.sendMessage(client, template);

        //assert
        Mockito.verify(consoleModeSpy, times(1)).getEventNameFromConsole();
        Mockito.verify(consoleModeSpy, times(1)).getEventDateFromConsole();
        Mockito.verify(consoleModeSpy, times(1)).getClientEmailFromConsole();
        Mockito.verify(consoleModeSpy, times(1)).start();
        Mockito.verify(messengerSpy, times(1)).sendMessage(client, template);
    }

}