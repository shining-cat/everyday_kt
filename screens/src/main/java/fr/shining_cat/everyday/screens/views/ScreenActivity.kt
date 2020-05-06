package fr.shining_cat.everyday.screens.views

import android.content.res.Resources
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.AbstractActivity
import fr.shining_cat.everyday.screens.R
import org.koin.android.ext.android.get

class ScreenActivity : AbstractActivity() {

    private val LOG_TAG = ScreenActivity::class.java.simpleName

    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)
        logger.d(LOG_TAG, "onCreate")
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.screens_nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController
        setupBottomNavMenu(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            logger.d(LOG_TAG, "Navigated to $dest")
        }
        hideLoadingView()
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }
}