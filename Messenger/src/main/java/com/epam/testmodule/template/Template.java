package com.epam.testmodule.template;

import lombok.Data;

/**
 * The type Template.
 */
@Data
public class Template {
    private String eventName;
    private String date;

    public String getEvent() {
        return eventName;
    }
    public void setEventName(String event) {
        this.eventName = event;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
