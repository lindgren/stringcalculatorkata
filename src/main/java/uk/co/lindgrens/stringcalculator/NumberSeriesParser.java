package uk.co.lindgrens.stringcalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberSeriesParser {
    private List<Integer> numbersList = new ArrayList<>();

    public NumberSeriesParser(String numbers) {
        numbersList = parse(numbers);
    }

    public List<Integer> getNumberSeries() {
        return numbersList;
    }

    private List<Integer> parse(String numbers) {
        String numberSeries = extractNumberSequence(numbers);
        String delimiter = getDelimiter(numbers);

        String sanitisedNumberSeries = sanitiseNumberSeries(delimiter, numberSeries);
        return getNumbersAsListOfStrings(delimiter, sanitisedNumberSeries);
    }

    private String extractNumberSequence(String numbers) {
        if(isNumberSerieWithoutCustomDelimiter(numbers)) {
            return numbers;
        }

        int startPos = numbers.indexOf("\n") + 1;
        return numbers.substring(startPos, numbers.length());
    }

    private boolean isNumberSerieWithoutCustomDelimiter(String numbers) {
        return !numbers.startsWith("//");
    }

    private String getDelimiter(String numbers) {
        Delimiter delimiter = new Delimiter(numbers);
        return delimiter.getDelimiter();
    }

    private String sanitiseNumberSeries(String delimiter, String numberSeries) {
        return numberSeries.replaceAll("\\n", delimiter);
    }

    private List<Integer> getNumbersAsListOfStrings(String delimiter, String numberSeries) {
        List<String> numbersAsList = Arrays.asList(numberSeries.split(delimiter));
        List<Integer> numbersList = new ArrayList<>();
        for(String val : numbersAsList) {
            numbersList.add(Integer.parseInt(val));
        }

        return numbersList;
    }
}
