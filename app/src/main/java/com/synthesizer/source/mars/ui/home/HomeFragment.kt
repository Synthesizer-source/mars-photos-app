package com.synthesizer.source.mars.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.synthesizer.source.mars.R
import com.synthesizer.source.mars.databinding.FragmentHomeBinding
import com.synthesizer.source.mars.util.EventObserver
import com.synthesizer.source.mars.util.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
            photoList.itemAnimator = null
            photoList.adapter = photoListAdapter
            photoListAdapter.itemClickListener = {
                navigateToPhotoDetail(it)
            }
            photoListAdapter.firstItemLoadedListener = {
                viewModel.onDataLoaded()
            }
            if (photoList.itemDecorationCount == 0) photoList.addItemDecoration(PhotoDecoration())

            toolbar.setOnMenuItemClickListener {
                selectFilter(it)
                true
            }

            rovers.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    selectRover(tab?.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
        observe()
    }

    private fun observe() {
        viewModel.photoList.observe(viewLifecycleOwner, {
            photoListAdapter.submitData(lifecycle, it)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.apply {
                photoList.setVisibility(!it)
                progressBar.setVisibility(it)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, EventObserver {
            showError(it)
        })

        lifecycleScope.launch {
            photoListAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.handleLoadStatesError(loadStates)
            }
        }
    }

    private fun updateFilterMenu(tabPosition: Int?) {
        binding.apply {
            toolbar.menu.clear()
            viewModel.setCurrentCameraType(null)
            toolbar.inflateMenu(viewModel.getRoverCameraTypes(tabPosition))
        }
    }

    private fun selectFilter(menuItem: MenuItem) {
        if (menuItem.isChecked) return
        menuItem.isChecked = true
        val camera =
            if (binding.toolbar.menu.getItem(0) == menuItem) null else menuItem.title.toString()
        viewModel.setCurrentCameraType(camera)
    }

    private fun selectRover(tabPosition: Int?) {
        updateFilterMenu(tabPosition)
        viewModel.setCurrentRoverName(tabPosition)
    }

    private fun navigateToPhotoDetail(id: Int) {
        if (findNavController().currentDestination?.id == R.id.homeFragment) {
            val action = HomeFragmentDirections.goToPhotoDetail(id)
            findNavController().navigate(action)
        }
    }

    private fun showError(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) {
                photoListAdapter.retry()
            }.show()
    }
}