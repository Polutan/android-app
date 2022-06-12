package com.example.polutanapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.polutanapp.R
import com.example.polutanapp.ViewModelFactory
import com.example.polutanapp.adapter.ScoreAdapter
import com.example.polutanapp.databinding.FragmentHomeBinding
import com.example.polutanapp.model.UserModel
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.viewmodel.HomeViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var user: UserModel
    private lateinit var scoreAdapter: ScoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setUpViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireActivity().dataStore))
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()

        scoreAdapter = ScoreAdapter()

        homeViewModel.getUser().observe(viewLifecycleOwner, {
            if (it.token.isNotEmpty()) {
                homeViewModel.getScoreAQI(it.token)
                this.user = it
                binding.tvCurrentAqi.text = user.score.toString()
                textForCategory()

                binding.rvItems.layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
                binding.rvItems.setHasFixedSize(true)
                binding.rvItems.adapter = scoreAdapter

            }
        })

        binding.imageIconInfo.setOnClickListener {
            val mInfoFragment = InformationFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragment_home,
                    mInfoFragment,
                    InformationFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun textForCategory() {
        if (user.score in 0..50) {
            binding.tvCurrentNameAqi.text = "Good"
        } else if (user.score in 51..100) {
            binding.tvCurrentNameAqi.text = "Moderate"
        } else if (user.score in 101..150) {
            binding.tvCurrentNameAqi.text = "Unhealthy for sensitive group"
        } else if (user.score in 151..200) {
            binding.tvCurrentNameAqi.text = "Unhealthy"
        } else if (user.score in 201..300) {
            binding.tvCurrentNameAqi.text = "Very Unhealthy"
        }
    }

}