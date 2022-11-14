package com.epam.testmodule;

/**
 * Mail server class.
 */
public class MailServer {

    /**
     * Send notification.
     *
     * @param addresses      the addresses
     * @param messageContent the message content
     */
    public String send(String addresses, String messageContent) {
        String send = "MessageContent is : {" + messageContent + "}" + " Sanded to addresses : " + addresses;
        System.out.println(send);
        return send;
    }
}
