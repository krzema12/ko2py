package topython

import model.python.arguments
import model.python.argumentsImpl

fun arguments.toPython(): String {
    return when (this) {
        is argumentsImpl -> toPython()
    }
}

fun argumentsImpl.toPython() =
    (args.map { it.toPython() } + vararg?.let { listOf("*${it.toPython()}") }.orEmpty())
        .joinToString(", ")
