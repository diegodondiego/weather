/**
 * ISSGC ®2014
 */
package br.issgc.cumulus.tests.wunderground;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import br.issgc.weather.cumulus.json.BadEvaluationException;
import br.issgc.weather.cumulus.json.NavigationByDot;

/**
 * getting forecast for Rio de Janeiro using the navigation by dot on JSON
 * 
 * @author dinhego
 *
 */
public class GetForescastForRio extends Access {

	/**
	 * retrieve forecast @ wundergound and eval as JS and return the forecast
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws BadEvaluationException
	 */
	@Test
	public void isRainingInThere() throws ClientProtocolException, IOException, BadEvaluationException {

		StringBuilder builder = new StringBuilder();

		builder.append(API_END_POINT);
		builder.append(apiKey);
		builder.append("/forecast/q/Rio_de_Janeiro.json");

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		HttpGet getConditions = new HttpGet(builder.toString());

		CloseableHttpResponse response = httpClient.execute(getConditions);

		assertTrue(response != null && response.getStatusLine().getStatusCode() == 200);

		String forecastForRio = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));

		assertTrue("couldn't retrieve the forecast", !isEmpty(forecastForRio));

		NavigationByDot nav = new NavigationByDot(forecastForRio);

		String conditionsNow = nav.getFieldValue("forecast.simpleforecast.forecastday[0].conditions", String.class);

		assertTrue("): no conditions at RJ.", !isEmpty(conditionsNow));

		System.out.println(format("is raining on Rio? %s", conditionsNow));

	}
}
