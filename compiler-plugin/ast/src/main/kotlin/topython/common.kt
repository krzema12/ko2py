package topython

fun String.indent() =
    lines().joinToString("\n") { it.indentOneLine() }

fun String.indentOneLine() =
    "    $this"
