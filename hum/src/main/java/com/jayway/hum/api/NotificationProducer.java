package com.jayway.hum.api;

/**
 * The API for creating notifications in Growl.
 */
public interface NotificationProducer {
    /**
     * Creates a Growl notification.
     *
     * @param notificationType The notification type, which should be one specified in the
     *                         {@link AvailableNotificationTypesSpecification} specification.
     * @param title            The notification title.
     * @param message          The notification message.
     */
    void notify(String notificationType, String title, String message);
}
