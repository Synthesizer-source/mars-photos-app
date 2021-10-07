package com.synthesizer.source.mars.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.synthesizer.source.mars.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private var photoListAdapter = PhotoListAdapter()

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoList.apply {
            adapter = photoListAdapter
            if (itemDecorationCount == 0) addItemDecoration(PhotoDecoration())
        }
        binding.apply {
            rovers.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    photoListAdapter = PhotoListAdapter()
                    photoList.adapter = photoListAdapter
                    viewModel.getRoverName(tab!!.position)?.also { roverName ->
                        viewModel.fetchPhotoList(roverName)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }
        observe()
    }

    private fun observe() {
        viewModel.photoList.observe(viewLifecycleOwner, {
            photoListAdapter.submitData(lifecycle, it)
        })
    }
}