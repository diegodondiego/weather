/**
 * Oi. Copie livremente, mas cite a fonte!
 * @CC 2014
 * 
 */
package br.issgc.cumulus.tests.firstssteps;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * First test using the wunderground weather api. Put you wunderground api key
 * on a wunderground.properties at src/tests/resources
 * 
 * @author dinhego
 *
 */
public class HelloWunderground {

	private final String API_END_POINT = "http://api.wunderground.com/api/";

	private String apiKey;

	@Before
	public void loadAPIKEY() throws IOException {

		InputStream apiConfiguraion = this.getClass().getClassLoader().getResourceAsStream("wunderground.properties");

		Properties configurationProperties = new Properties();
		configurationProperties.load(apiConfiguraion);

		apiKey = (String) configurationProperties.get("api.key");
		assertTrue(!StringUtils.isEmpty(apiKey));

	}

	/**
	 * testing geolocation
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@Test
	public void findMyGeolocation() throws ClientProtocolException, IOException {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(API_END_POINT);
		stringBuilder.append(apiKey);
		stringBuilder.append("/geolookup/q/autoip.json");

		HttpClient httpClient = HttpClientBuilder.create().build();

		HttpGet getConditions = new HttpGet(stringBuilder.toString());

		CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(getConditions);

		assertTrue(response != null && response.getStatusLine().getStatusCode() == 200);

		response.close();

	}

	/**
	 * rio de janeiro weather conditions
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void cariocaConditions() throws ClientProtocolException, IOException {

		StringBuilder builder = new StringBuilder();

		builder.append(API_END_POINT);
		builder.append(apiKey);
		builder.append("/conditions/q/Uberlandia.json");

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		HttpGet getConditions = new HttpGet(builder.toString());

		CloseableHttpResponse response = httpClient.execute(getConditions);

		assertTrue(response != null && response.getStatusLine().getStatusCode() == 200);

		// System.out.println(EntityUtils.toString(response.getEntity()));

		response.close();

	}
}
