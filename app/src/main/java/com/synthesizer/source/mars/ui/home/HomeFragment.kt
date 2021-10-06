package com.synthesizer.source.mars.ui.home

import androidx.fragment.app.Fragment
import com.synthesizer.source.mars.R
import com.synthesizer.source.mars.data.api.ApiService
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var service: ApiService

    override fun onStart() {
        super.onStart()
        service.getPhotos().enqueue(object : Callback<HashMap<String, Any>> {
            override fun onResponse(
                call: Call<HashMap<String, Any>>,
                response: Response<HashMap<String, Any>>
            ) {
                if (response.isSuccessful) {
                    println(response.body())
                }
            }

            override fun onFailure(call: Call<HashMap<String, Any>>, t: Throwable) {
                println(t.message)
            }
        })
    }
}