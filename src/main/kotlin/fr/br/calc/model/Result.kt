package fr.br.calc.model

class Result {

    constructor() {
        this.result = null
        this.errorMessage = null
    }

    constructor(result: Double, errorMessage: String?) {
        this.result = result
        this.errorMessage = errorMessage
    }

    var result : Double?

    var errorMessage : String?


}