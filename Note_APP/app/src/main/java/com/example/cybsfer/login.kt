package com.example.cybsfer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cybsfer.data.DatabaseHelper
import com.example.cybsfer.databinding.ActivityLoginBinding

class login : AppCompatActivity() {


    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.buttonLogin.setOnClickListener {
            val username = binding.userNameLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            val user = dbHelper.getUser(username)

            if (user != null && user.password == password) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_ID", user.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textView8.setOnClickListener {
            val intent = Intent(this, signin::class.java)
            startActivity(intent)
        }
    }
}