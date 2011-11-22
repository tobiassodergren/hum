package com.jayway.hum.examples;

import com.jayway.hum.Hum;
import com.jayway.hum.api.NotificationProducer;

/**
 * User: tobias
 * Date: 11/21/11
 * Time: 9:44 PM
 */
public class HumExample {
    public static void main(String[] args) {
        if (!Hum.isGrowlSupported()) {
            System.err.println("Your system does not support Hum");
            return;
        }

        NotificationProducer notificationProducer = Hum.registerApplication("My Java application with Growl")
                .withAvailableNotificationTypes("important", "spam")
                .withEnabledNotificationTypes("important")
                .initialize();

        notificationProducer.notify("important", "Hello", "There");
    }
}
