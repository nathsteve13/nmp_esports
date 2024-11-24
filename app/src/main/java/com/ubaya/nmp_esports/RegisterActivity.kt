package com.ubaya.nmp_esports

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.ActivityRegisterBinding
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val firstName = binding.fnameTxt.text.toString()
            val lastName = binding.lnameTxt.text.toString()
            val username = binding.usernameTxt.text.toString()
            val password = binding.passTxt.text.toString()
            val confirmPassword = binding.confirmPassTxt.text.toString()

            if (password == confirmPassword) {
                registerAccount(firstName, lastName, username, password)
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerAccount(firstName: String, lastName: String, username: String, password: String) {
        val url = "https://ubaya.xyz/native/160422124/register.php"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val result = jsonObject.getString("result")

                    if (result == "OK") {
                        Toast.makeText(this, "Account registered successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val message = jsonObject.optString("message", "Failed to register")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("apiresult", e.message.toString())
                    Toast.makeText(this, "Error processing response", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Log.e("apiresult", it.message.toString())
                Toast.makeText(this, "Failed to connect to server", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["first_name"] = firstName
                params["last_name"] = lastName
                params["username"] = username
                params["password"] = password
                return params
            }
        }

        queue.add(stringRequest)
    }
}
