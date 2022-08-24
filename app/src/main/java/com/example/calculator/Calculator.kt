package com.example.calculator

class Calculator {

    fun someLogic(expr: String): Float {

        var index = 0
        val pass = { cond: (Char) -> Boolean -> while (index < expr.length && cond(expr[index])) index++ }
        val firstReading = { c: Char -> (index < expr.length && expr[index] == c).also { if (it) index++ } }
        val skipSpaces = { pass { it.isWhitespace() } }
        val secondReading = { op: Char -> skipSpaces().run { firstReading(op) }.also { if (it) skipSpaces() } }
        var root: () -> Float = { 0.0f }

        val num = {
            if (secondReading('(')) {
                root().also {
                    secondReading(')').also { if (!it) throw IllegalExpressionException(index, "Missing )") }
                }
            } else {
                val start = index
                firstReading('-') or firstReading('+')
                pass { it.isDigit() || it == '.' }
                try {
                    expr.substring(start, index).toFloat()
                } catch (e: NumberFormatException) {
                    throw IllegalExpressionException(start, "Invalid number", cause = e)
                }
            }
        }

        fun maths(left: () -> Float, op: Char): List<Float> = mutableListOf(left()).apply {
            while (secondReading(op)) addAll(maths(left, op))
        }

        val div = { maths(num, '/').reduce { a, b -> a / b } }
        val mul = { maths(div, '*').reduce { a, b -> a * b } }
        val sub = { maths(mul, '-').reduce { a, b -> a - b } }
        val add = { maths(sub, '+').reduce { a, b -> a + b } }

        root = add
        return root().also { if (index < expr.length) throw IllegalExpressionException(index, "Invalid expression") }
    }

    class IllegalExpressionException(val index: Int, message: String? = null, cause: Throwable? = null) :
        IllegalArgumentException("$message at:$index", cause)
}