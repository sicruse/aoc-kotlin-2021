package days

import kotlin.math.absoluteValue

class Day2 : Day(2) {

    private val course: List<ManoeuvringInstruction> by lazy { inputList.map { code -> ManoeuvringInstruction.fromText(code) } }

    data class ManoeuvringInstruction(val maneuver: Manoeuvr, val amount: Int) {
        companion object {
            fun fromText(code: String): ManoeuvringInstruction {
                val instruction = code.split(" ")
                val maneuver = instruction.first()
                val amount = instruction.last().toInt()
                return ManoeuvringInstruction(Manoeuvr.valueOf(maneuver), amount)
            }
        }
    }

    data class Position(val horizontal: Int, val depth: Int)
    data class PositionWithAim(val position: Position, val aim: Int)

    enum class Manoeuvr {
        forward {
            override fun maneuver(from: Position, amount: Int): Position = Position(from.horizontal + amount, from.depth)
            override fun maneuverByAim(from: PositionWithAim, amount: Int): PositionWithAim {
                val h = from.position.horizontal + amount
                val d = from.position.depth + from.aim * amount
                return PositionWithAim(Position(h, d), from.aim)
            }
        },
        down {
            override fun maneuver(from: Position, amount: Int): Position = Position(from.horizontal, from.depth + amount)
            override fun maneuverByAim(from: PositionWithAim, amount: Int): PositionWithAim =
                PositionWithAim(from.position, from.aim + amount)
        },
        up {
            override fun maneuver(from: Position, amount: Int): Position = Position(from.horizontal, from.depth - amount)
            override fun maneuverByAim(from: PositionWithAim, amount: Int): PositionWithAim =
                PositionWithAim(from.position, from.aim - amount)
        };

        abstract fun maneuver(from: Position, amount: Int): Position
        abstract fun maneuverByAim(from: PositionWithAim, amount: Int): PositionWithAim
    }

    private fun navigate(course: List<ManoeuvringInstruction>): Sequence<Position> = sequence {
        var currentPosition = Position(0, 0)
        for (instruction in course) {
            val newPosition = instruction.maneuver.maneuver(currentPosition, instruction.amount)
            currentPosition = newPosition
            yield(newPosition)
        }
    }

    private fun navigateWithAim(course: List<ManoeuvringInstruction>): Sequence<PositionWithAim> = sequence {
        var currentPosition = PositionWithAim(Position(0, 0), 0)
        for (instruction in course) {
            val newPosition = instruction.maneuver.maneuverByAim(currentPosition, instruction.amount)
            currentPosition = newPosition
            yield(newPosition)
        }
    }

    override fun partOne(): Any {
        val finalPosition = navigate(course).last()
        return finalPosition.horizontal.absoluteValue * finalPosition.depth.absoluteValue
    }

    override fun partTwo(): Any {
        val finalPosition = navigateWithAim(course).last()
        return finalPosition.position.horizontal.absoluteValue * finalPosition.position.depth.absoluteValue    }
}