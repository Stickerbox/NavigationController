package com.stickerbox.tabbarcontroller

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import android.widget.FrameLayout

class NavigationView @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private enum class Operation {
        FORWARD, BACKWARD, RESET
    }

    lateinit var rootFragment: Fragment
    var fragmentManager: FragmentManager? = null

    private var activeFragment: Fragment? = null
    private val fragments = mutableListOf<Fragment>()

    val hasFragmentsVisible: Boolean
        get() = fragments.isNotEmpty()

    val canPop: Boolean
        get() = fragments.size > 1

    fun push(fragment: Fragment) {
        fragments.add(fragment)
        updateUi(Operation.FORWARD)
    }

    fun pop() {
        if (!canPop) return
        fragments.removeAt(fragments.size - 1)
        updateUi(Operation.BACKWARD)
    }

    fun popToRoot() {
        updateUi(Operation.RESET)
    }

    private fun updateUi(op: Operation) {
        if (fragmentManager == null) throw IllegalStateException("Fragment manager has not be set")
        val transaction = fragmentManager!!.beginTransaction()
        if (activeFragment != null) {
            transaction.detach(activeFragment)
        }
        when (op) {
            Operation.FORWARD -> {
                activeFragment = fragments.last()
                transaction.add(id, activeFragment)
            }
            Operation.BACKWARD -> {
                transaction.remove(activeFragment)
                activeFragment = fragments.last()
                transaction.attach(activeFragment)
            }
            Operation.RESET -> {
                fragments.forEach {
                    transaction.remove(it)
                }
                fragments.clear()
                activeFragment = rootFragment
                fragments.add(activeFragment!!)
                transaction.add(id, activeFragment)
            }
        }
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

}