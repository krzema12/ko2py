package it.krzeminski.ko2py.compiler

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import kotlin.io.path.Path
import kotlin.io.path.isRegularFile
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.walk

class JvmAndPythonOutputTest : FunSpec({
    Path("src/test/resources").walk()
        .toList()
        .filter { it.isRegularFile() && it.fileName.toString().endsWith(".kt") }
        .also { require(it.isNotEmpty()) }
        .forEach {
            test(it.nameWithoutExtension) {
                val input = KotlinToPythonTest::class.java.getResource("/" + it.fileName.toString()).readText()

                val compilationResult = compileToPython(input)
                val jvmClassFile = compilationResult.generatedFiles
                    .first { it.extension == "class" }
                    .parentFile
                val pythonCodeString = compilationResult.getPythonOutput()
                val pythonCode = tempfile()
                pythonCode.writeText(pythonCodeString)
                val stdoutFromJvm = runCommand("java", "-cp", jvmClassFile.absolutePath, "MainKt")
                val stdoutFromPython = runCommand("python3", pythonCode.absolutePath)

                val expectedStdout = input
                    .substringAfter("// Output:\n")
                    .lines().joinToString(separator = "\n") { it.removePrefix("//").trimStart() }

                withClue("Output mismatch! Python code:\n$pythonCodeString") {
                    stdoutFromPython shouldBe expectedStdout
                    stdoutFromJvm shouldBe expectedStdout
                }
            }
        }
})
