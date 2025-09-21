package topython

import model.python.boolop.And
import model.python.boolop.Or
import model.python.boolop.boolop


fun boolop.toPython(): String = when (this) {
    And -> "and"
    Or -> "or"
}
