package hu.ait.minesweeper.ui

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import hu.ait.minesweeper.MainActivity
import hu.ait.minesweeper.R
import hu.ait.minesweeper.model.GamesweeperModel

class GamesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paintBackGround = Paint()
    var paintLine = Paint()
    var paintText = Paint()



    init {
        paintBackGround.color = Color. BLACK
        paintBackGround.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintText.color = Color.GREEN
        paintText.textSize = 60f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackGround)

        drawBoard(canvas)

        drawState(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height/5f
    }

    private fun drawBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        // four horizontal lines
        for (i in 1..4) {
            canvas?.drawLine(
                0f, (i * height / 5).toFloat(), width.toFloat(),
                (i * height / 5).toFloat(), paintLine
            )
        }

        // four vertical lines
        for (i in 1..4) {
            canvas?.drawLine(
                (i * width / 5).toFloat(), 0f, (i * width / 5).toFloat(), height.toFloat(),
                paintLine
            )
        }


    }

    private fun drawState(canvas: Canvas?) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (GamesweeperModel.fieldMatrix[i][j].wasClicked) {
                    if (GamesweeperModel.fieldMatrix[i][j].isFlagged) {
                        canvas?.drawText(context.getString(R.string.flag),
                            i * (width/5f), (j+1)*(height/5f), paintText)
                    } else {
                        canvas?.drawText(GamesweeperModel.fieldMatrix[i][j].minesAround.toString(),
                            i * (width/5f), (j+1)*(height/5f), paintText)
                    }
                }
            }
        }

    }

    private fun checkEndGame(){
        if (GamesweeperModel.userLost) {
            Snackbar.make(this, context.getString(R.string.lost_message), Snackbar.LENGTH_SHORT).show()
            GamesweeperModel.resetGame()
        } else if (GamesweeperModel.userWon) {
            Snackbar.make(this, context.getString(R.string.win_message), Snackbar.LENGTH_SHORT).show()
            GamesweeperModel.resetGame()
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width/5)
            val tY = event.y.toInt() / (height/5)
            if (GamesweeperModel.fieldMatrix[tX][tY].wasClicked) {
                Snackbar.make(this, context.getString(R.string.click_warning), Snackbar.LENGTH_SHORT).show()
            } else {
                GamesweeperModel.updateClicked(tX, tY)
            }
            GamesweeperModel.checkLocation(tX, tY)
            checkEndGame()
            invalidate()
        }

        return true
    }

}