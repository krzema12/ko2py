fun myFirstFunction() {
    println("Hello world!")
}

fun functionWithArguments(arg1: String, arg2: Int) {
    println("This is another function")
}

fun main() {
    myFirstFunction()
    functionWithArguments(arg1 = "Hello", arg2 = 42)
}
