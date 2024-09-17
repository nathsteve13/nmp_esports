package com.ubaya.nmp_esports

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ubaya.nmp_esports.databinding.FragmentHomeBinding
import com.ubaya.nmp_esports.databinding.FragmentScheduleBinding

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
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentScheduleBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnSch1.setOnClickListener {
            val scheduleTitle = "Open Winter Cup - Valorant"
            val scheduleTime = "09-09-2024 13.00 PM"

            val intent = Intent(activity, ScheduleDetailActivity::class.java)
            intent.putExtra(SCHEDULE_TITLE, scheduleTitle)
            intent.putExtra(SCHEDULE_TIME, scheduleTime)
            startActivity(intent)
        }

        binding.btnSch2.setOnClickListener {
            val scheduleTitle = "Open Winter Cup - Mobile Legend"
            val scheduleTime = "09-09-2024 13.00 PM"

            val intent = Intent(activity, ScheduleDetailActivity::class.java)
            intent.putExtra(SCHEDULE_TITLE, scheduleTitle)
            intent.putExtra(SCHEDULE_TIME, scheduleTime)
            startActivity(intent)
        }

        binding.btnSch3.setOnClickListener {
            val scheduleTitle = "Open Winter Cup - Fortnite"
            val scheduleTime = "09-09-2024 13.00 PM"

            val intent = Intent(activity, ScheduleDetailActivity::class.java)
            intent.putExtra(SCHEDULE_TITLE, scheduleTitle)
            intent.putExtra(SCHEDULE_TIME, scheduleTime)
            startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        const val SCHEDULE_TITLE = "schedule_title"
        const val SCHEDULE_TIME = "schedule_time"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
