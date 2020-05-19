package hu.ait.minesweeper.model

import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import hu.ait.minesweeper.MainActivity

data class Field(var type: Int, var minesAround: Int, var isFlagged: Boolean,
                 var wasClicked: Boolean)

object GamesweeperModel {

    // type: 0 if not mine, 1 if a mine

    val fieldMatrix: Array<Array<Field>> = Array(5) {
        Array(5) {
            Field(0,0,false, false)
        }
    }

    public val FLAG: Short =  0
    public val FIELD: Short = 1
    public var minesFound: Int = 0

    var userLost = false
    var userWon = false

    var clickMode = FLAG

    fun changeClickMode() {
        clickMode = if (clickMode == FLAG) FIELD else FLAG
    }

    fun resetGame() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].wasClicked = false
                fieldMatrix[i][j].isFlagged = false
            }
        }
        minesFound = 0
        userLost = false
        userWon = false
    }


    fun checkLocation(x: Int, y: Int) {
        if (fieldMatrix[x][y].type == 0 && clickMode == FLAG) {
            userLost = true
        } else if (fieldMatrix[x][y].type == 1 && clickMode == FLAG){
            minesFound += 1
            if (minesFound == 3) {
                userWon = true
            }
        } else if (fieldMatrix[x][y].type == 1 && clickMode == FIELD){
            userLost = true
        }
    }

    fun updateClicked(x: Int, y: Int) {
        fieldMatrix[x][y].wasClicked = true
        if (clickMode == FLAG) {
            fieldMatrix[x][y].isFlagged = true
        }
    }

    fun countMinesAround(x: Int, y: Int) {
        for (i in x-1..x+1) {
            for (j in y-1..y+1) {
                if (i>=0 && i<=4) {
                    if (j>=0 && j<=4) {
                        fieldMatrix[i][j].minesAround += 1
                    }
                }
            }
        }
    }

    fun generateMines() {
        val x1 = 4
        val y1 = 4

        val x2 = 3
        val y2 = 3

        val x3 = 0
        val y3 = 0


        fieldMatrix[x1][y1].type = 1
        countMinesAround(x1, y1)
        fieldMatrix[x2][y2].type = 1
        countMinesAround(x2, y2)
        fieldMatrix[x3][y3].type = 1
        countMinesAround(x3, y3)

        fieldMatrix[x1][y1].minesAround = 0
        fieldMatrix[x2][y2].minesAround = 0
        fieldMatrix[x3][y3].minesAround = 0

    }


}