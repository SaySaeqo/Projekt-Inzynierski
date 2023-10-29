package pl.edu.pg.cloudlib

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import pl.edu.pg.cloudlib.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_list -> supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, ListFragment()).commit()
                R.id.nav_scanner -> supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, QRScannerFragment()).commit()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout,
            binding.topAppBar,R.string.open_nav, R.string.close_nav
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, ListFragment()).commit()
            binding.navView.setCheckedItem(binding.navView.id)
        }
    }
}