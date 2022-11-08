package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.util.Scanner;

public class ConsoleMode {

    public ConsoleMode() {

    }

    public String getEventNameFromConsole() {
        System.out.println("Please enter event name : ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public String getEventDateFromConsole() {
        System.out.println("Please enter event date : ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public String getClientEmailFromConsole() {
        System.out.println("Please enter client mail : ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public void start() {
        System.out.println("You chose console mode.");
        String eventName = getEventNameFromConsole();
        String eventDate = getEventDateFromConsole();
        String clientMail = getClientEmailFromConsole();
        Template template = new Template();
        Client client = new Client();
        template.setEventName(eventName);
        template.setDate(eventDate);
        client.setMail(clientMail);
        Messenger messenger = new Messenger(new MailServer(), new TemplateEngine());
        messenger.sendMessage(client, template);
    }
}
