package com.kamira.mortyverse

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.textfield.TextInputLayout
import com.kamira.mortyverse.core.utils.AdMobConfig
import com.kamira.mortyverse.data.network.ApiClient
import com.kamira.mortyverse.data.network.ApiService
import com.kamira.mortyverse.data.repositories.CharacterRepositoryImpl
import com.kamira.mortyverse.databinding.ActivityCharacterBinding
import com.kamira.mortyverse.domain.models.Character
import com.kamira.mortyverse.presentation.activities.CharacterDetailsActivity
import com.kamira.mortyverse.presentation.adapter.CharacterPagingAdapter
import com.kamira.mortyverse.presentation.extensions.applyToolbarInsets
import com.kamira.mortyverse.presentation.factory.AppViewModelFactory
import com.kamira.mortyverse.presentation.viewModel.CharacterViewModel

class CharacterActivity : AppCompatActivity() {
    private lateinit var characterAdapter: CharacterPagingAdapter
    private lateinit var errorDisplay: TextInputLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivityCharacterBinding
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.mainToolbar
        setSupportActionBar(toolbar)
        toolbar.applyToolbarInsets()
        errorDisplay = binding.errorDisplayLayout
        progressBar = binding.progressBar
        initializeRecyclerView()
        initializeViewModel()
        initializeAdMob()

    }

    private fun initializeRecyclerView() {
        characterAdapter = CharacterPagingAdapter({ character ->
            loadCharacterDetails(character)
        })
        binding.movieRecyclerView.apply {
            layoutAnimation=  AnimationUtils.loadLayoutAnimation(
                this@CharacterActivity,
                R.anim.layout_fall_down
            )
            layoutManager = LinearLayoutManager(this@CharacterActivity)
            addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = characterAdapter
            scheduleLayoutAnimation()
        }

        characterAdapter.addLoadStateListener { loadState ->
            progressBar.visibility = if (loadState.refresh is LoadState.Loading)
                View.VISIBLE
            else
                View.GONE
            val errorState =
                loadState.refresh as? LoadState.Error
            errorState?.let {
                errorDisplay.error =
                    it.error.localizedMessage
            }
        }

    }

    private fun initializeViewModel() {
        val apiClient: ApiClient = ApiService.apiClient
        val repo = CharacterRepositoryImpl(apiClient = apiClient)
        val factory = AppViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, factory = factory)[CharacterViewModel::class.java]
        viewModel.returnMovies().observe(this) { items ->
           characterAdapter.submitData(this.lifecycle, items)
        }
    }

    private fun loadCharacterDetails(character:Character) {
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra("character", character)
        startActivity(intent)
    }

    private fun initializeAdMob() {
        MobileAds.initialize(this)
        val adView = binding.adView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        InterstitialAd.load(
            this,
            AdMobConfig.INTERSTITIAL_CHARACTER,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }
            }
        )
    }

//    fun showAd() {
//        interstitialAd?.show(this)
//    }


}