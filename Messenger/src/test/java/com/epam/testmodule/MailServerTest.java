package com.epam.testmodule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MailServerTest {

    @Test
    void send_WhenSendMessage_ShouldReturnCorrectString() {
        //given
        MailServer mailServer = new MailServer();
        String addresses = "client@gmail.com";
        String messageContent = "Content";
        String expected = "MessageContent is : {" + "Content" + "}" + " Sanded to addresses : " + "client@gmail.com";
        //when
        String extracted = mailServer.send(addresses,messageContent);
        //assert
        Assertions.assertEquals(expected,extracted);
    }
}