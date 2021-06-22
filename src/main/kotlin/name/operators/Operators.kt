package name.operators

open class Type (
    val type: String,
    val symbol: String? = null,
    val name: String = type,
)


class Operator (name: String, symbol: String) : Type("Operator", symbol, name) {
    companion object {
        var operators: MutableList<Operator> = mutableListOf()

        fun find(operator: String): Int {

            val mappedOps = operators.map {
                it.symbol
            }

            return mappedOps.indexOf(operator)
        }

        private val OP_Minus: Operator = Operator ("MIN", "-")
        private val OP_Plus: Operator = Operator ("PLUS", "+")
        private val OP_Multiply: Operator = Operator ("MUL", "*")
        private val OP_Divide: Operator = Operator ("DIV", "/")


    }

    init {
        operators.add(this)
    }

    override fun toString(): String {
        return name
    }
}

