package fr.br.calc.services

import fr.br.calc.enums.MathElementEnum
import fr.br.calc.model.AbstractMathElement
import fr.br.calc.model.NumericElement
import fr.br.calc.model.OperatorElement
import org.slf4j.LoggerFactory
import java.util.*
import java.util.ArrayDeque
import java.util.Deque
import kotlin.math.pow
import kotlin.math.sqrt


object RpnListComputer {
    private val logger = LoggerFactory.getLogger(javaClass)
    /**
     * Compute list of math elements RPN ordered
     */
    fun computeRPNMathElementsList ( listOfMathElements: ArrayDeque<AbstractMathElement> ) : Double {
        var resultStack = ArrayDeque<Double>()

        try {

            while (!listOfMathElements.isEmpty()) {
                // Get next element from list
                var currentElement = listOfMathElements.pop()

                // If its a number ...
                if (currentElement.mathElementType == MathElementEnum.Number) {
                    // Cast it to double and add push it to result stack
                    resultStack.push((currentElement as NumericElement).value.toDouble())
                }
                // If operator ...
                else if (currentElement.mathElementType == MathElementEnum.Operator) {
                    // get the two prevedent elements
                    when ((currentElement as OperatorElement).value) {
                        // TODO replace with enums
                        "+" -> operateAdd(resultStack)
                        "-" -> operateSub(resultStack)
                        "*" -> operateMultiplication(resultStack)
                        "/" -> operateDiv(resultStack)
                        "^" -> operateExponent(resultStack)
                    }
                }
                // If function ...
                else if (currentElement.mathElementType == MathElementEnum.Function) {
                    when ((currentElement as OperatorElement).value) {
                        // TODO replace with enums
                        "sqrt" -> operateSquareRoot(resultStack)
                    }
                }
            }
        }
        catch (e : Exception){
            logger.error(e.message.toString())
            throw e

        }

        return resultStack.pop()
    }

    /**
     *
     */
    private fun operateAdd(rpnList: Deque<Double>) {
        rpnList.push( rpnList.pop() + rpnList.pop() )
    }

    /**
     *
     */
    private fun operateSub(rpnList: Deque<Double>) {
        val right = rpnList.pop()
        val left = rpnList.pop()
        rpnList.push( left - right )
    }

    /**
     *
     */
    private fun operateMultiplication(rpnList: Deque<Double>) {
        rpnList.push( rpnList.pop() * rpnList.pop() )
    }

    /**
     *
     */
    private fun operateDiv(rpnList: Deque<Double>) {
        val right = rpnList.pop()
        val left = rpnList.pop()
        rpnList.push( left / right )
    }

    /**
     *
     */
    private fun operateExponent(rpnList: Deque<Double>) {
        val right = rpnList.pop()
        val left = rpnList.pop()
        rpnList.push( left.pow(right) )
    }

    /**
     *
     */
    private fun operateSquareRoot(rpnList: Deque<Double>) {
        rpnList.push( sqrt(rpnList.pop()) )
    }
}