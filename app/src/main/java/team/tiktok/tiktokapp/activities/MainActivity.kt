package team.tiktok.tiktokapp.activities

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding  : ActivityMainBinding
    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fmNavHostGraph)
        setupSmoothBottomMenu()


//        title and icon settings are displayed together


    }


    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bot_menu)
        val menu = popupMenu.menu
        binding.navBot.setupWithNavController(menu, navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}