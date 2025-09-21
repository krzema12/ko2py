import model.python.Assign
import model.python.Call
import model.python.Constant
import model.python.Expr
import model.python.For
import model.python.List
import model.python.Load
import model.python.Module
import model.python.Name
import model.python.Store
import model.python.constant
import model.python.identifier
import model.python.mod

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
