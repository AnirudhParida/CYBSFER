package com.example.metric_convertor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.metric_convertor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate the layout
        setContentView(binding.root) // Set the content view to the inflated layout

        val unitArray = resources.getStringArray(R.array.unit_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unitArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        // Set up the button click listener
        binding.buttonConvert.setOnClickListener {
            convertUnits()
        }
    }

    private fun convertUnits() {
        val inputValue = binding.editTextValue.text.toString().toDoubleOrNull()
        if (inputValue == null) {
            binding.textViewResult.text = "Invalid input"
            return
        }

        val fromUnit = binding.spinnerFrom.selectedItem.toString()
        val toUnit = binding.spinnerTo.selectedItem.toString()

        val result = when {
            fromUnit == "Centimeters" && toUnit == "Meters" -> inputValue / 100
            fromUnit == "Meters" && toUnit == "Centimeters" -> inputValue * 100
            fromUnit == "Grams" && toUnit == "Kilograms" -> inputValue / 1000
            fromUnit == "Kilograms" && toUnit == "Grams" -> inputValue * 1000
            else -> inputValue // No conversion needed
        }

        binding.textViewResult.text = "$inputValue $fromUnit is $result $toUnit"
    }
}