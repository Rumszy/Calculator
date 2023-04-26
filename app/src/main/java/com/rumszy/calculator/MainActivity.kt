package com.rumszy.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var calcText:TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calcText = findViewById(R.id.tvCalcText)

    }

    fun onNumeric(view: View) {

        if (calcText?.text!! == "0") {
            calcText?.text = (view as Button).text
            lastNumeric = true
        } else {
            calcText?.append((view as Button).text)
            lastNumeric = true
        }
    }

    fun onOperation(view: View) {
        calcText?.text?.let {
            if (lastNumeric && !hasOperation(it.toString())) {
                calcText?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onClear(view: View) {
        calcText?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var value = calcText?.text.toString()
            var prefix = ""

            try {
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                when {
                    value.contains("/") -> {
                        var splitValue = value.split("/")

                        var first = splitValue[0]
                        var second = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            first = prefix + first
                        }

                        calcText?.text = hasPointZero((first.toDouble() / second.toDouble()).toString())
                    }

                    value.contains("*") -> {
                        var splitValue = value.split("*")

                        var first = splitValue[0]
                        var second = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            first = prefix + first
                        }

                        calcText?.text = hasPointZero((first.toDouble() * second.toDouble()).toString())
                    }

                    value.contains("-") -> {
                        var splitValue = value.split("-")

                        var first = splitValue[0]
                        var second = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            first = prefix + first
                        }

                        calcText?.text = hasPointZero((first.toDouble() - second.toDouble()).toString())
                    }

                    value.contains("+") -> {
                        var splitValue = value.split("+")

                        var first = splitValue[0]
                        var second = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            first = prefix + first
                        }

                        calcText?.text = hasPointZero((first.toDouble() + second.toDouble()).toString())
                    }
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }

    }

    private fun hasPointZero(result: String): CharSequence? {

        var value = result

        if (value.contains(".0")) {
            value = result.substring(0, result.length-2)
        }
        return value
    }

    private fun hasOperation(text: String): Boolean {
        return if (text.startsWith("-")) {
            false
        }
        else {
                    text.contains("-") ||
                    text.contains("+") ||
                    text.contains("*") ||
                    text.contains("/")
        }
    }

    fun onPoint(view: View) {
        
        if (lastNumeric && !lastDot) {
            calcText?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
}