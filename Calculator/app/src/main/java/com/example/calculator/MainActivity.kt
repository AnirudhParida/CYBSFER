package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var btnClicked : TextView?= null
    private var lastnumber = false
    private var lastdecimal = false
    private var c = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClicked = findViewById(R.id.user_input)
    }

    fun onDigit(view: View){
        btnClicked?.append((view as Button).text)
        lastnumber = true
        lastdecimal = false

    }

    fun onCLear(view: View){
        btnClicked?.text = ""
    }

    fun onDecimal(view: View){
        if (lastnumber && !lastdecimal){
            btnClicked?.append(".")
            lastnumber = false
            lastdecimal =true
        }
        //Toast.makeText(this,"Button Clicked",Toast.LENGTH_LONG).show()
    }

    fun onEqual(view: View){
        if(lastnumber){
            var input_value = btnClicked?.text.toString()
            var prefix = ""
            try {
                if (input_value.startsWith("-")){
                    prefix = "-"
                    input_value=input_value.substring(1)
                }
                if (input_value.contains("-")){
                    var inputArray = input_value.split("-")
                    var value1 = inputArray[0]
                    var value2 = inputArray[1]

                    if (prefix.isNotEmpty()){
                        value1 = prefix + value1
                    }

                    btnClicked?.text = (value1.toDouble() - value2.toDouble()).toString()

                }
                else if (input_value.contains("+")){
                    var inputArray = input_value.split("+")
                    var value1 = inputArray[0]
                    var value2 = inputArray[1]

                    if (prefix.isNotEmpty()){
                        value1 = prefix + value1
                    }

                    btnClicked?.text = (value1.toDouble() + value2.toDouble()).toString()

                }
                else if (input_value.contains("*")){
                    var inputArray = input_value.split("*")
                    var value1 = inputArray[0]
                    var value2 = inputArray[1]

                    if (prefix.isNotEmpty()){
                        value1 = prefix + value1
                    }

                    btnClicked?.text = (value1.toDouble() * value2.toDouble()).toString()

                }
                else if (input_value.contains("/")){
                    var inputArray = input_value.split("/")
                    var value1 = inputArray[0]
                    var value2 = inputArray[1]

                    if (prefix.isNotEmpty()){
                        value1 = prefix + value1
                    }

                    btnClicked?.text = (value1.toDouble() / value2.toDouble()).toString()

                }

            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }

//        fun onEqual(view: View){
//            if(lastnumber){
//                var input_value = btnClicked?.text.toString()
//                var inputArray = input_value.split("-")
//                var value1 = inputArray[0]
//                var value2 = inputArray[1]
//
//                if(value1>value2) {
//                    btnClicked?.text = (value1.toDouble() - value2.toDouble()).toString()
//                }
//                if(value1<value2) {
//                    btnClicked?.text = (value1.toDouble() - value2.toDouble()).toString()
//                }
//
//            }
//        }

    fun onDelete(view: View){
        var length = btnClicked?.length()
        if (length != null) {
            if (length>0){
                btnClicked?.text?.substring(length)
            }
        }
    }

    fun Operator(view: View){
        if (lastnumber && !operatoradded(btnClicked?.text.toString())){
            btnClicked?.append((view as Button).text)
            lastnumber = false
            lastdecimal = false
        }
    }

    private fun operatoradded(value: String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
        }

    }
}