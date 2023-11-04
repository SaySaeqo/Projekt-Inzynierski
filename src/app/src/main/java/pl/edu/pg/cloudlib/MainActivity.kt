package pl.edu.pg.cloudlib

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
                R.id.nav_list -> supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<ListFragment>(binding.fragmentContainer.id)
                    addToBackStack(null)
                }
                R.id.nav_scanner -> supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<QRScannerFragment>(binding.fragmentContainer.id)
                    addToBackStack(null)
                }
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
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ListFragment>(binding.fragmentContainer.id)
                addToBackStack(null)
            }
            binding.navView.setCheckedItem(R.id.nav_list)
        }

        supportFragmentManager.setFragmentResultListener(QRScannerFragment.BUNDLE_KEY, this)
        { _, bundle ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ExhibitFragment>(binding.fragmentContainer.id, args = bundle)
                addToBackStack(null)
            }
            binding.navView.setCheckedItem(0)
        }
    }

    companion object{
        private const val TAG = "MAIN"
    }
}