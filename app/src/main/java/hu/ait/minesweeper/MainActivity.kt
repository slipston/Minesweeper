package hu.ait.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import hu.ait.minesweeper.model.GamesweeperModel
import hu.ait.minesweeper.model.GamesweeperModel.changeClickMode
import hu.ait.minesweeper.ui.GamesweeperView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GamesweeperModel.generateMines()

        btnToggle.setOnClickListener {
            changeClickMode()

            if (GamesweeperModel.clickMode == GamesweeperModel.FLAG) {
                btnToggle.setText(R.string.place_flag)
            } else {
                btnToggle.setText(R.string.try_field)
            }
        }
    }
}
