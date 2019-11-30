package fr.br.calc.model

import fr.br.calc.enums.MathElementEnum

class Operation(mathElementType : MathElementEnum, mathElements : List<AbstractMathElement>) : AbstractMathElement(mathElementType ){

    var mathElements : List<AbstractMathElement> = mathElements
        get() = field
        set(value) { field = value }

    override fun getExpressionSize() : Int {
        var size : Int = 0
        mathElements.forEach {
            size += it.getExpressionSize()
        }
        return size
    }
}