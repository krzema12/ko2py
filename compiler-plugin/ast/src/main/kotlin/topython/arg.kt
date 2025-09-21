package topython

import model.python.arg
import model.python.argImpl

fun arg.toPython(): String {
    return when (this) {
        is argImpl -> toPython()
    }
}

fun argImpl.toPython() =
    arg.name
