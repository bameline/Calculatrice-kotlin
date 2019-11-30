package fr.br.calc.model

import fr.br.calc.enums.MathElementEnum

class OperatorElement(mathElementType : MathElementEnum, value : String ) : AbstractMathElement(mathElementType ){

    val value : String = value

    override fun getExpressionSize(): Int {
        return value.length
    }
}