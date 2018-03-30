package com.stickerbox.tabbarcontroller

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_navigation_controller.*

class NavigationController : Fragment() {

    private lateinit var navigationView: NavigationView
    lateinit var rootFragment: Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_controller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView = navigation_view
        navigationView.rootFragment = rootFragment
        navigationView.fragmentManager = childFragmentManager
        navigationView.popToRoot()
    }

    fun pop() {
        navigationView.pop()
    }

    fun popToRoot() {
        navigationView.popToRoot()
    }

    fun push(fragment: Fragment) {
        navigationView.push(fragment)
    }

    fun onBackPressed() : Boolean {
        return if (!navigationView.canPop) {
            false
        } else {
            navigationView.pop()
            true
        }
    }

    companion object {

        fun newInstance(root: Fragment) : NavigationController {
            val instance = NavigationController()
            instance.rootFragment = root
            return instance
        }
    }

}

var Fragment.navigationController : NavigationController?
    get() = getNavigationController(parentFragment)
    set(value) {}

private fun Fragment.getNavigationController(parent: Fragment? = null) : NavigationController? {
    if (parentFragment == null) return null
    if (parent == null) return getNavigationController(parentFragment)
    if (parentFragment!! is NavigationController) {
        return parentFragment as NavigationController
    }
    return getNavigationController(parentFragment)
}