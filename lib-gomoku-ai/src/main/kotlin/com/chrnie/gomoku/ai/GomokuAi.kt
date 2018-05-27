package com.chrnie.gomoku.ai

import com.chrnie.gomoku.Chessman
import com.chrnie.gomoku.GomokuGame
import java.util.*
import kotlin.math.max
import kotlin.math.min

class GomokuAi(val game: GomokuGame, val difficulty: Int) {

    private val random = Random(System.currentTimeMillis())
    private val evaluator = Evaluator

    init {
        if (difficulty <= 0 || difficulty % 2 != 0) {
            throw RuntimeException("difficulty must > 0")
        }
    }

    fun next(): Point {
        if (game.isWin) {
            throw RuntimeException("game is win")
        }

        var maxScore = Int.MIN_VALUE
        val resultList = ArrayList<Point>()

        val pointList = findPutChessmanPoint()
        for (p in pointList) {
            val score = alphabeta(p.x, p.y, difficulty, Int.MIN_VALUE, Int.MAX_VALUE, game.chessman)
            if (score < maxScore) {
                continue
            }
            if (score > maxScore) {
                maxScore = score
                resultList.clear()
            }
            resultList.add(p)
        }

        if (resultList.isEmpty()) {
            var p: Point? = null
            while (p == null) {
                val x = random.nextInt(game.chessboardWidth)
                val y = random.nextInt(game.chessboardHeight)
                if (game.chessmanAt(x, y) != null) {
                    continue
                }
                p = Point(x, y)
            }

            return p
        }

        val r = random.nextInt(resultList.size)
        return resultList[r]
    }

    private fun alphabeta(x: Int, y: Int, depth: Int, alpha: Int, beta: Int, player: Chessman): Int {
        println("($x,$y) - depth:$depth - alpha:$alpha - beta:$beta")

        game.putChessman(x, y)

        if (game.isWin || depth == 0) {
            game.undo()
            val score = evaluator.evaluate(game, player)
            println("($x,$y) - depth:$depth - alpha:$alpha - beta:$beta - score:$score")
            return score
        }


        val pointList = findPutChessmanPoint()

        var a = alpha
        var b = beta

        var score: Int
        if (player == game.chessman) {
            score = Int.MIN_VALUE

            for (p in pointList) {
                score = max(score, alphabeta(p.x, p.y, depth - 1, a, b, player))
                a = max(a, score)
                if (b <= a) {
                    break
                }
            }
        } else {
            score = Int.MAX_VALUE

            for (p in pointList) {
                score = min(score, alphabeta(p.x, p.y, depth - 1, a, b, player))
                b = min(score, b)
                if (b <= a) {
                    break
                }
            }
        }

        game.undo()
        return score
    }

    private fun findPutChessmanPoint(): List<Point> {
        val set = HashSet<Point>()

        val width = game.chessboardWidth
        val height = game.chessboardHeight

        for (x in 0 until width) {
            for (y in 0 until height) {
                if (game.chessmanAt(x, y) == null) {
                    continue
                }

                set.add(Point(x - 1, y - 1))
                set.add(Point(x, y - 1))
                set.add(Point(x + 1, y - 1))
                set.add(Point(x + 1, y))
                set.add(Point(x + 1, y + 1))
                set.add(Point(x, y + 1))
                set.add(Point(x - 1, y + 1))
                set.add(Point(x - 1, y))
            }
        }

        return set.filter { (x, y) -> (x in 0 until width) && (y in 0 until height) && (game.chessmanAt(x, y) == null) }
    }
}