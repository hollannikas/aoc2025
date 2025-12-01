private const val MAX_DIAL = 100

fun turn(dial: Int, steps: Int, direction: Char): Int =
    (dial + if (direction == 'L') -steps else steps).mod(MAX_DIAL)

fun main() {
    val input = requireNotNull(
        object {}.javaClass.getResourceAsStream("/day1.txt")
    ) { "Resource day1.txt not found on classpath" }

    data class State(val dial: Int, val zeroCount: Int)

    val result = input.bufferedReader().useLines { lines ->
        lines
            .filter { it.isNotBlank() }
            .map { line ->
                val direction = line[0]
                val amount = line.substring(1).toInt()
                amount to direction
            }
            .fold(State(50, 0)) { (dial, count), (amount, direction) ->
                val next = turn(dial, amount, direction)
                State(
                    dial = next,
                    zeroCount = if (next == 0) count + 1 else count
                )
            }
    }

    println(result.zeroCount)
}