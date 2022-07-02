package team.tiktok.tiktokapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding  : ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.fmNavHostGraph)
        binding.navBot.setupWithNavController(navController)

//        title and icon settings are displayed together
        binding.navBot.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED

    }

}