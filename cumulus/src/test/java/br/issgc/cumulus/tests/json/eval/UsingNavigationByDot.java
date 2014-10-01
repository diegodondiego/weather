/**
 * ISSGC ®2014
 */
package br.issgc.cumulus.tests.json.eval;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import br.issgc.weather.cumulus.json.BadEvaluationException;
import br.issgc.weather.cumulus.json.NavigationByDot;

/**
 * Testing the class {@link NavigationByDot}
 * 
 * @author dinhego
 *
 */
public class UsingNavigationByDot {

	private String rawWundergroundJson;

	@Before
	public void readingJson() throws IOException, URISyntaxException {

		URL testingJson = this.getClass().getClassLoader().getResource("geolocation.json");

		assertTrue(testingJson != null);

		// using java8
		rawWundergroundJson = new String(Files.readAllBytes((Paths.get(testingJson.toURI()))));

		assertTrue(rawWundergroundJson != null && !rawWundergroundJson.isEmpty());
	}

	@Test(expected = BadEvaluationException.class)
	public void andNonJson() throws BadEvaluationException {

		NavigationByDot nav = new NavigationByDot("man, i'm no JSON.");

		fail("OMG. i have a navigation!" + nav);

	}

	@Test(expected = IllegalArgumentException.class)
	public void andEmptyJson() throws BadEvaluationException {

		NavigationByDot nav = new NavigationByDot("");

		fail("OMG. i have a navigation!" + nav);

	}

	@Test(expected = IllegalArgumentException.class)
	public void andNullJson() throws BadEvaluationException {

		NavigationByDot nav = new NavigationByDot(null);

		fail("OMG. i have a navigation!" + nav);

	}

	@Test
	public void withWundergroundJson() throws BadEvaluationException {

		NavigationByDot nav = new NavigationByDot(rawWundergroundJson);

		assertTrue(nav != null);
	}

	@Test
	public void withWundergroundJsonAndRetrievingCityName() throws BadEvaluationException {

		NavigationByDot nav = new NavigationByDot(rawWundergroundJson);

		assertTrue(nav != null);

		assertTrue("Rio de Janeiro".equals(nav.getFieldValue("location.city", String.class)));

	}

}
