package fr.br.calc.model

import fr.br.calc.enums.MathElementEnum

abstract class AbstractMathElement(mathElementType: MathElementEnum) {
    val mathElementType: MathElementEnum = mathElementType
    abstract fun getExpressionSize() : Int
}