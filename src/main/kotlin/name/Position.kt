package name

class Position (
    var idx: Int,
    var line: Int
        ) {

    fun next(currChar: Char = ' ') {
        this.idx++
//        this.currChar = if (this.pos < this.text.length) this.text[this.pos] else null
    }


}