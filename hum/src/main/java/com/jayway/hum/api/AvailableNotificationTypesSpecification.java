package com.jayway.hum.api;

/**
 * Specification for available notification types.
 * <p/>
 * A growl application may have one or more available notification types that can produce notifications.
 * Each notification type can be enabled or disabled in the growl control panel for the system.
 */
public interface AvailableNotificationTypesSpecification {
    /**
     * Specifies all available notification types that produces notifications.
     *
     * @param available The available notification types.
     * @return An EnabledSpecificationInitializer.
     */
    EnabledNotificationTypesSpecificationInitializer withAvailableNotificationTypes(String... available);
}
