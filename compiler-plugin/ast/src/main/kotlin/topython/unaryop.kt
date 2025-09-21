package topython

import model.python.*

fun unaryop.toPython(): String = when (this) {
    Invert -> "~"
    Not -> "not "
    UAdd -> "+"
    USub -> "-"
}
