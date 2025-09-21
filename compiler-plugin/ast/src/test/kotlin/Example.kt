import model.python.builtins.constant
import model.python.builtins.identifier
import model.python.expr.Call
import model.python.expr.Constant
import model.python.expr.List
import model.python.expr.Name
import model.python.expr_context.Load
import model.python.expr_context.Store
import model.python.mod.mod
import model.python.stmt.Assign
import model.python.stmt.Expr
import model.python.stmt.For
import model.python.mod.Module

val examplePythonCodeAst: mod = Module(
    body = listOf(
        Assign(
            targets = listOf(Name(id = identifier("fruits"), ctx = Store)),
            value = List(
                elts = listOf(
                    Constant(value = constant("\"apple\""), kind = null),
                    Constant(value = constant("\"banana\""), kind = null),
                    Constant(value = constant("\"cherry\""), kind = null),
                ),
                ctx = Load,
            ),
            type_comment = null,
        ),
        For(
            target = Name(id = identifier("x"), ctx = Store),
            iter = Name(id = identifier("fruits"), ctx = Load),
            body = listOf(
                Expr(
                    value = Call(
                        func = Name(id = identifier("print"), ctx = Load),
                        args = listOf(Name(id = identifier("x"), ctx = Load),),
                        keywords = emptyList(),
                    )
                )
            ),
            orelse = emptyList(),
            type_comment = null,
        ),
    ),
    type_ignores = emptyList(),
)
