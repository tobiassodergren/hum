package com.jayway.hum;

import com.jayway.hum.api.AvailableNotificationTypesSpecification;
import com.jayway.hum.api.EnabledNotificationTypesSpecificationInitializer;
import com.jayway.hum.api.NotificationProducer;
import com.jayway.hum.api.HumInitializer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the main class for the Hum API.
 * <p/>
 * In order to use Hum, perform the following steps:
 * <p/>
 * <ol>
 * <li>Check if Growl/AppleScript is supported on the system by calling {@link #isGrowlSupported()}</li>
 * <li>Configure the Growl application that should produce notifications by calling {@link #registerApplication(String)}</li>
 * <li>Use the {@link com.jayway.hum.api.NotificationProducer}, which is returned when the application is configured, to create notifications.</li>
 * </ol>
 * <p/>
 * Copyright 2011 Tobias Södergren
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Hum implements NotificationProducer {
    private static final String GROWL_APPLICATION = "com.Growl.GrowlHelperApp";
    private static AppleScriptEngineFactory appleScriptEngineFactory = new DefaultEngineFactory();
    private ScriptEngine appleScriptEngine;
    private final String applicationName;

    /**
     * Registers an application in Growl.
     *
     * @param applicationName The name of the application.
     * @return An AvailableNotificationTypesSpecification to specify the available notification types.
     */
    public static AvailableNotificationTypesSpecification registerApplication(String applicationName) {
        return new HumInitializerImplNotificationTypes(applicationName);
    }

    /**
     * Checks whether Growl and/or ApplesScript is supported for the system.
     *
     * @return True if both AppleScript is avalable and Growl is installed, false otherwise.
     */
    public static boolean isGrowlSupported() {
        ScriptEngine engine = appleScriptEngineFactory.getAppleScriptEngine();

        if (engine == null) {
            return false;
        }

        long count = script(engine).add("tell application ")
                .quote("System Events")
                .nextRow("return count of (every process whose bundle identifier is ")
                .quote(GROWL_APPLICATION).add(") > 0")
                .nextRow("end tell")
                .execute(0L);

        return count > 0;
    }

    /**
     * Private constructor, use {@link #registerApplication(String)} to create an instance.
     *
     * @param applicationName The name of the Growl application.
     */
    private Hum(String applicationName) {
        this.applicationName = applicationName;
        appleScriptEngine = appleScriptEngineFactory.getAppleScriptEngine();
    }

    public void notify(String notificationType, String title, String message) {
        script(appleScriptEngine).add("tell application id ")
                .quote(GROWL_APPLICATION)
                .nextRow("notify with name ").quote(notificationType)
                .add(" title ").quote(title)
                .add(" description ").quote(message)
                .add(" application name ").quote(applicationName)
                .nextRow("end tell").execute();
    }

    static void setScriptEngineFactory(AppleScriptEngineFactory factory) {
        appleScriptEngineFactory = factory;
    }

    private static class HumInitializerImplNotificationTypes implements HumInitializer, AvailableNotificationTypesSpecification, EnabledNotificationTypesSpecificationInitializer {

        private Set<String> availableSet = new HashSet<String>();
        private Set<String> enabledSet = new HashSet<String>();
        private final String applicationName;

        private HumInitializerImplNotificationTypes(String applicationName) {
            this.applicationName = applicationName;
        }

        public EnabledNotificationTypesSpecificationInitializer withAvailableNotificationTypes(String... available) {
            for (String element : available) {
                if (element == null) {
                    throw new IllegalArgumentException("The available string must not be null");
                }

                availableSet.add(element);
            }

            return this;
        }

        public HumInitializer withEnabledNotificationTypes(String... enabled) {
            for (String element : enabled) {
                if (element == null) {
                    throw new IllegalArgumentException("The enabled string must not be null");
                }

                if (!availableSet.contains(element)) {
                    throw new IllegalArgumentException(
                            String.format("The string '%s' was not specified as being an available type", element));
                }

                enabledSet.add(element);
            }

            return this;
        }

        public NotificationProducer initialize() {
            if (!isGrowlSupported()) {
                throw new IllegalArgumentException("Growl is not supported on this system");
            }

            script().add("tell application id ")
                    .quote(GROWL_APPLICATION)
                    .nextRow("set the availableList to ")
                    .array(availableSet)
                    .nextRow("set the enabledList to ")
                    .array(enabledSet)
                    .nextRow("register as application ")
                    .quote(applicationName)
                    .add(" all notifications availableList default notifications enabledList")
                    .nextRow("end tell")
                    .execute();

            return new Hum(applicationName);
        }
    }

    private static Script script() {
        return new Script(appleScriptEngineFactory.getAppleScriptEngine());
    }

    private static Script script(ScriptEngine engine) {
        return new Script(engine);
    }

    /**
     * This is the default factory for getting an AppleScript engine.
     */
    private static class DefaultEngineFactory implements AppleScriptEngineFactory {

        public ScriptEngine getAppleScriptEngine() {
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            return scriptEngineManager.getEngineByName("AppleScript");
        }

    }
}
