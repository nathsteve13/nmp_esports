package com.ubaya.nmp_esports

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

import com.ubaya.nmp_esports.databinding.ActivityInformationBinding
import com.ubaya.nmp_esports.databinding.ActivityScheduleDetailBinding
import org.json.JSONArray
import org.json.JSONObject

class ScheduleDetailActivity : AppCompatActivity() {
    companion object {
        const val SCHEDULE_TITLE = "SCHEDULE_TITLE"
        const val SCHEDULE_TIME = "SCHEDULE_TIME"
        const val DESCRIPTION = "DESCRIPTION"
        const val IMAGE_URL = "IMAGE_URL"
    }

    private lateinit var binding: ActivityScheduleDetailBinding
    private val scheduleDetailList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleId = intent.getIntExtra("schedule_id", 0)
        val scheduleTitle: String = intent.getStringExtra(SCHEDULE_TITLE).orEmpty()
        val scheduleTime: String = intent.getStringExtra(SCHEDULE_TIME).orEmpty()
        val description: String = intent.getStringExtra(DESCRIPTION).orEmpty()
        val imageUrl: String = intent.getStringExtra(IMAGE_URL).orEmpty()

        fetchScheduleDetails(scheduleId)
        binding.txtTitle.text = scheduleTitle
        binding.txtTime.text = scheduleTime
        binding.txtDescription.text = description
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.esportimage)
            .into(binding.imgScheduleDetail)


        binding.btnNotify.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage("Notification created")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchScheduleDetails(eventId: Int) {
        val url = "https://ubaya.xyz/native/160422124/get_schedule_details.php?idteam=$eventId"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")

                        scheduleDetailList.clear()

                        for (i in 0 until data.length()) {
                            val memberObject: JSONObject = data.getJSONObject(i)
                            val name = memberObject.getString("name")

                            scheduleDetailList.add(name)
                        }
                    } else {
                        Toast.makeText(this, "Tidak ada tim ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Parsing error: ${e.message}")
                }
            },
            { error ->
                Toast.makeText(this, "Gagal mengambil data detail event", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Fetching error: ${error.message}")
            })

        queue.add(request)
    }
}