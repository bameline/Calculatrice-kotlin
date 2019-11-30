package fr.br.calc.app

import javafx.stage.Stage
import fr.br.calc.view.MainView
import fr.br.calc.view.Styles
import tornadofx.App

/**
 * Start point of the application
 */
class CalculatorApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}