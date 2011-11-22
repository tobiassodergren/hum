package com.jayway.hum;

import javax.script.ScriptEngine;

/**
 * Interface for a factory for getting an AppleScript engine.
 */
interface AppleScriptEngineFactory {
    ScriptEngine getAppleScriptEngine();
}
