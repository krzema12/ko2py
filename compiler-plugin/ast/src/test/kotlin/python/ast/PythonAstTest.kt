package python.ast

import examplePythonCodeAst
import generated.Python.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import topython.toPython

class PythonAstTest : FunSpec({
    test("to Python") {
        val expected = """
            fruits = ["apple", "banana", "cherry"]
            for x in fruits:
                print(x)
        """.trimIndent()

        examplePythonCodeAst.toPython() shouldBe expected
    }

    test("compare to Python") {
        val expected = "1 <= a < 10"
        val initial = Compare(
            left = Constant(constant("1"), null),
            ops = listOf(LtE, Lt),
            comparators = listOf(
                Name(identifier("a"), Load),
                Constant(constant("10"), null),
            ),
        )
        val actual = initial.toPython()

        actual shouldBe expected
    }

    test("elif to python") {
        val expected = """
            |if a <= 5:
            |    branch1
            |elif a <= 10:
            |    branch2
            |else:
            |    branch3
            |
        """.trimMargin()
        val initial = If(
            test = Compare(
                left = Name(identifier("a"), Load),
                ops = listOf(LtE),
                comparators = listOf(Constant(constant("5"), null)),
            ),
            body = listOf(Expr(Name(identifier("branch1"), Load))),
            orelse = listOf(
                If(
                    test = Compare(
                        left = Name(identifier("a"), Load),
                        ops = listOf(LtE),
                        comparators = listOf(Constant(constant("10"), null)),
                    ),
                    body = listOf(Expr(Name(identifier("branch2"), Load))),
                    orelse = listOf(Expr(Name(identifier("branch3"), Load))),
                )
            ),
        )
        val actual = initial.toPython()

        actual shouldBe expected
    }
})
