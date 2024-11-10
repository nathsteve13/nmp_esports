package com.ubaya.nmp_esports

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.ubaya.nmp_esports.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("account_prefs", Context.MODE_PRIVATE)

        binding.button.setOnClickListener {
            val firstName = binding.fnameTxt.text.toString()
            val lastName = binding.lnameTxt.text.toString()
            val username = binding.usernameTxt.text.toString()
            val password = binding.passTxt.text.toString()
            val confirmPassword = binding.confirmPassTxt.text.toString()

            if (password == confirmPassword) {
                saveAccount(firstName, lastName, username, password)
                Toast.makeText(this, "Account registered successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveAccount(firstName: String, lastName: String, username: String, password: String) {
        with(sharedPreferences.edit()) {
            putString("first_name", firstName)
            putString("last_name", lastName)
            putString("username", username)
            putString("password", password)
            apply()
        }
    }
}
