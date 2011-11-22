package com.jayway.hum;

import org.junit.Test;

import javax.script.*;
import java.io.Reader;

import static org.junit.Assert.assertEquals;

/**
 * This test verifies the initialization routine for Hum
 */
public class HumInitializationTest {

    @Test
    public void initializedWithoutEnabledShouldGenerateCorrectScript() {

        final EngineStub engine = new EngineStub();
        engine.result = 1L;

        Hum.setScriptEngineFactory(new AppleScriptEngineFactory() {
            public ScriptEngine getAppleScriptEngine() {
                return engine;
            }
        });

        Hum.registerApplication("Test").withAvailableNotificationTypes("one", "two").initialize();

        StringBuilder expected = new StringBuilder();
        expected.append("tell application id \"com.Growl.GrowlHelperApp\"");
        expected.append("\n");
        expected.append("set the availableList to {\"two\", \"one\"}");
        expected.append("\n");
        expected.append("set the enabledList to {}");
        expected.append("\n");
        expected.append("register as application \"Test\" all notifications availableList default notifications enabledList");
        expected.append("\n");
        expected.append("end tell");

        assertEquals(expected.toString(), engine.script);

    }

    private class EngineStub implements ScriptEngine {

        private String script;

        private Object result;

        public Object eval(String script, ScriptContext context) throws ScriptException {
            this.script = script;

            return result;
        }

        public Object eval(Reader reader, ScriptContext context) throws ScriptException {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.eval is not implemented.");
        }

        public Object eval(String script) throws ScriptException {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.eval is not implemented.");
        }

        public Object eval(Reader reader) throws ScriptException {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.eval is not implemented.");
        }

        public Object eval(String script, Bindings n) throws ScriptException {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.eval is not implemented.");
        }

        public Object eval(Reader reader, Bindings n) throws ScriptException {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.eval is not implemented.");
        }

        public void put(String key, Object value) {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.put is not implemented.");
        }

        public Object get(String key) {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.get is not implemented.");
        }

        public Bindings getBindings(int scope) {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.getBindings is not implemented.");
        }

        public void setBindings(Bindings bindings, int scope) {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.setBindings is not implemented.");
        }

        public Bindings createBindings() {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.createBindings is not implemented.");
        }

        public ScriptContext getContext() {
            return null;
        }

        public void setContext(ScriptContext context) {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.setContext is not implemented.");
        }

        public ScriptEngineFactory getFactory() {
            throw new UnsupportedOperationException("Method com.jayway.hum.HumInitializationTest.EngineStub.getFactory is not implemented.");
        }
    }
}
