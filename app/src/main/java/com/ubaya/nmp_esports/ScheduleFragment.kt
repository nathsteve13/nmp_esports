package com.ubaya.nmp_esports

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.FragmentHomeBinding
import com.ubaya.nmp_esports.databinding.FragmentPlayBinding
import com.ubaya.nmp_esports.databinding.FragmentScheduleBinding
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ScheduleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentScheduleBinding
    private val scheduleList = mutableListOf<Schedule>()
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val view = binding.root

        scheduleAdapter = ScheduleAdapter(scheduleList)
        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recSchedule.adapter = scheduleAdapter

        fetchGames()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchGames() {
        val url = "https://ubaya.xyz/native/160422124/get_schedules.php"
        val queue = Volley.newRequestQueue(requireContext())

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    Log.d("DEBUG", "Response: $response")
                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")
                        scheduleList.clear()

                        for (i in 0 until data.length()) {
                            val gameObject: JSONObject = data.getJSONObject(i)
                            val idevent = gameObject.getInt("idevent")
                            val name = gameObject.getString("name")
                            val date = gameObject.getString("date")
                            val description = gameObject.getString("description")
                            val imageUrl = gameObject.getString("image_url")


                            val schedule = Schedule(idevent, name, date, description, imageUrl)
                            scheduleList.add(schedule)
                        }

                        scheduleAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error parsing data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("ERROR", "Error fetching data: ${error.message}")
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}
