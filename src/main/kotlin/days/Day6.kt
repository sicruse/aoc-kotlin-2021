package days

class Day6 : Day(6) {
    companion object {
        private const val timeToReset = 6
        private const val timeToSpawn = 8
    }

    private val initialLanternfishAges by lazy {
        val values = inputList.first().split(",").map{ it.toInt() }
        (0..timeToSpawn).map { ageIndex -> values.count { age -> age == ageIndex }.toLong() }.toLongArray()
    }

    val lanternfishAgeByDay: Sequence<Long> = sequence {
        val lanternfishAges = initialLanternfishAges.copyOf()
        yield(lanternfishAges.sum())
        while (true) {
            val spawnPopulation = lanternfishAges[0]
            for (ageIndex in 1..timeToSpawn) lanternfishAges[ageIndex-1] = lanternfishAges[ageIndex]
            lanternfishAges[timeToReset] += spawnPopulation
            lanternfishAges[timeToSpawn] = spawnPopulation
            yield(lanternfishAges.sum())
        }
    }

    override fun partOne(): Any {
        return lanternfishAgeByDay.elementAt(80)
    }

    override fun partTwo(): Any {
        return lanternfishAgeByDay.elementAt(256)
    }


}
