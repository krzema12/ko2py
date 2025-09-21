package generation

import java.nio.file.Path

data class SourceFile(
    val name: String,
    val content: String,
)

fun SourceFile.writeToFile(directory: Path) {
    val targetFile = directory.resolve(name.lowercase()).toFile()
    targetFile.parentFile.mkdirs()
    targetFile.writeText(content)
}
