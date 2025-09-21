package topython

import model.python.unaryop.*

fun unaryop.toPython(): String = when (this) {
    Invert -> "~"
    Not -> "not "
    UAdd -> "+"
    USub -> "-"
}
