package it.krzeminski.ko2py.compiler

import model.python.builtins.constant
import model.python.builtins.identifier
import model.python.expr.BinOp
import model.python.expr.Call
import model.python.expr.Constant
import model.python.expr.Name
import model.python.expr.expr
import model.python.expr_context.Load
import model.python.operator.Add
import model.python.operator.Mult
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrConstKind
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.util.kotlinFqName
import org.jetbrains.kotlin.ir.util.target

fun IrExpression.toPythonExpr(): expr =
    when (this) {
        is IrCall -> this.toPythonExpr()
        is IrConst -> this.toPythonExpr()
        else -> Name(id = identifier("IrExpression.toPythonExpr missing support for $this"), ctx = Load)
    }

fun IrConst.toPythonExpr(): expr =
    Constant(
        value = constant(
            when (this.kind) {
                IrConstKind.String -> "\"${this.value}\""
                IrConstKind.Int -> this.value.toString()
                else -> "IrConst.toPythonExpr missing support for $this"
            }
        ),
        kind = null,
    )

fun IrCall.toPythonExpr(): expr = if (this.target.parent.kotlinFqName.asString() == "kotlin.Int") {
    if (this.target.name.asString() == "plus") {
        BinOp(
            left = this.arguments[0]!!.toPythonExpr(),
            op = Add,
            right = this.arguments[1]!!.toPythonExpr(),
        )
    } else if (this.target.name.asString() == "times") {
        BinOp(
            left = this.arguments[0]!!.toPythonExpr(),
            op = Mult,
            right = this.arguments[1]!!.toPythonExpr(),
        )
    } else {
        TODO("Unsupported function call: ${this.target.name.asString()} on ${this.target.parent.kotlinFqName.asString()}")
    }
} else {
    Call(
        func = Name(id = identifier(this.target.name.asString()), ctx = Load),
        args = this.arguments.filterNotNull().map {
            it.toPythonExpr()
        },
        keywords = emptyList(),
    )
}
