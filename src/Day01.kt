fun main() {
    fun part1(input: List<String>): Int {
        var maxSum = 0
        var curSum = 0
        for (value in input) {
            value.toIntOrNull()?.let { curSum += it }
                ?: run {
                    if (curSum > maxSum) maxSum = curSum
                    curSum = 0
                }
        }
        return maxSum
    }

    fun part2(input: List<String>): Int {
        val sumsList = mutableListOf<Int>()
        var curSum = 0
        for (value in input) {
            value.toIntOrNull()?.let { curSum += it }
                ?: run {
                    sumsList.add(curSum)
                    curSum = 0
                }
        }
        return sumsList.sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
