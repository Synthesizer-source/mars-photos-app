package com.synthesizer.source.mars.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.synthesizer.source.mars.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(LayoutInflater.from(context)) }

    val photoListAdapter = PhotoListAdapter()

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
        observe()
    }

    private fun observe() {
        viewModel.photoList.observe(viewLifecycleOwner, {
            photoListAdapter.addData(it.photos)
        })
    }
}