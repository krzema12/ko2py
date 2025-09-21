package topython

import model.python.cmpop.*


fun cmpop.toPython(): String = when (this) {
    Eq -> "=="
    Gt -> ">"
    GtE -> ">="
    In -> "in"
    Is -> "is"
    IsNot -> "is not"
    Lt -> "<"
    LtE -> "<="
    NotEq -> "!="
    NotIn -> "not in"
}