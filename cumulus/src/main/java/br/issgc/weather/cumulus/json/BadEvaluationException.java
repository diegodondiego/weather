/**
 * ISSGC ®2014
 */
package br.issgc.weather.cumulus.json;

import static java.lang.String.format;

import javax.script.ScriptException;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * couldn't evaluate the js script in a good way. sorry. ):
 * 
 * @author dinhego
 *
 */
public class BadEvaluationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5159079717281706114L;

	/**
	 * 
	 * @param script
	 * @param e
	 */
	public BadEvaluationException(@NonNull String script, @NonNull Exception e) {
		// TODO log it
		super(format("Problem while evaluating the script [%s]: [%s].", script, e.getMessage()), e);
	}

	/**
	 * 
	 * @param script
	 * @param fieldNavUsingDots
	 * @param e
	 */
	public BadEvaluationException(String script, @NonNull String fieldNavUsingDots, ScriptException e) {
		// TODO log it
		super(format("Problem while evaluating the filed value [%s] at the script [%s]: [%s].", fieldNavUsingDots,
				script, e.getMessage()), e);
	}
}
