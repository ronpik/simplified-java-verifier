package oop.ex6.globalReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	public static final int EMPTY_LINE = 1;
	public static final int COMMENT = 2;
	public static final int VARIABLE = 3;
	public static final int METHOD_DECLARTION = 4;
	public static final int METHOD_CALL = 5;
	public static final int IF_OR_WHILE = 6;
	public static final int RETURN = 7;
	public static final int CLOSE_BRACE = 8; // braces = '{' & '}'
	public static final Pattern METHOD_RECOGNIZER = Pattern.compile(Constants.METHOD_RECOGNIZER);
	public static final Pattern LEGAL_METHOD_CALL = Pattern.compile(Constants.METHOD_CALL);
	public static final Pattern CONDITION= Pattern.compile(Constants.COND_DECLARE);
	public static final Pattern RETURN_PATT = Pattern.compile(Constants.RETURN);
	public static final Pattern CLOSE_BLOCK = Pattern.compile(Constants.CLOSE_SCOPE);
	
	public static final Pattern variablePattern = Pattern.compile("(?:\\s*(final)\\s+)?\\b(int|String|double|char|boolean)?"
			+ "\\b((?:\\s*(?:_[A-Za-z0-9_]+|[A-Za-z][A-Za-z0-9_]*)\\s*(?:=(?:\\s*(?:[^," + '"' + ";\\s]++|"
			+ "[" + '"' + "][^" + '"' + "]++[" + '"' + "])+))?\\s*,)*)\\s*(?:(_[A-Za-z0-9_]+|[A-Za-z]"
					+ "[A-Za-z0-9_]*)\\s*(?:=(?:\\s*([^," + '"' + ";\\s]+|[" + '"' + "][^" + '"' + "]++[" + '"' + "])+))?\\s*);\\s*");
	
	
	public static Matcher variableMatcher = null;
	/*  */
	
	public Tools() {
	}
	
	 /* checkLine
	 * write about it in the readme. open-close
	 * easy to take care of exceptions and syntes errors.
	 */
	public static int checkLine(String givenLine, int lineNumber) throws ReaderUnknownRowException {
		//System.out.println(lineNumber + " " + givenLine);
		if (checkForEmptyLine(givenLine)) {
			return EMPTY_LINE;
		} else if (checkForCommentLine(givenLine)) {
			return COMMENT;
		} else if (RETURN_PATT.matcher(givenLine).matches()) {
			return RETURN;
		} else if (checkForVariableLine(givenLine)) {
			return VARIABLE;
		} else if (METHOD_RECOGNIZER.matcher(givenLine).matches()){
			return METHOD_DECLARTION;
		} else if (LEGAL_METHOD_CALL.matcher(givenLine).matches()) {
			return METHOD_CALL;
		} else if (CONDITION.matcher(givenLine).matches()) {
			return IF_OR_WHILE;
		} else if (CLOSE_BLOCK.matcher(givenLine).matches()) {
			return CLOSE_BRACE;
		} else {
			throw new ReaderUnknownRowException(lineNumber);
		}
	}
	
	
	// CheckLine's function assistant functions - BEGINNING //
	
	/**
	 * Skip empty lines, lines with tabs or spaces.
	 * @param line
	 * @return true or false
	 */
	private static boolean checkForEmptyLine(String line) {
		return line.matches("\\s*"); // update the constant here;
	}
	
	
	/**
	 * Check if line begins with '//'.
	 * @param line
	 * @return true or false;
	 */
	private static boolean checkForCommentLine(String line) {
		return line.startsWith("//");
	}
	
	
	/**
	 * Checks if line is a 'variable' type line.
	 * @return true or false
	 */
	private static boolean checkForVariableLine(String currentLine) {
		variableMatcher = variablePattern.matcher(currentLine);
		if (variableMatcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Matcher getVarMatcher() {
		return variableMatcher;
	}
	
	public static Pattern getVarPattern() {
		return variablePattern;
	}
	
	// CheckLine's function assistant functions - END //
	
	
}