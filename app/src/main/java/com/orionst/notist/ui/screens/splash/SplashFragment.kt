package com.orionst.notist.ui.screens.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.orionst.notist.R
import com.orionst.notist.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class SplashFragment: BaseFragment<Boolean?, SplashViewState>() {

    override val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            it.bottom_app_bar.visibility = View.INVISIBLE
            it.fab.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            viewModel.requestUser()
        }, START_DELAY)

    }

    override fun renderData(data: Boolean?) {
        // if data==true
        data?.takeIf { it }?.let {
            val action = SplashFragmentDirections.actionSignInToApp()
            findNavController().navigate(action)
        }

    }

    companion object {
        private const val START_DELAY = 1000L
    }

}