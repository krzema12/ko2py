package topython

import generated.Python.*

fun unaryop.toPython(): String = when (this) {
    Invert -> "~"
    Not -> "not "
    UAdd -> "+"
    USub -> "-"
}
