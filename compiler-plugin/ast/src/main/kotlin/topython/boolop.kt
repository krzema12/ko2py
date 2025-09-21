package topython

import generated.Python.And
import generated.Python.Or
import generated.Python.boolop

fun boolop.toPython(): String = when (this) {
    And -> "and"
    Or -> "or"
}
