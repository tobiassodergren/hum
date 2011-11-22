package com.jayway.hum.api;

/**
 * Specification for the which of the available notification types that should be enabled by default.
 */
public interface EnabledNotificationTypesSpecificationInitializer extends HumInitializer {
    /**
     * Specifies which of the available notification types that should be enabled by default, hence produce
     * visible notifications directly without having to enable them in the Growl control panel.
     *
     * @param enabled A list of enabled notification types.
     * @return A HumInitializer.
     * @throws IllegalArgumentException If an enabled notification type is not in the list of the available
     *                                  notification types.
     */
    HumInitializer withEnabledNotificationTypes(String... enabled) throws IllegalArgumentException;
}
