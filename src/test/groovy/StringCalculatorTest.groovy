import spock.lang.Specification
import spock.lang.Unroll
import uk.co.lindgrens.stringcalculator.StringCalculator

class StringCalculatorTest extends Specification {
    StringCalculator stringCalculator

    def setup() {
        stringCalculator = new StringCalculator()
    }

    @Unroll
    def "should calculate the sum of numbers"() {
        expect:
        stringCalculator.add(NUMBERS) == SUM

        where:
        NUMBERS                 | SUM
        ''                      | 0
        '1'                     | 1
        '1,2'                   | 3
        '1\n2,3'                | 6
        '//;\n1;2'              | 3
        '//[***]\n1***2***3'    | 6
    }

    def "should throw exception for negative numbers"() {
        given:
        String negativeNumbers = '-1,-2,-3'

        when:
        stringCalculator.add(negativeNumbers)

        then:
        Exception exception = thrown(IllegalArgumentException)
        exception.message == '-1,-2,-3'
    }

    def "should ignore numbers bigger than 1000"() {
        given:
        String numbers = '2,1000,1001'

        when:
        int sum = stringCalculator.add(numbers)

        then:
        sum == 1002
    }
}
