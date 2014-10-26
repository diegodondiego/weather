/**
 * Oi. Copie livremente, mas cite a fonte!
 * @CC 2014
 * 
 */
package br.issgc.cumulus.tests.wunderground;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

/**
 * First test using the wunderground weather api. Put you wunderground api key
 * on a wunderground.properties at src/tests/resources
 * 
 * @author dinhego
 *
 */
public class Hello extends Access {

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
	public void uberloveConditions() throws ClientProtocolException, IOException {

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
