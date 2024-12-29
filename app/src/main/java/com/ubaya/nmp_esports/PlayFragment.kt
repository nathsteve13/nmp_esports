package com.ubaya.nmp_esports

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.FragmentPlayBinding
import org.json.JSONArray
import org.json.JSONObject

class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private val gameList = mutableListOf<Game>()
    private lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        val view = binding.root

        gameAdapter = GameAdapter(gameList)
        binding.recGames.layoutManager = LinearLayoutManager(requireContext())
        binding.recGames.adapter = gameAdapter

        fetchGames()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchGames() {
        val url = "https://ubaya.xyz/native/160422124/get_games.php"
        val queue = Volley.newRequestQueue(requireContext())

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    Log.d("DEBUG", "Response: $response")
                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")
                        gameList.clear()

                        for (i in 0 until data.length()) {
                            val gameObject: JSONObject = data.getJSONObject(i)
                            val idgame = gameObject.getInt("idgame")
                            val name = gameObject.getString("name")
                            val description = gameObject.getString("description")
                            val imageUrl = gameObject.getString("image_url")

                            val game = Game(idgame, name, description, imageUrl)
                            gameList.add(game)
                        }

                        gameAdapter.notifyDataSetChanged()
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
