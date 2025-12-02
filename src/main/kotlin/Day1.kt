private const val MAX_DIAL = 100

private fun turn(dial: Int, steps: Int, direction: Char): Int =
    (dial + if (direction == 'L') -steps else steps).mod(MAX_DIAL)

private fun readDay1Moves(): List<Pair<Int, Char>> {
    val input = requireNotNull(
        object {}.javaClass.getResourceAsStream("/day1.txt")
    ) { "Resource day1.txt not found on classpath" }

    return input.bufferedReader().useLines { lines ->
        lines
            .map { line ->
                val direction = line[0]
                val amount = line.substring(1).toInt()
                amount to direction
            }
            .toList()
    }
}

fun day11(): Int {
    data class State(val dial: Int, val zeroCount: Int)

    return readDay1Moves()
        .fold(State(50, 0)) { (dial, count), (amount, direction) ->
            val next = turn(dial, amount, direction)
            State(
                dial = next,
                zeroCount = if (next == 0) count + 1 else count
            )
        }
        .zeroCount
}

private fun countZeroPasses(current: Int, steps: Int, direction: Char): Int {
    val firstHit = if (direction == 'R') {
        val d = (MAX_DIAL - current) % MAX_DIAL
        if (d == 0) MAX_DIAL else d
    } else {
        val d = current % MAX_DIAL
        if (d == 0) MAX_DIAL else d
    }
    return if (steps <= firstHit) 0 else 1 + (steps - firstHit - 1) / MAX_DIAL
}

fun day12(): Int {
    data class State(val dial: Int, val passCount: Int)

    return readDay1Moves()
        .fold(State(50, 0)) { (dial, count), (amount, direction) ->
            val passes = countZeroPasses(dial, amount, direction)
            val next = turn(dial, amount, direction)
            State(next, count + passes + if (next == 0) 1 else 0)
        }
        .passCount
}
