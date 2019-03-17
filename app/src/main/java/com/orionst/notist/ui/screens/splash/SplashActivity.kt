package com.orionst.notist.ui.screens.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.github.ajalt.timberkt.Timber
import com.orionst.notist.R
import com.orionst.notist.data.errors.NoAuthException
import com.orionst.notist.ui.MainActivity

class SplashActivity: AppCompatActivity() {

    private val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getViewState().observe(this, Observer<SplashViewState> { viewState ->
            viewState?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            viewModel.requestUser()
        }, START_DELAY)
    }

    private fun renderData(data: Boolean?) {
        // if data==true
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }

    private fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLoginScreen()
            else -> error?.let { t ->
                Timber.e(t)
                t.message?.let {
                    showError(it)
                }
            }
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun startLoginScreen() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android_robot)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK){
            finish()
        }
    }

    companion object {
        private const val RC_SIGN_IN = 42001
        private const val START_DELAY = 1000L
    }
}
