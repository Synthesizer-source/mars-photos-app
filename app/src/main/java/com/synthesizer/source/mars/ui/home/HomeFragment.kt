package com.synthesizer.source.mars.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.remote.PhotoListResponse
import com.synthesizer.source.mars.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(LayoutInflater.from(context)) }

    val photoListAdapter = PhotoListAdapter()

    @Inject
    lateinit var service: ApiService

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
    }

    override fun onStart() {
        super.onStart()
        service.getPhotos("curiosity", 1).enqueue(object : Callback<PhotoListResponse> {
            override fun onResponse(
                call: Call<PhotoListResponse>,
                response: Response<PhotoListResponse>
            ) {
                if (response.isSuccessful) {
                    println(response.body())
                    photoListAdapter.addData(response.body()!!.photos)
                }
            }

            override fun onFailure(call: Call<PhotoListResponse>, t: Throwable) {
                println(t.message)
            }

        })
    }
}