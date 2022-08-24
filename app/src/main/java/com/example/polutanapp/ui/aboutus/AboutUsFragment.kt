package com.example.polutanapp.ui.aboutus

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.polutanapp.R
import com.example.polutanapp.databinding.FragmentAboutUsBinding
import com.example.polutanapp.ui.home.HomeFragment
import com.example.polutanapp.ui.setting.SettingActivity


class AboutUsFragment : Fragment() {
    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarAboutUs.setNavigationIcon(R.drawable.icon_back)
        binding.toolbarAboutUs.setNavigationOnClickListener {
            val mHomeFragment = HomeFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_about_us, mHomeFragment, HomeFragment::class.java.simpleName)
//                addToBackStack(null)
                commit()
            }
        }

        binding.toolbarAboutUs.inflateMenu(R.menu.option_menu)
        binding.toolbarAboutUs.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting -> {
                    val intent = Intent(context, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

}