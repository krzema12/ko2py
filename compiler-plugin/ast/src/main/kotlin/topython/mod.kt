package topython

import model.python.mod.Expression
import model.python.mod.FunctionType
import model.python.mod.Interactive
import model.python.mod.Module
import model.python.mod.mod


fun mod.toPython(): String {
    return when (this) {
        is Module -> toPython()
        is Interactive -> TODO()
        is Expression -> TODO()
        is FunctionType -> TODO()
    }
}

fun Module.toPython() =
    body.joinToString("\n") { it.toPython() }
