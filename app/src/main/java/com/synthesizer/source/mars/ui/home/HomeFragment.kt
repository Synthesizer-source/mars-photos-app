package com.synthesizer.source.mars.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        binding.apply {
            photoList.adapter = photoListAdapter
            photoListAdapter.itemClickListener = {
                navigateToPhotoDetail(it)
            }
            if (photoList.itemDecorationCount == 0) photoList.addItemDecoration(PhotoDecoration())

            toolbar.setOnMenuItemClickListener {
                selectFilter(it)
                true
            }

            rovers.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let { selectRover(it.position) }
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

    private fun updateFilterMenu(roverName: String) {
        binding.apply {
            toolbar.menu.clear()
            toolbar.inflateMenu(viewModel.getRoverCameraTypes(roverName)!!)
        }
    }

    private fun createNewPhotoListAdapter() {
        photoListAdapter = PhotoListAdapter()
        photoListAdapter.itemClickListener = {
            navigateToPhotoDetail(it)
        }
        binding.photoList.adapter = photoListAdapter
    }

    private fun selectFilter(menuItem: MenuItem) {
        if (menuItem.isChecked) return
        menuItem.isChecked = true
        createNewPhotoListAdapter()
        val roverName = viewModel.getRoverName(binding.rovers.selectedTabPosition)!!
        val camera =
            if (binding.toolbar.menu.getItem(0) == menuItem) null else menuItem.title.toString()
        viewModel.fetchPhotoList(
            roverName = roverName,
            camera = camera
        )
    }

    private fun selectRover(tabPosition: Int) {
        createNewPhotoListAdapter()
        viewModel.getRoverName(tabPosition)?.also { roverName ->
            viewModel.fetchPhotoList(roverName)
            updateFilterMenu(roverName)
        }
    }

    private fun navigateToPhotoDetail(id: Int) {
        val action = HomeFragmentDirections.goToPhotoDetail(id)
        findNavController().navigate(action)
    }
}