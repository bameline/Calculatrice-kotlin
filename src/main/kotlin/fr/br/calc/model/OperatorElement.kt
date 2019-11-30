package fr.br.calc.model

import fr.br.calc.enums.MathElementEnum
import fr.br.calc.errors.CustomException

class OperatorElement(mathElementType : MathElementEnum, value : String ) : AbstractMathElement(mathElementType ){

    val value : String = value

    override fun getExpressionSize(): Int {
        return value.length
    }


    /**
     * Return an int indicating an operator priority
     * TODO change indicator as enum
     * 1 < 2 < 3 < 4
     */
    fun getOperatorPriority() : Int {
        if( mathElementType == MathElementEnum.Function){
            return 4
        }
        else{
            return when (value) {
                // TODO replace with enums
                "+" -> 1
                "-" -> 1
                "*" -> 2
                "/" -> 2
                "^" -> 3
                // TODO custom error
                else -> throw CustomException("ERROR")
            }
        }
    }
}