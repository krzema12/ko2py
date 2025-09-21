import asdl.parseAsdl
import generation.generateKotlinFromAsdl
import generation.writeToFile
import java.nio.file.Paths

fun main() {
    val python39AsdlAsText = {}.javaClass.getResource("/Python39.asdl").readText()
    val python39Asdl = parseAsdl(python39AsdlAsText)

    val targetDirectory = Paths.get("compiler-plugin/ast/src/main/kotlin/model")
    targetDirectory.toFile().deleteRecursively()
    python39Asdl.generateKotlinFromAsdl()
        .forEach {
            it.writeToFile(directory = targetDirectory)
        }
}
