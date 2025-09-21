package topython

import generated.Python.*

fun operator.toPython(): String = when (this) {
    Add -> "+"
    Sub -> "-"
    Mult -> "*"
    MatMult -> "@"
    Div -> "/"
    Mod -> "%"
    Pow -> "**"
    LShift -> "<<"
    RShift -> ">>"
    BitOr -> "|"
    BitXor -> "^"
    BitAnd -> "&"
    FloorDiv -> "//"
}
