/**
 * ISSGC ®2015
 */
package br.issgc.weather.cumulus.conf;

/**
 * that's all the endpoint urls for the data features at wunderground API: <a
 * href="http://www.wunderground.com/weather/api/d/docs?d=data/index">link</a>.
 * 
 * TODO define if the urls should be placed at a configuration file
 * 
 * @author dinhego
 */
public enum WundergroundEndpointFeatures {

	CONDITION("condition");

	private String featureUrl;

	/**
	 * 
	 * @param featureUrl
	 */
	private WundergroundEndpointFeatures(String featureUrl) {
		this.featureUrl = featureUrl;
	}

	/**
	 * 
	 * @return the value of the featured url.
	 */
	public String url() {
		return this.featureUrl;
	}
}
