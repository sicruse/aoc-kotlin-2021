package days

class Day1 : Day(1) {

    private val depths: List<Int> by lazy { inputList.map { it.toInt() } }
    private val windows = depths.windowed(3 ).map { it.sum() }
    private fun decent(values: List<Int>) = values.windowed(2 ).count { it.first() < it.last() }

    override fun partOne(): Any {
        return decent(depths)
    }

    override fun partTwo(): Any {
        return decent(windows)
    }
}
