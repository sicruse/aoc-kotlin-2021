import days.Day
import java.util.*

class Template : Day(0) {

    override fun partOne(): Any {
        return inputList.take(2)
            .map { it.uppercase(Locale.getDefault()) }
            .joinToString(" ")
    }

    override fun partTwo(): Any {
        return inputList.take(2)
            .map { it.uppercase(Locale.getDefault()) }
            .joinToString(" ")
    }
}
