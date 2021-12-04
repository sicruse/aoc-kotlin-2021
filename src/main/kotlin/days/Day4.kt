package days

class Day4 : Day(4) {

    private val plays: List<Int> by lazy {
        inputList.first().split(",").map { it.toInt() }
    }

    private val boards: List<Board> by lazy {
        inputString
            // split the file at blank lines
            .split("\\n\\n".toRegex())
            // skip the first record
            .drop(1)
            .map { it -> Board(it) }
    }

    class Board(text: String) {
        private val body: Array<Array<Int>>
        private var index: MutableSet<Int>
        private val rowMatchCount = IntArray(5)
        private val colMatchCount = IntArray(5)
        private var won: Boolean = false

        init {
            body = bodyFromText(text.split(":\n").last())
            index = body.flatMap { it.toSet() }.toMutableSet()
        }

        companion object {
            fun bodyFromText(text: String): Array<Array<Int>> =
                text
                    .split("\\n".toRegex())
                    .map {
                        it
                            .replace("  "," ")
                            .trim()
                            .split(" ")
                            .map {
                                it.toInt()
                            }
                            .toTypedArray()
                    }.toTypedArray()
        }

        fun match(number: Int): Pair<Int, Int> {
            val row = body.indexOfFirst { it.contains(number) }
            val col = body[row].indexOfFirst { it == number }
            return Pair(row, col)
        }

        fun score(number: Int): Int? {
            if (won) return null
            else if (index.contains(number)) {
                val (row, col) = match(number)
                index.remove(number)
                rowMatchCount[row]++
                colMatchCount[col]++
                if (rowMatchCount.contains(5) || colMatchCount.contains(5)) {
                    won = true
                    return number * index.fold(0) { acc, num -> acc + num }
                }
            }
            return null
        }
    }

    override fun partOne(): Any {
        for (number in plays) {
            val score = boards.fold(0) { acc, board ->
                val win = board.score(number)
                if (win != null) acc + win else acc
            }
            if (score > 0) return score
        }
        return 0
    }

    override fun partTwo(): Any {
        var winningScore = 0
        for (number in plays) {
            val score = boards.fold(0) { acc, board ->
                val win = board.score(number)
                if (win != null) acc + win else acc
            }
            if (score > 0) winningScore = score
        }
        return winningScore
    }
}
