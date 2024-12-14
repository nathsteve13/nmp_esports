import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.JoinProposalAdapter
import com.ubaya.nmp_esports.databinding.ActivityJoinProposalBinding
import com.ubaya.nmp_esports.proposal
import org.json.JSONArray
import org.json.JSONObject

class JoinProposalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinProposalBinding
    private lateinit var joinProposalAdapter: JoinProposalAdapter
    private val proposalList = mutableListOf<proposal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinProposalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        joinProposalAdapter = JoinProposalAdapter(proposalList)
        binding.recProposal.apply {
            layoutManager = LinearLayoutManager(this@JoinProposalActivity)
            adapter = joinProposalAdapter
        }

        fetchProposalData()
    }

    private fun fetchProposalData() {
        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)

        if (userId != -1) {
            val url = "https://ubaya.xyz/native/160422124/get_proposal.php"

            val params = HashMap<String, Any>()
            params["idmember"] = userId

            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, url, JSONObject(params as Map<*, *>),
                Response.Listener { response ->
                    try {
                        val dataArray: JSONArray = response.getJSONArray("data")

                        proposalList.clear()
                        for (i in 0 until dataArray.length()) {
                            val proposalObject: JSONObject = dataArray.getJSONObject(i)
                            val idJoinProposal = proposalObject.getInt("idjoin_proposal")
                            val idMember = proposalObject.getInt("idmember")
                            val idTeam = proposalObject.getInt("idteam")
                            val description = proposalObject.getString("description")
                            val status = proposalObject.getString("status")
                            val teamName = proposalObject.getString("name")

                            proposalList.add(proposal(idJoinProposal, idMember, idTeam, description, status, teamName))
                        }

                        joinProposalAdapter.notifyDataSetChanged()

                    } catch (e: Exception) {
                        Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
                }) {
            }

            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(jsonObjectRequest)
        } else {
            Toast.makeText(this, "User ID not found in session.", Toast.LENGTH_SHORT).show()
        }
    }

}
