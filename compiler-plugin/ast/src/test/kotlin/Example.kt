import generated.Python.Assign
import generated.Python.Call
import generated.Python.Constant
import generated.Python.Expr
import generated.Python.For
import generated.Python.List
import generated.Python.Load
import generated.Python.Module
import generated.Python.Name
import generated.Python.Store
import generated.Python.constant
import generated.Python.identifier
import generated.Python.mod

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
