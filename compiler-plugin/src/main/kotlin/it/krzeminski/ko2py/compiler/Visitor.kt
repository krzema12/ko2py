@file:OptIn(UnsafeDuringIrConstructionAPI::class)

package it.krzeminski.ko2py.compiler

import model.python.arg.argImpl
import model.python.arguments.argumentsImpl
import model.python.builtins.identifier
import model.python.builtins.constant
import model.python.builtins.string
import model.python.cmpop.Eq
import model.python.expr.Call
import model.python.expr.Compare
import model.python.expr.Constant
import model.python.expr.Name
import model.python.expr_context.Load
import model.python.stmt.Expr
import model.python.stmt.FunctionDef
import model.python.stmt.If
import model.python.stmt.stmt
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrConstKind
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.types.isInt
import org.jetbrains.kotlin.ir.types.isString
import org.jetbrains.kotlin.ir.util.kotlinFqName
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.util.target
import org.jetbrains.kotlin.ir.visitors.IrVisitor
import kotlin.math.exp

class Visitor : IrVisitor<List<stmt>, Unit>() {
    override fun visitElement(element: IrElement, data: Unit): List<stmt> {
        when (element) {
            is IrModuleFragment -> return visitModuleFragment(element, data)
            else -> TODO("Not yet implemented: ${element.javaClass.canonicalName}")
        }
    }

    override fun visitModuleFragment(declaration: IrModuleFragment, data: Unit): List<stmt> {
        return declaration.files.flatMap {
            visitFile(it, data)
        }
    }

    override fun visitFile(declaration: IrFile, data: Unit): List<stmt> {
        return declaration.declarations.flatMap {
            when (it) {
                is IrFunction -> visitFunction(it, data)
                else -> TODO("Not implemented! visitFile")
            }
        } + if (hasMainFunction(declaration)) {
            generateCallToMain()
        } else {
            emptyList()
        }
    }

    private fun hasMainFunction(declaration: IrFile): Boolean
        = declaration.declarations.any { it is IrFunction && it.name.identifier == "main" }

    private fun generateCallToMain(): List<stmt> {
        return listOf(
            If(
                test = Compare(
                    left = Name(id = identifier("__name__"), ctx = Load),
                    ops = listOf(Eq),
                    comparators = listOf(Constant(value = constant("\"__main__\""), kind = null)),
                ),
                body = listOf(
                    Expr(
                        Call(
                            func = Name(id = identifier("main"), ctx = Load),
                            args = emptyList(),
                            keywords = emptyList(),
                        )
                    ),
                ),
                orelse = emptyList(),
            ),
        )
    }

    override fun visitFunction(declaration: IrFunction, data: Unit): List<stmt> {
        val args = argumentsImpl(
            args = declaration.parameters.map { param ->
                argImpl(
                    arg = identifier(param.name.asString()),
                    type_comment = when {
                        param.type.isString() -> string("str")
                        param.type.isInt() -> string("int")
                        else -> string("unknown type ${param.type}")
                    },
                    annotation = null,
                )
            },
            posonlyargs = emptyList(),
            vararg = null,
            kwonlyargs = emptyList(),
            kw_defaults = emptyList(),
            kwarg = null,
            defaults = emptyList(),
        )
        return listOf(
            FunctionDef(
                name = identifier(declaration.name.identifier),
                args = args,
                body = declaration.body?.let { visitBody(it, data) } ?: emptyList(),
                decorator_list = emptyList(),
                returns = null,
                type_comment = null
            )
        )
    }

    override fun visitBody(body: IrBody, data: Unit): List<stmt> {
        return body.statements.flatMap {
            when (it) {
                is IrCall -> visitCall(it, data)
                else -> TODO("Not implemented! visitBody, statement: $it")
            }
        }
    }

    override fun visitCall(expression: IrCall, data: Unit): List<stmt> {
        val funName = expression.target.name.asString().let {
            if (it == "println" && expression.target.parent.kotlinFqName.asString() == "kotlin.io") {
                // Until we have the full Kotlin stdlib compiled to Python, let's just
                // proxy to Python's 'print' function.
                "print"
            } else {
                it
            }
        }
        return listOf(
            Expr(Call(
                func = Name(id = identifier(funName), ctx = Load),
                args = expression.arguments.map {
                    Name(id = identifier(
                        when (it) {
                            is IrConst -> {
                                when (it.kind) {
                                    IrConstKind.String -> "\"${it.value}\""
                                    IrConstKind.Int -> it.value.toString()
                                    else -> "TODO_argValue"
                                }
                            }
                            else -> "TODO_argValue"
                        }
                    ), ctx = Load)
                },
                keywords = emptyList(),
            ))
        )
    }
}
