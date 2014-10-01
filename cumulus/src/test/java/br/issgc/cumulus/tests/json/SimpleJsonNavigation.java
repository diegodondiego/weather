/**
 * Oi. Copie livremente, mas cite a fonte!
 * @CC 2014
 * 
 */
package br.issgc.cumulus.tests.json;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Navigating on json without deserialization.
 * 
 * @author dinhego
 *
 */
public class SimpleJsonNavigation {

	private String rawSimpleJson;

	private ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("nashorn");

	@Before
	public void readingJson() throws IOException {

		InputStream testingJson = this.getClass().getClassLoader().getResourceAsStream("simple.json");

		assertTrue(testingJson != null);

		StringWriter stringWriter = new StringWriter();

		IOUtils.copy(testingJson, stringWriter, "UTF-8");

		rawSimpleJson = stringWriter.toString();

		assertTrue(rawSimpleJson != null && !rawSimpleJson.isEmpty());
	}

	@Test
	public void simpleEval() throws ScriptException {

		StringBuilder builder = new StringBuilder();
		builder.append("var test = eval('(");
		builder.append(rawSimpleJson.replaceAll("\\n", ""));
		builder.append(")')");

		jsEngine.eval(builder.toString());

		// validates if the scope variable 'test' was created with the json
		// valuess
		Set<String> createdObjects = jsEngine.getBindings(ScriptContext.ENGINE_SCOPE).keySet();
		assertTrue(createdObjects != null && createdObjects.contains("test"));

		Object json = jsEngine.eval("test.field1");

		assertTrue(json != null && json instanceof String && "value".equals(json));

	}

	@Test
	public void usingNashorn() throws ScriptException, NoSuchMethodException {

		// json evaluator
		Object jsonEvaluation = jsEngine.eval("JSON");
		Invocable invocable = (Invocable) jsEngine;

		StringBuilder builder = new StringBuilder();
		builder.append(rawSimpleJson);

		Object data = invocable.invokeMethod(jsonEvaluation, "parse", builder.toString());
		assertTrue(data != null);

	}
}