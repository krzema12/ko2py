package it.krzeminski.ko2py.compiler

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import java.io.File

class ArgsProcessor : CommandLineProcessor {
    override val pluginId: String = "ko2py"

    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption(
            optionName = "outputDir",
            valueDescription = "<file-path>",
            description = "Path to the directory where the generated Python code should be placed.",
            required = true,
            allowMultipleOccurrences = false,
        )
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        if (option.optionName != "outputDir") {
            throw IllegalArgumentException("Unexpected config option ${option.optionName}")
        }
        configuration.put(OutputDirKey, File(value))
    }
}
