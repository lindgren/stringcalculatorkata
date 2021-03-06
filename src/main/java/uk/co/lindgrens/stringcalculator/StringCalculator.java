package uk.co.lindgrens.stringcalculator;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {
    private static final int VALID_NUMBER_LOWER_BOUND = 0;
    private static final int VALID_NUMBER_UPPER_BOUND = 1000;

    public int add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        }

        return calculateSum(parseInput(numbers));
    }

    private List<Integer> parseInput(String numbers) {
        NumberSeriesParser numberSeriesParser = new NumberSeriesParser(numbers);
        return numberSeriesParser.getNumberSeries();
    }

   private int calculateSum(List<Integer> numbersList) {
        int total = 0;
        List<Integer> errors = new ArrayList<>();
        for(int num : numbersList) {
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