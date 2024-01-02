package com.example.cybsfer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.cybsfer.data.DatabaseHelper
import com.example.cybsfer.data.User
import com.example.cybsfer.databinding.ActivityMainBinding
import com.example.cybsfer.databinding.ActivitySigninBinding

class signin : AppCompatActivity() {

    private lateinit var dbHelper:DatabaseHelper
    private lateinit var binding:ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.buttonSignin.setOnClickListener {
            val usernameEditText: EditText = binding.userNameSigin
            val passwordEditText: EditText = binding.passwordSignin

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val user = User(0, username, password)
                dbHelper.addUser(user)

                val intent = Intent(this, login::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT).show()
            }
        }

    }
}