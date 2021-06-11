package name

// Base Error class inherited Exception Kotlin class
open class Error (
    val type: String,
    val causeContent: String,
    val pos: Position,
    val details: String
        ) : Exception (details) {

    override fun toString(): String {
        // Get:
        //  Message to display.
        //  Tracer ~ where the error is occurring
        //  Content ~ The line where the error is occurring with all the content
        //  Arrow ~ An arrow showing specifically which column is causing the error
        val msg = "$type: $details"
        val tracer = "at line ${pos.line}, col ${pos.idx}"
        val content = "${pos.line}  $causeContent"
        val arrow = " ".repeat(pos.idx + 3) + "^"

        return " $content\n $arrow \n  $tracer\n$msg"
    }
}

class ReferenceError(details: String, causeContent: String, pos: Position) : Error ("ReferenceError", causeContent, pos,  details)

class SyntaxError(details: String, causeContent: String, pos: Position) : Error ("SyntaxError", causeContent, pos, details)