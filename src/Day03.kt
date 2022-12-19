import kotlin.properties.Delegates

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { Rucksack(it) }.sumOf { it.calculatePriorityOfSharedItem() }
    }

    fun part2(input: List<String>): Int {
        val rucksacks = mutableListOf<String>()
        val rucksackGroups = mutableListOf<RucksackGroup>()
        input.forEachIndexed { index, s ->
            rucksacks.add(s)
            if ((index + 1) % 3 == 0) {
                rucksackGroups.add(RucksackGroup(rucksacks))
                rucksacks.clear()
            }
        }
        rucksackGroups.forEach {
            it.calculateBadge()
            println(it.badgePriority)
        }
        return rucksackGroups.sumOf { it.badgePriority }
    }


    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

class Rucksack(input: String) {
    // These are two compartments
    private val firstC: Set<Char> = input.take(input.length / 2).toSortedSet()
    private val secondC: Set<Char> = input.takeLast(input.length / 2).toSortedSet()
    private var sharedItem by Delegates.notNull<Char>()
    private var sharedItemPriority by Delegates.notNull<Int>()

    init {
        firstC.forEach { if (secondC.contains(it)) sharedItem = it }
        sharedItemPriority = getPriority(sharedItem)
    }

    fun calculatePriorityOfSharedItem(): Int {
        return sharedItemPriority
    }

    override fun toString(): String {
        return "1 = $firstC, 2 = $secondC, sh = $sharedItem, p = $sharedItemPriority"
    }
}

class RucksackGroup(input: List<String>) {
    val line1 = input[0].toSet()
    val line2 = input[1].toSet().toMutableSet()
    val line3 = input[2].toSet().toMutableSet()
    private var badge by Delegates.notNull<Char>()
    var badgePriority by Delegates.notNull<Int>()
        private set

    fun calculateBadge(): Char {
        line1.forEach {
            if (!line2.add(it) && !line3.add(it)) badge = it
        }
        badgePriority = getPriority(badge)
        return badge
    }
}

fun getPriority(badge: Char): Int {
    return badge.code - if (badge.isLowerCase()) SMALL_LETTER_OFFSET else CAPITAL_LETTER_OFFSET
}

const val CAPITAL_LETTER_OFFSET = 38
const val SMALL_LETTER_OFFSET = 96