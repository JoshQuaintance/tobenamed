import name.Lexer

fun shell() {
    while (true) {
        print(">> ")
        val action = readLine()

        // Exit Condition
        if (action == "exit")
            break


        if (action != null) {
            // Run try catch so that we can show custom errors
            try {
                Lexer(action)

            } catch (e: Throwable) {
                println(e.toString())
            }
        }

    }
}