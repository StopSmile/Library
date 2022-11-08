package com.epam.ld.module2.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MailServerTest {

    @Test
    void send_WhenSendMessage_ShouldReturnCorrectString() {
        MailServer mailServer = new MailServer();
        String addresses = "client@gmail.com";
        String messageContent = "Content";
        String expected = "MessageContent is : {" + "Content" + "}" + " Sanded to addresses : " + "client@gmail.com";
        String extracted = mailServer.send(addresses,messageContent);
        Assertions.assertEquals(expected,extracted);
    }
}