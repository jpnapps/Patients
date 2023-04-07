package com.jpndev.patients


import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jpndev.newsapiclient.presentation.PItemAdapter
import com.jpndev.patients.databinding.ActivityMainBinding
import com.jpndev.patients.presentation.ui.MainViewModel
import com.jpndev.patients.presentation.ui.MainViewModelFactory
import com.jpndev.patients.ui.dashboard.DashboardFragment
import com.jpndev.patients.ui.more.MoreFragment
import com.jpndev.patients.utils.extensions.setTitleColor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainFactory: MainViewModelFactory
    lateinit var viewMainModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var pitemadapter: PItemAdapter
    //private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMainModel = ViewModelProvider(this, mainFactory).get(MainViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*  val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController= navHostFragment.navController
         //   val navController = findNavController(R.id.fragmentContainerView)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_more
                )
            )
        // setupActionBarWithNavController(navController, appBarConfiguration)
           binding.navView.setupWithNavController(navController)*/

        //Working one
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(
            navController
        )
       /*  navController = findNavController(R.id.main_fragment)
        setupActionBarWithNavController(navController)*/
        //  setBioPrompt()
        //  setBioAuth()

        //setBottomMenu()
    }

    private fun setBottomMenu() {
        binding.navView.setItemIconTintList(null);
        // Create a ColorStateList for the selected and unselected states
        val selectedColorStateList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.white))
        val unselectedColorStateList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.black_2))
        // Set the selected and unselected colors for the icon and text
        /*binding.navView.itemIconTintList = unselectedColorStateList
        binding.navView.itemTextColor = unselectedColorStateList*/
        // Set the unselected color for all items
        binding.navView.itemIconTintList = unselectedColorStateList
        for (i in 0 until binding.navView.menu.size()) {
            val menuItem = binding.navView.menu.getItem(i)
            menuItem.iconTintList = unselectedColorStateList
            menuItem.setTitleColor(ContextCompat.getColor(this, R.color.black_2))
        }
        // Set a listener to change the colors when an item is selected
        binding.navView.setOnNavigationItemSelectedListener { item ->
            // Set the selected color for the item that was just selected
             //   item.iconTintList = selectedColorStateList
            item.setTitleColor(ContextCompat.getColor(this, R.color.white))
            item.iconTintList = selectedColorStateList
            // Set the unselected color for all other items
        /*    for (i in 0 until binding.navView.menu.size()) {
                val menuItem = binding.navView.menu.getItem(i)
                if (menuItem != item) {
                    menuItem.iconTintList = unselectedColorStateList
                    menuItem.setTitleColor(ContextCompat.getColor(this, R.color.black_2))
                }
                else
                {
                    menuItem.iconTintList = selectedColorStateList
                    menuItem.setTitleColor(ContextCompat.getColor(this, R.color.white))
                }
            }*/
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    // Load the dashboard fragment
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, DashboardFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_more -> {
                    // Load the more fragment
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, MoreFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }


/*    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private fun setBioPrompt() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()

                    finish()
                }
            })
    }

    private fun setBioAuth() {
        // Allows user to authenticate using either a Class 3 biometric or
        // their lock screen credential (PIN, pattern, or password).
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()
            // Can't call setNegativeButtonText() and
            // setAllowedAuthenticators(... or DEVICE_CREDENTIAL) at the same time.
            // .setNegativeButtonText("Use account password")

        biometricPrompt.authenticate(promptInfo)
    }*/
}