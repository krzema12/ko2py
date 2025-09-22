package it.krzeminski.ko2py.compiler

import model.python.arguments.argumentsImpl
import model.python.builtins.identifier
import model.python.stmt.FunctionDef
import model.python.stmt.stmt
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.visitors.IrVisitor

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

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    override fun visitFile(declaration: IrFile, data: Unit): List<stmt> {
        return declaration.declarations.flatMap {
            when (it) {
                is IrFunction -> visitFunction(it, data)
                else -> TODO("Not implemented! visitFile")
            }
        }
    }

    override fun visitFunction(declaration: IrFunction, data: Unit): List<stmt> {
        return listOf(
            FunctionDef(
                name = identifier(declaration.name.identifier),
                args = argumentsImpl(
                    args = emptyList(),
                    vararg = null,
                    kwonlyargs = emptyList(),
                    posonlyargs = emptyList(),
                    kw_defaults = emptyList(),
                    kwarg = null,
                    defaults = emptyList()
                ),
                body = emptyList(),
                decorator_list = emptyList(),
                returns = null,
                type_comment = null
            )
        )
    }
}
