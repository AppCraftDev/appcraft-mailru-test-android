package ru.mailru_test.global.navigation

import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Fade
import androidx.transition.Slide
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.jetbrains.annotations.NotNull
import ru.mailru_test.app.feature.splash.ContactsFragment
import ru.mailru_test.global.ui.fragment.BaseFragment

open class SupportAppNavigation(private val appActivity: @NotNull AppCompatActivity, containerId: Int) :
    AppNavigator(appActivity, containerId) {

    override fun setupFragmentTransaction(fragmentTransaction: FragmentTransaction, currentFragment: Fragment?, nextFragment: Fragment?) {
        super.setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
        fragmentTransaction.setReorderingAllowed(true)

        setupTransitionAnimation(currentFragment, nextFragment)
    }

    override fun applyCommands(commands: Array<out Command>) {
        super.applyCommands(commands)
        appActivity.supportFragmentManager.executePendingTransactions()
    }

    private fun setupTransitionAnimation(currentFragment: Fragment?, nextFragment: Fragment?) {
        val toTopAnim = nextFragment != null && fragmentToTopAnimation.any { nextFragment::class == it }
        when {
            toTopAnim -> {
                nextFragment?.enterTransition = Slide(Gravity.BOTTOM)
                currentFragment?.exitTransition = Slide(Gravity.TOP)
            }
            else -> {
                if (currentFragment is ContactsFragment) {
                    nextFragment?.enterTransition = Fade()
                    currentFragment.exitTransition = Fade()
                } else {
                    nextFragment?.enterTransition = Slide(Gravity.END)
                    currentFragment?.exitTransition = Slide(Gravity.START)
                }
            }
        }
    }

    private val fragmentToTopAnimation = listOf<BaseFragment>(
//        CheckListFragment::class
    )
}