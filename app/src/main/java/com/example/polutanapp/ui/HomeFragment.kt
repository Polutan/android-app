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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.polutanapp.R
import com.example.polutanapp.ViewModelFactory
import com.example.polutanapp.databinding.FragmentHomeBinding
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.viewmodel.HomeViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeVieWModel:HomeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    private fun setUpViewModel() {
//        homeViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(dataStore))
//        )[HomeViewModel::class.java]

//        homeViewModel =
//            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

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

        homeVieWModel.getUser().observe(viewLifecycleOwner, {
            if (it.token.isNotEmpty()) {
                homeVieWModel.getScoreAQI(it.token)
                binding.tvCurrentAqi.text = homeVieWModel.getListScoreAQI().toString()
            }
        })

        binding.imageIconInfo.setOnClickListener {
            val mInfoFragment = InformationFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_home, mInfoFragment, InformationFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }


}