package it.krzeminski.ko2py.compiler

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class KotlinToPythonTest : FunSpec({
    test("smoke test") {
        // language=kotlin
        val input = """
            fun main() {
                println("Hello, world!")
            }
        """.trimIndent()

        val actualOutput = compileToPython(input).getPythonOutput()

        actualOutput shouldBe """
            def main():
                print("Hello, world!")

            if __name__ == "__main__":
                main()

        """.trimIndent()
    }
})
