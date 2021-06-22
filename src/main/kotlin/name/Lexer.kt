package name

import name.operators.*

class Lexer (
    private val text: String
        ) {

        private var pos = Position(-1, 1)
        private var currChar: Char? = null

    /*
     *  Operators and Constants
     */
    private val DIGITS      = "-1234567890"
    private val TT_INT      = Type("INT")
    private val TT_FLOAT    = Type("FLOAT")

    // Run this to initialize the Lexer class
    init {
        this.next()
        this.tokenize()
    }


    private fun parseNumber(numStr: String): Token {

        // Checks if there is more than 1 dots in the number, if it is, it's invalid syntax
        if (numStr.count { it == '.'} > 1) {
            // Make the index to send to the error at the second decimal point
            this.pos.idx = numStr.indexOf('.', numStr.indexOf('.') + 1) + 1

            throw SyntaxError("Too much decimal point", numStr, this.pos)
        };

        // If it only has one it's a float
        if (numStr.count { it == '.'} == 1) return Token(TT_FLOAT.type, numStr.toFloat())


        // Otherwise it's gonna be an integer
        return Token(TT_INT.type, numStr.toInt())

    }

    private fun tokenize(): MutableList<Token> {
        var temp = ""
        val ret: MutableList<Token> = mutableListOf()

        // Easy way to parse the current items in temp
        fun parseTemp() {
            if (temp == "") return

            // Create a copy of the current temp
            val copy: String = temp


            temp = ""


            // If the first character in the copy is a digit
            if (copy[0] in DIGITS) {

                // Parse it as a number
                ret.add(this.parseNumber(copy))
                return
            }

            // TODO Check if a variable named the same as the copy exist first, and then throw the error
            throw ReferenceError(
                "Value '$copy' is not referenced",
                copy,
                Position(this.pos.idx - (copy.length - 1), 1)
            )

        }


        // Scans for a string until the end of quotation
        fun scanStringLiteral(): String {
            var stringLiteral = "\""

            // Makes sure to ignore the first double quotation mark (")
            this.next()

            // Get the string
            while (this.currChar != '"' && this.currChar != '\n' && this.currChar != null) {
                stringLiteral += this.currChar
                this.next()
            }

            // If it doesn't end with another double quotation mark (")
            // It will throw an error
            if (this.currChar == '\n' || this.currChar == null) {

                this.pos.idx++
                throw SyntaxError(
                    "End Of Line found before end of string literal",
                    stringLiteral,
                    this.pos
                )
            }

            // If it does end with a double quotation mark ("), then return
            // the string scanned with the ending quotation mark
            return stringLiteral + '"'
        }


        // Main loop process
        while (this.currChar != null) {

            // Ignore spaces and tabs
            if (this.currChar in listOf(' ', '\t')) {
                parseTemp()
                this.next();
                continue
            }

            // If it starts with a string literal, then send it to string scanner
            if (this.currChar == '"') {
                val stringLiteral = scanStringLiteral()

                ret.add(Token("String", stringLiteral))

                this.next()
                continue
            }

            val findOperator = Operator.find(this.currChar.toString())


//            println(Operator.operators)

            // If the curr character is an operator, then parse the current temp and add the operator by itself
            if (findOperator > -1) {
                parseTemp()
                ret.add(Token(Operator.operators[findOperator].name))

            } else
                // Otherwise continue adding to temp
                temp += this.currChar

            this.next()

        }

        // Makes sure if there is something in temp, to parse it
        if (temp != "") parseTemp()

        println(ret)

        return ret

    }

    fun next() {
        this.pos.next()
        this.currChar = if (this.pos.idx < this.text.length) this.text[this.pos.idx] else null
    }
}