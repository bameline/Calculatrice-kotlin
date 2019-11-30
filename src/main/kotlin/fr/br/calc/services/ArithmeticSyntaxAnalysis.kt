package fr.br.calc.services

import fr.br.calc.enums.MathElementEnum
import fr.br.calc.model.AbstractMathElement
import fr.br.calc.model.NumericElement
import fr.br.calc.model.Operation
import fr.br.calc.model.OperatorElement
import org.slf4j.LoggerFactory
import java.lang.Exception

object ArithmeticSyntaxAnalysis {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val ALPHABET_CHARACTERS_REGEX = "[a-zA-Z]".toRegex()
    private val NUMERIC_CHARACTERS_REGEX = "[0-9.,]".toRegex()
    private val OPERATOR_CHARACTERS_REGEX = "[-+*/^]".toRegex()
    private val PARENTHESIS_CHARACTER_REGEX = "[()]".toRegex()

    /**
     * Get query as operation instance by lexial analysis
     */
    fun checkForOperation( queryCharArray : List<Char> ) : Operation {

        var operationElementsList = arrayListOf<AbstractMathElement>()
        var breakLoop : Boolean = false;
        var index = 0
        try {
            while (index < queryCharArray.size && !breakLoop) {
                val currentChar = queryCharArray[index].toString()

                // If numeric character found
                if (NUMERIC_CHARACTERS_REGEX.matches(currentChar)) {

                    // Check for a numeric element from this
                    var newNumericElement = checkForNumericElement(queryCharArray.subList(index, queryCharArray.size))
                    operationElementsList.add(newNumericElement)

                    index += newNumericElement.getExpressionSize()
                }

                // If alphabetic character found
                else if (ALPHABET_CHARACTERS_REGEX.matches(currentChar)) {
                    var newFunctionElement = checkForFunction(queryCharArray.subList(index, queryCharArray.size))
                    operationElementsList.add(newFunctionElement)

                    index += newFunctionElement.getExpressionSize()
                }

                // If operator element found
                else if (OPERATOR_CHARACTERS_REGEX.matches(currentChar)) {

                    //Special case of negative number
                    // TODO replace by enum
                    if( currentChar.equals("-") && ( operationElementsList.lastOrNull() == null|| operationElementsList.last().mathElementType != MathElementEnum.Number) ){
                        // Then its a negative number
                        // Get the number expression
                        var newNumericElement = checkForNumericElement(queryCharArray.subList(index + 1, queryCharArray.size))
                        //turn it negative
                        newNumericElement.value = "-" + newNumericElement.value
                        operationElementsList.add(newNumericElement)

                        index += newNumericElement.getExpressionSize()
                    }
                    else {
                            // Operators are expected to be one character, so we just add it
                            operationElementsList.add(OperatorElement(MathElementEnum.Operator, currentChar))
                            index++
                        }
                }

                // If parenthesis found
                else if (PARENTHESIS_CHARACTER_REGEX.matches(currentChar)) {

                    // If right left parenthesis = new sub-operation
                    // TODO replace by enum
                    if (currentChar.equals("(") ) {
                        // Recursive call to get nested operations as operation object
                        var subOperation: Operation = checkForOperation(queryCharArray.subList(index + 1, queryCharArray.size))

                        // Add parenthesis around the nested operation
                        var subOperationTotalElementsList = arrayListOf<AbstractMathElement>()
                        subOperationTotalElementsList.add(OperatorElement(MathElementEnum.Parenthesis, queryCharArray[index].toString()))
                        subOperationTotalElementsList.addAll( subOperation.mathElements )
                        subOperation.mathElements = subOperationTotalElementsList

                        // Add sub operation to operation list
                        operationElementsList.add(subOperation)
                        index += subOperation.getExpressionSize()
                    }
                    // else end of nested operation, return result
                    // TODO replace by enum
                    else if (currentChar.equals(")")) {
                        operationElementsList.add(OperatorElement(MathElementEnum.Parenthesis, currentChar))
                        breakLoop = true;

                    }
                }
                else{
                    // TODO throw custom exception
                }

            }
        }
        catch (e: Exception) {
            logger.error(e.message.toString())
            throw e
        }

        return Operation(MathElementEnum.Operation, operationElementsList)

    }

    /**
     * Get a numeric OperationElement
     */
    private fun checkForNumericElement( queryCharArray : List<Char>  ) : NumericElement {
        var numericElement : String = ""
        var endOfNumber = false
        var index = 0

        while (index < queryCharArray.size && !endOfNumber ) {
            val currentChar = queryCharArray[index].toString()
            if ( NUMERIC_CHARACTERS_REGEX.matches(currentChar) ) {
                numericElement += currentChar
                index++
            }
            else{
                endOfNumber = true
            }
        }
        return NumericElement(MathElementEnum.Number, numericElement);
    }

    /**
     * get a Fonction operationElement
     */
    private fun checkForFunction( queryCharArray : List<Char> ) : OperatorElement {
        var functionName : String = ""
        var endOfFunctionName = false
        var index = 0

        while (index < queryCharArray.size && !endOfFunctionName ) {
            val currentChar = queryCharArray[index].toString()
            if ( ALPHABET_CHARACTERS_REGEX.matches(currentChar) ) {
                functionName += currentChar
                index++
            }
            else{
                endOfFunctionName = true
            }
        }
        return OperatorElement(MathElementEnum.Function, functionName)
    }
}