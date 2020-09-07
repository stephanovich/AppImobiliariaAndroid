package com.example.tp1imob

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tp1imob.model.Imobiliaria
import com.example.tp1imob.viewmodel.ImobiliariaViewModel

class MainActivity : AppCompatActivity() {

    lateinit var imobiliariaViewModel: ImobiliariaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        imobiliariaViewModel = ViewModelProviders.of(this)[ImobiliariaViewModel::class.java]
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        this.actionBar?.hide()
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val intt = intent
        val imobiliaria = intt.getSerializableExtra("imob") as Imobiliaria
        imobiliariaViewModel.imobiliaria = imobiliaria
    }
}
