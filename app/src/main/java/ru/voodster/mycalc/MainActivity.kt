package ru.voodster.mycalc

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import ru.voodster.mycalc.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

   private lateinit var binding:ActivityMainBinding

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val mainView = binding.root
        setContentView(mainView)
    }

    fun onDecimalPoint(){
        Toast.makeText(this,"works",Toast.LENGTH_SHORT)
        if(lastNumeric && !lastDot){
            binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onDigit(view: View){
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(){
        binding.tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onOperator(view: View) {
        if(lastNumeric&&!isOperatorAdded(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")|| value.contains("*")
                    || value.contains("+")||value.contains("-")
        }
    }
    private fun removeZero(result: String):String{
        var value = result
        if (result.contains(".0")){
            value = result.substring(0,result.length - 2)
        }
        return value
    }

    fun onDel(){

    }

    fun onEqual(){
        if(lastNumeric){
            var tvValue = binding.tvInput.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (!prefix.isEmpty()) one = prefix + one

                        binding.tvInput.text= removeZero((one.toDouble()-two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (!prefix.isEmpty()){
                            one = prefix + one
                        }

                        binding.tvInput.text= removeZero((one.toDouble()+two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (!prefix.isEmpty()){
                            one = prefix + one
                        }

                        binding.tvInput.text= removeZero((one.toDouble()*two.toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (!prefix.isEmpty()){
                            one = prefix + one
                        }

                        binding.tvInput.text= removeZero((one.toDouble()/two.toDouble()).toString())
                    }
                }
            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}