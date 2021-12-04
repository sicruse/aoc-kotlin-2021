package days

import kotlin.math.sign

class Day3 : Day(3) {

    class Diagnostics(data: List<String>) {
        private val signals: List<Int> by lazy { data.map { Integer.parseInt(it, 2); } }
        private val bitwidth = data.first().length

        // sum the 1 bits across each column of data and if greater than half of the total number of rows then the answer is 1 otherwise 0
        // assume that there is never an even split of bits in the rows

        enum class Diagnostic {
            oxygen {
                override fun process(signalset: List<Int>, bitindex: Int): Int {
                    val signals = mostCommon(signalset, bitindex)
                    return super.process(signals, bitindex)
                }
            },
            co2 {
                 override fun process(signalset: List<Int>, bitindex: Int): Int {
                    val signals = leastCommon(signalset, bitindex)
                    return super.process(signals, bitindex)
                }
            };

            fun mostCommon(signalset: List<Int>, bitindex: Int): List<Int> {
                val countofones = signalset.sumOf { it shr bitindex - 1 and 1 }
                val countofzeros = signalset.count() - countofones
                if (countofones >= countofzeros)
                    return signalset.filter { it shr bitindex - 1 and 1 == 1 }
                else
                    return signalset.filter { it shr bitindex - 1 and 1 == 0 }
            }
            fun leastCommon(signalset: List<Int>, bitindex: Int): List<Int> {
                val countofones = signalset.sumOf { it shr bitindex - 1 and 1 }
                val countofzeros = signalset.count() - countofones
                if (countofzeros <= countofones)
                    return signalset.filter { it shr bitindex - 1 and 1 == 0 }
                else
                    return signalset.filter { it shr bitindex - 1 and 1 == 1 }
            }

            open fun process(signalset: List<Int>, bitindex: Int): Int {
                if (signalset.count() == 1) return signalset.first()
                else return process(signalset, bitindex - 1)
            }
        }

        val gamma by lazy {
            val threshold = data.count() / 2

            (bitwidth downTo 1).fold(0) { acc, bitindex ->
                val countofones = signals.sumOf { it shr bitindex - 1 and 1 }
                val bit = if (countofones > threshold) 1 else 0
                acc or (bit shl (bitindex - 1))
            }
        }

        val epsilon by lazy {
            val bitmask = -1 ushr Int.SIZE_BITS - bitwidth
            gamma.inv() and bitmask
        }

        val powerconsumption by lazy { gamma * epsilon }

        val lifesupport by lazy {
            val oxygen = Diagnostic.oxygen.process(signals, bitwidth)
            val co2 = Diagnostic.co2.process(signals, bitwidth)
            oxygen * co2
        }

    }

    override fun partOne(): Any {
        val diagnostics = Diagnostics(inputList)
        return diagnostics.powerconsumption
    }

    override fun partTwo(): Any {
        val diagnostics = Diagnostics(inputList)
        return diagnostics.lifesupport
    }

}
