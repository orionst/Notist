package com.orionst.notist.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.Timber


abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, S>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getViewState().observe(this, Observer<S> {
            viewState ->
                if (viewState == null) return@Observer
                if (viewState.data != null) renderData(viewState.data)
                if (viewState.error != null) renderError(viewState.error)
        })
        setHasOptionsMenu(true)
    }

    protected fun renderError(error: Throwable) {
        error.let {t ->
            Timber.e(t)
                t.message?.let {
                    showError(it)
                }
        }
    }

    abstract fun renderData(data: T)

    protected fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }
}
