package topython

import model.python.*

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
