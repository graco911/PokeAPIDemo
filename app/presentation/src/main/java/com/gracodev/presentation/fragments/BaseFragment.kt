package com.gracodev.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.gracodev.presentation.R
import com.gracodev.presentation.layouts.LoadingScreen

abstract class BaseFragment : Fragment() {
    abstract var TAG: String
    lateinit var dialog: LoadingScreen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = LoadingScreen()
    }

    open fun showDialog(tag: String) {
        dialog.show(childFragmentManager, tag)
    }

    open fun dismissDialog() {
        dialog.dismiss()
    }

    open fun showError(message: String) {
        requireView().snackbarError(message)
    }
}

fun View.snackbarError(message: String) {
    val snackbar = Snackbar
        .make(this, message, Snackbar.LENGTH_LONG).also { _ ->
        }
    snackbar.view.background = ContextCompat.getDrawable(
        context,
        R.color.error_color
    )
    snackbar.show()
}