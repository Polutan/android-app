package com.example.polutanapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.polutanapp.R
import com.example.polutanapp.databinding.FragmentAboutUsBinding


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

        }

            binding.toolbarAboutUs.inflateMenu(R.menu.option_menu)
            binding.toolbarAboutUs.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.setting -> {
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