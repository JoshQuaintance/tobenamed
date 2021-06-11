package name



class Token(
    val type: String,
    val value: Any? = null
        ) {

    override fun toString(): String {
        if (this.value != null) return "${this.type}: ${this.value}"
        return this.type
    }
}