/**
 * ISSGC ®2015
 */
package br.issgc.weather.cumulus.factories;

import javax.inject.Inject;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;

import br.com.caelum.vraptor.Controller;

/**
 * Factory creating access to Wunderground API.
 * 
 * @author dinhego
 *
 */
@Controller
public class WundergroundFactory {

	@Inject
	private CloseableHttpClient httpClient;

	public WundergroundFactory() {
	}

	public HttpClient getHttpClient() {
		return this.httpClient;
	}

}
