package com.ubaya.nmp_esports

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
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

        // Ambil data dari intent
        val scheduleId = intent.getIntExtra("schedule_id", 0)
        val scheduleTitle: String = intent.getStringExtra(SCHEDULE_TITLE).orEmpty()
        val scheduleTime: String = intent.getStringExtra(SCHEDULE_TIME).orEmpty()
        val description: String = intent.getStringExtra(DESCRIPTION).orEmpty()
        val imageUrl: String = intent.getStringExtra(IMAGE_URL).orEmpty()

        // Set data awal
        binding.txtTitle.text = scheduleTitle
        binding.txtTime.text = scheduleTime
        binding.txtDescription.text = description
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.esportimage)
            .into(binding.imgScheduleDetail)

        // Fetch data tim dari API
        fetchScheduleDetails(scheduleId)

        // Tombol Notifikasi
        binding.btnNotify.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage("Notification created")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        // Tombol Kembali
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchScheduleDetails(eventId: Int) {
        val url = "https://ubaya.xyz/native/160422124/get_schedule_details.php?idevent=$eventId"
        val queue = Volley.newRequestQueue(this)

        // Log untuk debugging URL
        Log.d("DEBUG_URL", "Request URL: $url")

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    // Log respons untuk debugging
                    Log.d("DEBUG_RESPONSE", response.toString())

                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")

                        // Bersihkan list sebelum menambahkan data baru
                        scheduleDetailList.clear()

                        // Loop untuk menambahkan data tim ke list
                        for (i in 0 until data.length()) {
                            val memberObject: JSONObject = data.getJSONObject(i)
                            val name = memberObject.getString("name")
                            scheduleDetailList.add(name) // Tambahkan nama tim
                        }

                        // Update teks di txtTeams setelah data diambil
                        val displayText = if (scheduleDetailList.isEmpty()) {
                            "No teams found for this schedule."
                        } else {
                            scheduleDetailList.joinToString(separator = "\n") // Gabungkan nama tim dengan baris baru
                        }

                        binding.txtTeams.text = displayText
                    } else {
                        // Jika result bukan "success"
                        Toast.makeText(this, "Tidak ada tim ditemukan", Toast.LENGTH_SHORT).show()
                        binding.txtTeams.text = "No teams found for this schedule."
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
