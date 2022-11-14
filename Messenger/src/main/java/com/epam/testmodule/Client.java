package com.epam.testmodule;

import lombok.Data;



/**
 * The type Client.
 */
@Data
public class Client {
    private String mail;

    /**
     * Gets addresses.
     *
     * @return the addresses
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets addresses.
     *
     * @param mail the addresses
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

}
