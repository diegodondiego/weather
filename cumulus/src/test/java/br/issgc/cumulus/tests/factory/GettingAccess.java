/**
 * ISSGC ®2015
 */
package br.issgc.cumulus.tests.factory;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.issgc.weather.cumulus.factories.WundergroundFactory;

/**
 * Tests for a the factory of access for Wunderground API
 * 
 * @author dinhego
 *
 */
public class GettingAccess {

	@Test
	public void usingFactory() {

		WundergroundFactory factory = new WundergroundFactory();

		assertTrue("factory null!", factory != null);
	}
}
