package uk.co.lindgrens.stringcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delimiter {
    private String delimiter;
    private static final String DEFAULT_DELIMITER = ",";
    private Pattern singleCharacterDelimiterPattern = Pattern.compile("//(.)\n");
    private Pattern multiDelimiterWithSquareBracketsPattern = Pattern.compile("\\[(.*?)\\]");

    public Delimiter(String numbers) {
        delimiter = selectDelimiter(numbers);
    }

    public String getDelimiter() {
        return delimiter;
    }

    private String selectDelimiter(String numbers) {
        if(isCustomDelimiter(numbers)) {
            return getCustomDelimiter(numbers);
        }

        return DEFAULT_DELIMITER;
    }

    private boolean isCustomDelimiter(String numbers) {
        return numbers.startsWith("//");
    }

    private String getCustomDelimiter(String numbers) {
        Matcher matcher = singleCharacterDelimiterPattern.matcher(numbers);
        if(matcher.find()) {
            return getEscapedDelimiter(matcher.group(1));
        }

        return getMultiDelimiterWithSquareBrackets(numbers);
    }

    private String getEscapedDelimiter(String delimiter) {
        return "\\Q" +delimiter +"\\E";
    }

    private String getMultiDelimiterWithSquareBrackets(String numbers) {
        Matcher matcher = multiDelimiterWithSquareBracketsPattern.matcher(numbers);
        List<String> listOfDelimiters = new ArrayList<>();
        while(matcher.find()) {
            listOfDelimiters.add(getEscapedDelimiter(matcher.group(1)));
        }

        return buildRegex(listOfDelimiters);
    }

    private String buildRegex(List<String> listOfDelimiters) {
        int pos = 0;
        StringBuilder delimiter = new StringBuilder();
        for(String del : listOfDelimiters) {
            delimiter.append(del);
            if(pos < listOfDelimiters.size() - 1) {
                delimiter.append("|");
            }
            pos++;
        }

        return delimiter.toString();
    }
}
