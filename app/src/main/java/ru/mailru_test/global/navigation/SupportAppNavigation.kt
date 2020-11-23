package ru.mailru_test.global.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Fade
import androidx.transition.Fade.IN
import androidx.transition.Fade.OUT
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.jetbrains.annotations.NotNull

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
        nextFragment?.enterTransition = Fade(IN)
        currentFragment?.exitTransition = Fade(OUT)
    }
}