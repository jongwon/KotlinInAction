package ch05.ex5_2_2_TheApplyFunction1

fun 알파벳 () = buildString {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}

fun main(args: Array<String>) {
    println(알파벳())
}
