package uk.co.lindgrens.stringcalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private static final String DEFAULT_DELIMITER = ",";
    private static final int VALID_NUMBER_LOWER_BOUND = 0;
    private static final int VALID_NUMBER_UPPER_BOUND = 1000;

    public int add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        }

        return calculateSum(parseInput(numbers));
    }

    private List<String> parseInput(String numbers) {
        String numberSeries = extractNumberSequence(numbers);
        String delimiter = getDelimiter(numbers);

        String sanitisedNumberSeries = sanitiseNumberSeries(delimiter, numberSeries);
        return getNumbersAsListOfStrings(delimiter, sanitisedNumberSeries);
    }


    private String extractNumberSequence(String numbers) {
        if(isDefaultDelimiter(numbers)) {
            return numbers;
        }

        int startPos = numbers.indexOf("\n") + 1;
        return numbers.substring(startPos, numbers.length());
    }

    private boolean isDefaultDelimiter(String numbers) {
        return !numbers.startsWith("//");
    }

    private String getDelimiter(String numbers) {
        Pattern pattern = Pattern.compile("//\\[?(.*?)\\]?\n");
        Matcher matcher = pattern.matcher(numbers);

        if(matcher.find()) {
            return "\\Q" +matcher.group(1) +"\\E";
        }

        return DEFAULT_DELIMITER;
    }

    private String sanitiseNumberSeries(String delimiter, String numberSeries) {
        return numberSeries.replaceAll("\\n", delimiter);
    }

    private List<String> getNumbersAsListOfStrings(String delimiter, String numberSeries) {
        return Arrays.asList(numberSeries.split(delimiter));
    }

   private int calculateSum(List<String> numbersList) {
        int total = 0;
        List<Integer> errors = new ArrayList<>();
        for(String val : numbersList) {
            int num = Integer.parseInt(val);
            reportNegativeNumber(errors, num);
            total = calculateNewTotal(total, num);
        }

        throwExceptionIfNegativeNumbersAreReported(errors);
        return total;
    }

    private void reportNegativeNumber(List<Integer> errors, int num) {
        if(num < 0) {
            errors.add(num);
        }
    }

    private int calculateNewTotal(int sum, int num) {
        if(num >= VALID_NUMBER_LOWER_BOUND && num <= VALID_NUMBER_UPPER_BOUND) {
            sum +=num;
        }

        return sum;
    }

    private void throwExceptionIfNegativeNumbersAreReported(List<Integer> errors) {
        if (errors.isEmpty()) {
            return;
        }

        int pos = 0;
        StringBuilder exceptionMessage = new StringBuilder();
        for(int negativeNum : errors) {
            writeExceptionMessage(errors.size(), pos, exceptionMessage, negativeNum);
            pos++;
        }

        throw new IllegalArgumentException(exceptionMessage.toString());
    }

    private void writeExceptionMessage(int sizeOfList, int pos, StringBuilder exceptionMessage, int negativeNum) {
        exceptionMessage.append(negativeNum);

        if (pos < sizeOfList - 1) {
            exceptionMessage.append(",");
        }
    }
}
