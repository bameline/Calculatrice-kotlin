package fr.br.calc.model

import fr.br.calc.enums.MathElementEnum
import fr.br.calc.utils.Utils

class NumericElement (mathElementType : MathElementEnum, value : String ) : AbstractMathElement( mathElementType ){

    var value : String = value

    override fun getExpressionSize(): Int {
        return value.length
    }
}