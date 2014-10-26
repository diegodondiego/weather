/**
 * ISSGC ®2014
 */
package br.issgc.cumulus.tests.wunderground;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;

/**
 * 
 * 
 * @author dinhego
 *
 */
public class Access {

	protected final String API_END_POINT = "http://api.wunderground.com/api/";

	protected String apiKey;

	@Before
	public void loadAPIKEY() throws IOException {

		InputStream apiConfiguraion = this.getClass().getClassLoader().getResourceAsStream("wunderground.properties");

		Properties configurationProperties = new Properties();
		configurationProperties.load(apiConfiguraion);

		apiKey = (String) configurationProperties.get("api.key");
		assertTrue(!StringUtils.isEmpty(apiKey));

	}
}
