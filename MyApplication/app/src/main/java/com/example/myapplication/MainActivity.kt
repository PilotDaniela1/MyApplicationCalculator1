package com.example.myapplication;
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var resultTextView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        resultTextView = findViewById(R.id.resultTextView)


        val buttonIds = arrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonDot, R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply,
            R.id.buttonDivide, R.id.buttonBr1, R.id.buttonBr2, R.id.backspace,
            R.id.AC, R.id.buttonEquals
        )

        for (buttonId in buttonIds) {
            val button = findViewById<Button>(buttonId)

            when (buttonId) {

                in arrayOf(R.id.buttonDot, R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                    R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9) -> {
                    val color = ContextCompat.getColor(this, R.color.navi)
                    val drawable = ContextCompat.getDrawable(this, R.drawable.rounded_button)
                    drawable?.setColorFilter(color, PorterDuff.Mode.SRC)
                    button.background = drawable
                }
                in arrayOf(R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide) -> {
                    val color = ContextCompat.getColor(this, R.color.orange)
                    val drawable = ContextCompat.getDrawable(this, R.drawable.rounded_button)
                    drawable?.setColorFilter(color, PorterDuff.Mode.SRC)
                    button.background = drawable
                }
                in arrayOf(R.id.buttonEquals) -> {
                    val color = ContextCompat.getColor(this, R.color.green)
                    val drawable = ContextCompat.getDrawable(this, R.drawable.rounded_button)
                    drawable?.setColorFilter(color, PorterDuff.Mode.SRC)
                    button.background = drawable
                }
                in arrayOf(R.id.backspace, R.id.AC) -> {
                    val color = ContextCompat.getColor(this, R.color.red)
                    val drawable = ContextCompat.getDrawable(this, R.drawable.rounded_button)
                    drawable?.setColorFilter(color, PorterDuff.Mode.SRC)
                    button.background = drawable
                }


            }

            button.setOnClickListener { onButtonClick(it) }
        }

        val acButton = findViewById<Button>(R.id.AC)
        acButton.performClick()

        findViewById<Button>(R.id.buttonEquals).setOnClickListener { onEqualsButtonClick() }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        val currentText = editText.text.toString()

        when (buttonText) {
            "AC" -> {
                editText.setText("")
                resultTextView.setText("")
            }
            "C" -> {
                if (currentText.isNotEmpty()) {
                    editText.setText(currentText.dropLast(1))
                }
            }
            else -> {
                editText.setText(currentText + buttonText)
            }
        }
    }

    private fun onEqualsButtonClick() {
        val currentText = editText.text.toString()
        try {
            val result = evaluateExpression(currentText)
            var resultText = result.toString()

            if (result % 1 == 0.0) {
                resultText = result.toLong().toString()
            }

            if (resultText.length > 12) {
                resultTextView.setTextSize(30f)
            } else {
                resultTextView.setTextSize(45f)
            }

            resultTextView.text = Editable.Factory.getInstance().newEditable(resultText)

        } catch (e: Exception) {
            resultTextView.setTextSize(20f)  //
            resultTextView.text = Editable.Factory.getInstance().newEditable("Error: ${e.message}")
        }
    }

    private fun evaluateExpression(expression: String): Double {
        return ExpressionBuilder(expression).build().evaluate()
    }
}
