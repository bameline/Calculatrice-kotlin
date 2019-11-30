package fr.br.calc.view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.control.Button
import fr.br.calc.services.QueryRunner
import qc.ezo.calc.enums.NumbersEnum
import qc.ezo.calc.enums.OtherSymbolsEnum
import tornadofx.*

class MainView : View("Calculator") {

    // Observables
    private var query = SimpleStringProperty()
    private var result = SimpleStringProperty()

    // TODO put this out of controller
    override val root = hbox {
        form {

            fieldset(labelPosition = Orientation.VERTICAL) {
                id = "mainPanel"
                // Input query area
                field {
                    text = "Formula"
                    textfield().bind(query)
                }
                // This is the text result area
                field {
                    label().bind(result)
                }
                hbox {
                    button {
                        text = NumbersEnum.One.number.toString()
                    }
                    button {
                        text = NumbersEnum.Two.number.toString()
                    }
                    button {
                        text = NumbersEnum.Three.number.toString()
                    }
                }
                hbox {
                    button {
                        text = NumbersEnum.Four.number.toString()
                    }
                    button {
                        text = NumbersEnum.Five.number.toString()
                    }
                    button {
                        text = NumbersEnum.Six.number.toString()
                    }
                }
                hbox {
                    button {
                        text = NumbersEnum.Seven.number.toString()
                    }
                    button {
                        text = NumbersEnum.Eight.number.toString()
                    }
                    button {
                        text = NumbersEnum.Nine.number.toString()
                    }
                }
                hbox {
                    button {
                        text = NumbersEnum.Zero.number.toString()
                    }
                }
                hbox {
                    button {
                        text = OtherSymbolsEnum.Empty.symbol.toString()
                        id = "empty"
                    }
                    button {
                        text = OtherSymbolsEnum.Equals.symbol.toString()
                        id="compute"
                    }
                }
            }
        }
    }

    /**
     * Init view
     */
    init {
        // Init query value
        query.value = ""

        // Init each buttons
        // Add content to query buttons
        root.lookupAll(".button").forEach { button ->
            button.setOnMouseClicked {
                addQuery((button as Button).text)
            }
        }

        // Empty content button
        root.lookup("#empty").setOnMouseClicked {
            emptyQuery()
        }

        // Compute query button
        root.lookup("#compute").setOnMouseClicked {
            computeQuery()
        }
    }

    /**
     * Add element to query
     */
    private fun addQuery( addedSymbol : String ){
        query.value += addedSymbol
        println(query)
    }

    /**
     * Empty query
     */
    private fun emptyQuery(){
        query.value = ""
        println(query)
    }

    /**
     * Compute query
     */
    private fun computeQuery() {
        // Call fun to compute value, then return object with result and error and display
        //val resultContent = Result(5,"Error message")
        val resultContent = QueryRunner.runFromString( query.value );
        result.value = resultContent
    }
}