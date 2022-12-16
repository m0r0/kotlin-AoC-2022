fun main() {
    fun part1(input: List<String>): Int {
        return solvePart1(input)
    }

    fun part2(input: List<String>): Int {
        return solvePart2(input)
    }


    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

fun solvePart1(input: List<String>): Int {
    return input.sumOf { singleGame ->
        getOutcome(singleGame).score + getScoreForPlay(singleGame.split(" ").last())
    }
}

fun solvePart2(input: List<String>): Int {
    return input.sumOf { singleGame ->
        val desiredOutcome = getDesiredOutcome(singleGame)
        desiredOutcome.score + getScoreForOutcome(singleGame.split(" ").first(), desiredOutcome)
    }
}

private fun getOutcome(input: String): Outcome {
    return when (input) {
        "$ROCK_1 $ROCK_2", "$PAPER_1 $PAPER_2", "$SCISSORS_1 $SCISSORS_2" -> Outcome.Draw
        "$ROCK_1 $PAPER_2", "$PAPER_1 $SCISSORS_2", "$SCISSORS_1 $ROCK_2" -> Outcome.Win
        "$ROCK_1 $SCISSORS_2", "$PAPER_1 $ROCK_2", "$SCISSORS_1 $PAPER_2" -> Outcome.Loss
        else -> throw IllegalStateException("Invalid input $input")
    }
}

private fun getDesiredOutcome(input: String): Outcome {
    return when (input.split(" ").last()) {
        DRAW -> Outcome.Draw
        WIN -> Outcome.Win
        LOSS -> Outcome.Loss
        else -> throw IllegalStateException("Invalid input $input")
    }
}

private fun getScoreForOutcome(opponentsPlay: String, outcome: Outcome): Int {
    return when (opponentsPlay) {
        ROCK_1 -> when (outcome) {
            Outcome.Loss -> getScoreForPlay(SCISSORS_2)
            Outcome.Win -> getScoreForPlay(PAPER_2)
            Outcome.Draw -> getScoreForPlay(ROCK_2)
        }

        PAPER_1 -> when (outcome) {
            Outcome.Win -> getScoreForPlay(SCISSORS_2)
            Outcome.Draw -> getScoreForPlay(PAPER_2)
            Outcome.Loss -> getScoreForPlay(ROCK_2)
        }

        SCISSORS_1 -> when (outcome) {
            Outcome.Draw -> getScoreForPlay(SCISSORS_2)
            Outcome.Loss -> getScoreForPlay(PAPER_2)
            Outcome.Win -> getScoreForPlay(ROCK_2)
        }

        else -> throw IllegalStateException("Invalid play $opponentsPlay")
    }
}

private fun getScoreForPlay(play: String): Int {
    return play.first().code - 'W'.code
}

sealed class Outcome(val score: Int) {
    object Loss : Outcome(0)
    object Draw : Outcome(3)
    object Win : Outcome(6)
}


private const val ROCK_1 = "A"
private const val PAPER_1 = "B"
private const val SCISSORS_1 = "C"
private const val ROCK_2 = "X"
private const val LOSS = "X"
private const val PAPER_2 = "Y"
private const val DRAW = "Y"
private const val SCISSORS_2 = "Z"
private const val WIN = "Z"
