package com.ubaya.nmp_esports

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.ActivityLoginBinding
import org.json.JSONObject
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        if (sharedPref.contains("username")) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.usernameTxt.text.toString()
            val password = binding.pwdTxt.text.toString()
            login(username, password)
        }

        binding.btnToRegist.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(username: String, password: String) {
        val url = "https://ubaya.xyz/native/160422124/get_members.php"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val result = jsonObject.getString("result")

                    if (result == "success") {
                        val data = jsonObject.getJSONObject("data")
                        val userId = data.getInt("idmember")
                        val username = data.getString("username")

                        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putInt("userId", userId)
                            putString("username", username)
                            apply()
                        }

                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val message = jsonObject.getString("message")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("apiresult", e.message.toString())
                    Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Log.e("apiresult", it.message.toString())
                Toast.makeText(this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }

        queue.add(stringRequest)
    }


}
