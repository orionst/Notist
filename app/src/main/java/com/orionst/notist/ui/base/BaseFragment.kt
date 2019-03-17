package com.orionst.notist.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.github.ajalt.timberkt.Timber
import com.orionst.notist.R
import com.orionst.notist.data.errors.NoAuthException


abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, S>

    companion object {
        private const val RC_SIGN_IN = 42001
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getViewState().observe(this, Observer<S> { viewState ->
            viewState?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }
            }
        })
        setHasOptionsMenu(true)
    }

    protected fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLoginScreen()
            else ->
                error.let { t ->
                    Timber.e(t)
                    t.message?.let {
                        showError(it)
                }
            }
        }
    }

    abstract fun renderData(data: T)

    protected fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
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
}
