package com.jayway.hum.api;

/**
 * Initializes the Hum framework and produces an Notifier instance.
 */
public interface HumInitializer {
    /**
     * Initializes the Hum framework.
     *
     * @return A Notifier instance where notifications may be created.
     */
    NotificationProducer initialize();
}
