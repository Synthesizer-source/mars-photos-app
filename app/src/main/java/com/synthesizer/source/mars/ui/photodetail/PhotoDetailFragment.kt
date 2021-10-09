package com.synthesizer.source.mars.ui.photodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.synthesizer.source.mars.databinding.FragmentPhotoDetailBinding
import com.synthesizer.source.mars.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PhotoDetailFragment : DialogFragment() {

    private val binding by lazy { FragmentPhotoDetailBinding.inflate(layoutInflater) }

    private val args: PhotoDetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: PhotoDetailViewModel.AssistedFactory
    private val viewModel by viewModels<PhotoDetailViewModel> {
        PhotoDetailViewModel.provideFactory(viewModelFactory, args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorMessage.observe(viewLifecycleOwner, EventObserver {
            showError(it)
        })
    }

    private fun showError(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }
}