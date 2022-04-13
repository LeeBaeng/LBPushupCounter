package com.leebaeng.lbpushupcounter.presenter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.leebaeng.util.log.logV

open abstract class BaseFragment : Fragment {
    constructor(resId: Int) : super(resId)
    constructor() : super()

    protected lateinit var navController: NavController
    private var toast: Toast? = null

    fun showToast(str: String) {
        toast?.cancel()
        toast = Toast.makeText(context, str, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        "onInflate($context, $attrs, $savedInstanceState)".logV(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate($savedInstanceState)".logV(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        "onCreateView($inflater, $container, $savedInstanceState)".logV(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "onViewCreated($view, $savedInstanceState)".logV(this)
        navController = Navigation.findNavController(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        "onAttach($context)".logV(this)
    }

    override fun onStart() {
        super.onStart()
        "onStart".logV(this)
    }

    override fun onResume() {
        super.onResume()
        "onResume".logV(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        "onHiddenChanged($hidden)".logV(this)
    }

    override fun onPause() {
        super.onPause()
        "onPause".logV(this)
    }

    override fun onStop() {
        super.onStop()
        "onStop".logV(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        "onSaveInstanceState($outState)".logV(this)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        "onViewStateRestored($savedInstanceState)".logV(this)
    }

    override fun onDetach() {
        super.onDetach()
        "onDetach".logV(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        "onDestroyView".logV(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".logV(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        "onActivityCreated($savedInstanceState)".logV(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        "onActivityResult($requestCode, $requestCode, $data)".logV(this)
    }
}