package test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import fr.br.calc.services.QueryRunner

class QueryRunnerTest {
    @ParameterizedTest
    @CsvSource(
            "1+1, 2",
            "1 + 2, 3",
            "1 + -1, 0",
            "-1 - -1, 0",
            "5-4, 1",
            "5*2, 10",
            "(2+5)*3, 21",
            "10/2, 5",
            "2+2*5+5, 17",
            "2.8*3-1, 7.4",
            "2^8, 256",
            "2^8*5-1, 1279",
            "sqrt(4), 2",
            "1/0, ERROR"
    )

    fun testQueryRunner(input: String, expected: String) {
        Assertions.assertEquals(expected, QueryRunner.runFromString( input) )
    }

}