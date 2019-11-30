package fr.br.calc.services

import fr.br.calc.enums.MathElementEnum
import fr.br.calc.errors.CustomException
import fr.br.calc.model.AbstractMathElement
import fr.br.calc.model.NumericElement
import fr.br.calc.model.Operation
import fr.br.calc.model.OperatorElement
import fr.br.calc.utils.Utils
import org.slf4j.LoggerFactory
import java.util.ArrayDeque
import java.util.Stack


object QueryRunner {
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Compute  query string
     */
    public fun runFromString( query : String ) : String {

        logger.debug("Run query : $query")
        var result : String =""

        try {
            // First we simplify the query
            val queryCharArray: List<Char> = Utils.simplifyQuery(query).toList()

            // The we analyze the query to get an operation object, which will contain a list of operations elements or operations
            val operation: Operation = ArithmeticSyntaxAnalysis.checkForOperation(queryCharArray);

            // Compute the query
            val resultValue : Double = computeOperation(operation)

            if( resultValue == Double.POSITIVE_INFINITY || resultValue == Double.NEGATIVE_INFINITY) {
                throw CustomException("ERROR")
            }

            result = Utils.doubleToString( resultValue )
            logger.debug("Query result : $result")
        }
        catch(e : Exception) {
            // TODO implement custom errors
            result = e.message.toString()
        }
        finally {

        }

        return result;
    }

    /**
     * Compute an operation object
     */
    private fun computeOperation(operation : Operation) : Double {
        var resultValue : Double

        try{
            var listOfMathElements : MutableList<AbstractMathElement> = operation.mathElements.toMutableList()

            var index = 0
            // Go through operation elements list to treat nested operations
            while( index < listOfMathElements.size ) {
                var mathElement = listOfMathElements[index]
                if( mathElement.mathElementType == MathElementEnum.Operation ) {
                    // Recursive call to treat nested operation objects
                    listOfMathElements[index] = NumericElement(MathElementEnum.Number, computeOperation(mathElement as Operation).toString())
                }
                index++
            }

            val stack = Stack<OperatorElement>()
            val queue = ArrayDeque<AbstractMathElement>()

            // Simplified shutting yard algorithm to order elements
            for( currentMathElement : AbstractMathElement in listOfMathElements ) {
                if( currentMathElement.mathElementType == MathElementEnum.Operator || currentMathElement.mathElementType == MathElementEnum.Function ) {
                    var currentOperatorMathElement = currentMathElement as OperatorElement

                    // If stack is not empty and priority of current element is slower than peek of stack
                    while( !stack.empty() && ( Utils.getOperatorPriority( currentOperatorMathElement.value) < Utils.getOperatorPriority( stack.peek() .value) ) ){
                        //pop stack element to queue
                        queue.add( stack.pop() )
                    }
                    stack.push( currentOperatorMathElement );
                }
                else if ( currentMathElement.mathElementType == MathElementEnum.Number ){
                    queue.add( currentMathElement )
                }
                else{
                    // TODO throw custom error
                }
            }
            // Add remaining operants to the queue
            while ( ! stack.empty() ) {
                queue.add(stack.pop());
            }

            // Compute Reverse polish notation result
            resultValue = RpnListComputer.computeRPNMathElementsList(queue)

        }
        catch(e : Exception){
            throw e
        }

        return resultValue
    }

}