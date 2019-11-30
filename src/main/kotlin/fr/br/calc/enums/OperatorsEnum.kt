package qc.ezo.calc.enums

enum class OperatorsEnum (val operator : String) {
    Multiply("*"),
    Divide("/"),
    Add("+"),
    Substract("-"),
    Exponent("^");

    fun getValue() : String{
        return operator
    }
}