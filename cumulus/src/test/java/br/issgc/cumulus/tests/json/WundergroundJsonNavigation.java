/**
 * ISSGC ®2014
 */
package br.issgc.cumulus.tests.json;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Navigating on the wunderground geolocation json
 * 
 * @author dinhego
 *
 */
public class WundergroundJsonNavigation {

	private ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("nashorn");

	private String rawWundergroundJson;

	@Before
	public void readingJson() throws IOException {

		InputStream testingJson = this.getClass().getClassLoader().getResourceAsStream("geolocation.json");

		assertTrue(testingJson != null);

		StringWriter stringWriter = new StringWriter();

		IOUtils.copy(testingJson, stringWriter, "UTF-8");

		rawWundergroundJson = stringWriter.toString();

		assertTrue(rawWundergroundJson != null && !rawWundergroundJson.isEmpty());
	}

	@Test
	public void loadAndRetrieveLocation() throws ScriptException {

		StringBuilder builder = new StringBuilder();
		builder.append("var geo = eval('(");
		builder.append(rawWundergroundJson.replaceAll("\\n", ""));
		builder.append(")')");

		jsEngine.eval(builder.toString());

		Object geoLocation = jsEngine.eval("geo");
		assertTrue(geoLocation != null);

		if (!(geoLocation instanceof ScriptObjectMirror)) {
			fail("geolocation is not a ScriptObjectMirror");
		}

		assertTrue("Rio de Janeiro".equals(jsEngine.eval("geo.location.city")));
	}
}
