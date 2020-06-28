package ch05.ex5_2_1_TheApplyFunction

fun 알파벳 () = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()

fun main(args: Array<String>) {
    println(알파벳 ())
}
