package days

class Day5 : Day(5) {
    private val vents: List<Line> by lazy { inputList.map { Line(it) } }

    data class Point(val x: Int, val y: Int) {
        constructor(point: Point) : this(x = point.x, y = point.y)
        constructor(text: String) : this(fromText(text))

        companion object {
            private fun fromText(text: String): Point {
                val (xText, yText) = text.split(",")
                return Point(xText.toInt(), yText.toInt())
            }
        }
    }

    data class Line(val start: Point, val end: Point) {
        constructor(line: Line) : this(start = line.start, end = line.end)
        constructor(text: String) : this(fromText(text))

        val horizontal = start.y == end.y
        val vertical = start.x == end.x
        val diagonal = !(horizontal || vertical)
        val xRange = if (start.x <= end.x) (start.x .. end.x) else (start.x downTo end.x)
        val yRange = if (start.y <= end.y) (start.y .. end.y) else (start.y downTo end.y)
        val xyRange = xRange.zip(yRange)

        val points: Sequence<Point> = sequence {
            if (horizontal) {
                for (x in xRange) { yield( Point(x,start.y) ) }
            } else if (vertical) {
                for (y in yRange) { yield( Point(start.x,y) ) }
            } else {
                for ((x,y) in xyRange) { yield( Point(x,y) ) }
            }
        }

        companion object {
            private fun fromText(text: String): Line {
                val (startText, endText) = text.split(" -> ")
                return Line(Point(startText), Point(endText))
            }
        }
    }

    class SeaFloor(vents: List<Line>) {
        val points by lazy { vents.flatMap { it.points } }
        val danger by lazy { points.groupingBy { it }.eachCount().filter { it.value > 1 } }
    }

    override fun partOne(): Any {
        val seafloor = SeaFloor( vents.filter { !it.diagonal } )
        return seafloor.danger.count()
    }

    override fun partTwo(): Any {
        val seafloor = SeaFloor( vents )
        return seafloor.danger.count()
    }
}
