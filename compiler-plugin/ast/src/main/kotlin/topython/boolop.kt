package topython

import model.python.And
import model.python.Or
import model.python.boolop

fun boolop.toPython(): String = when (this) {
    And -> "and"
    Or -> "or"
}
