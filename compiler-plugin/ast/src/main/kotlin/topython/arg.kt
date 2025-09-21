package topython

import model.python.arg.arg
import model.python.arg.argImpl

fun arg.toPython(): String {
    return when (this) {
        is argImpl -> toPython()
    }
}

fun argImpl.toPython() =
    arg.name
