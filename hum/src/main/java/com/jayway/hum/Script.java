package com.jayway.hum;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.Collection;

/**
 * A package-protected class for creating and executing AppleScripts.
 */
class Script {
    private StringBuilder builder = new StringBuilder();
    private final ScriptEngine engine;

    Script(ScriptEngine engine) {
        this.engine = engine;
    }

    Script add(String text) {
        builder.append(text);
        return this;
    }

    Script quote(String text) {
        builder.append("\"");
        builder.append(text);
        builder.append("\"");
        return this;
    }

    Script nextRow(String text) {
        builder.append("\n");
        builder.append(text);
        return this;
    }

    Script array(String[] array) {
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("\"");
            builder.append(array[i]);
            builder.append("\"");
        }

        builder.append("}");
        return this;
    }

    Script array(Collection<String> collection) {
        array(collection.toArray(new String[collection.size()]));
        return this;
    }

    @SuppressWarnings("unchecked")
    <T> T execute(T defaultValue) {
        try {
            return (T) engine.eval(builder.toString(), engine.getContext());
        } catch (ScriptException e) {
            return defaultValue;
        }
    }

    void execute() {
        try {
            engine.eval(builder.toString(), engine.getContext());
        } catch (ScriptException e) {
            // Do nothing
        }
    }

}
