package com.example.calci

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var addOperation = false
    private var addDecimal = true
    private lateinit var textViewInput: TextView
    private lateinit var textViewResult: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewInput = findViewById(R.id.input)
        textViewResult = findViewById(R.id.output)

    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                textViewInput.append(view.text)
                addOperation = false
            } else
                textViewInput.append(view.text)

            addOperation = true

        }
    }

    fun operatorAction(view: View) {
        if (view is Button && addOperation) {
            textViewInput.append(view.text)
            addOperation = false
            addDecimal = true
        }
    }

    fun clearAction(view: View) {
        textViewResult.text = ""
        textViewInput.text = ""
    }

    fun equalsAction(view: View) {
        textViewResult.text = calculateResult()
    }

    private fun calculateResult(): String {
        val digitsOperator = digitsOperator()
        if(digitsOperator.isEmpty()) return ""

        val timesDivision=divCalculate(digitsOperator)
        if(timesDivision.isEmpty()) return ""
        
        val result=addSubtract(timesDivision)
        return result.toString()
    }

    private fun addSubtract(passedList: MutableList<Any>): Float {
        var result=passedList[0] as Float
        for(i in passedList.indices){
            if(passedList[i] is Char && i!=passedList.lastIndex)
            {
                val operator=passedList[i]
                val nextDigit=passedList[i+1] as Float
                if(operator=='+')
                    result+=nextDigit
                if(operator=='-')
                    result-=nextDigit
            }
        }
        return result
    }

    private fun divCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list=passedList
        while (list.contains('×')||list.contains('/')){
            list=calcdiv(list)
        }
        return list
    }

    private fun calcdiv(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size
        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float
                when (operator) {
                    '×' -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }

                    '/' -> {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }

                    else -> {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if (i>restartIndex)
                newList.add(passedList[i])
        }
        return newList
    }

    private fun digitsOperator():MutableList<Any>{
        val list= mutableListOf<Any>()
        var currentDigit=""
        for(character in textViewInput.text){
            if(character.isDigit() || character == '.')
                currentDigit+=character
            else{
                list.add(currentDigit.toFloat())
                currentDigit=""
                list.add(character)
            }
        }
        if (currentDigit!="")
            list.add(currentDigit.toFloat())
        return list
    }
}