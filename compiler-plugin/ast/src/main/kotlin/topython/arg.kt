package topython

import generated.Python.arg
import generated.Python.argImpl

fun arg.toPython(): String {
    return when (this) {
        is argImpl -> toPython()
    }
}

fun argImpl.toPython() =
    arg.name
