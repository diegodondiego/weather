/**
 * ISSGC ®2014
 */
package br.issgc.weather.cumulus.json;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Class for json evaluation using javascript and navigation using dots.
 * 
 * @author dinhego
 *
 */
public class NavigationByDot {

	private String rawJson;

	/**
	 * javascript var name for the result of the js evaluation
	 */
	private String varName;

	private ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("nashorn");

	/**
	 * Construct the object just with the raw json as {@link String}.
	 * 
	 * @param rawJson
	 * @throws BadEvaluationException
	 */
	public NavigationByDot(String rawJson) throws BadEvaluationException {
		super();

		if (isEmpty(rawJson)) {

			throw new IllegalArgumentException("There is no json to navigate. ):");
		}

		this.rawJson = rawJson;

		evalJson();
	}

	/**
	 * Eval the raw json using nashorn
	 * 
	 * @throws BadEvaluationException
	 */
	private void evalJson() throws BadEvaluationException {

		generateJSVarName();

		StringBuilder evalJsonExpression = new StringBuilder();
		evalJsonExpression.append("var ");
		evalJsonExpression.append(this.varName);
		evalJsonExpression.append(" = eval('(");
		// remove any CRLF from the string
		evalJsonExpression.append(this.rawJson.replaceAll("\\n", " "));
		evalJsonExpression.append(")')");

		try {
			jsEngine.eval(evalJsonExpression.toString());
		} catch (ScriptException e) {
			throw new BadEvaluationException(this.rawJson, e);
		}
	}

	/**
	 * Generate a the json var name based on timestamp.
	 */
	private void generateJSVarName() {

		StringBuilder buildJsVarName = new StringBuilder();
		buildJsVarName.append("json").append(OffsetDateTime.now().getLong(ChronoField.MILLI_OF_DAY));

		this.varName = buildJsVarName.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NavigationByDot [");
		if (rawJson != null) {
			builder.append("rawJson=").append(rawJson).append(", ");
		}
		if (varName != null) {
			builder.append("varName=").append(varName);
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * evaluate the json navigation expression at the scope of the json
	 * 
	 * @param fieldNavUsingDots
	 *            navigation expresion
	 * @param returnClass
	 * @return
	 * @throws BadEvaluationException
	 */
	public <T> T getFieldValue(@NonNull String fieldNavUsingDots, @NonNull Class<T> returnClass)
			throws BadEvaluationException {

		try {
			Object result = jsEngine.eval(format("%s.%s", this.varName, fieldNavUsingDots));

			if (result == null) {
				return null;
			}

			if (result.getClass().equals(returnClass)) {
				return returnClass.cast(result);
			}

			throw new ClassCastException(format("the value returned by the json [%s] mismatch with the type "
					+ "asked as parameter [%s]. script: [%s]. field wanted: [%s].", result, returnClass, this.rawJson,
					fieldNavUsingDots));
		} catch (ScriptException e) {
			throw new BadEvaluationException(this.rawJson, fieldNavUsingDots, e);
		}

	}
}
