package pl.edu.pg.cloudlib

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import pl.edu.pg.cloudlib.databinding.ActivityMainBinding
import pl.edu.pg.cloudlib.exhibit.AimFragment
import pl.edu.pg.cloudlib.exhibit.ExhibitFragment
import pl.edu.pg.cloudlib.list.SearchFragment
import pl.edu.pg.cloudlib.exhibit.SectionFragment
import pl.edu.pg.cloudlib.list.ListFragment
import pl.edu.pg.cloudlib.scanner.QRScannerFragment


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
            }
            binding.navView.setCheckedItem(R.id.nav_list)
        }

        supportFragmentManager.setFragmentResultListener(ExhibitFragment.BUNDLE_KEY, this)
        { _, bundle ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ExhibitFragment>(binding.fragmentContainer.id, args = bundle)
                addToBackStack(null)
            }
            binding.navView.setCheckedItem(R.id.menu_none)
        }
        supportFragmentManager.setFragmentResultListener(SectionFragment.BUNDLE_KEY, this)
        { _, bundle ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SectionFragment>(binding.fragmentContainer.id, args = bundle)
                addToBackStack(null)
            }
            binding.navView.setCheckedItem(R.id.menu_none)
        }
        supportFragmentManager.setFragmentResultListener(SearchFragment.BUNDLE_KEY, this)
        { _, bundle ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ListFragment>(binding.fragmentContainer.id, args = bundle)
                addToBackStack(null)
            }
            binding.navView.setCheckedItem(R.id.nav_list)
        }
        supportFragmentManager.setFragmentResultListener(ExhibitFragment.AIM_KEY, this)
        { _, bundle ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AimFragment>(binding.fragmentContainer.id, args = bundle)
                addToBackStack(null)
            }
            binding.navView.setCheckedItem(R.id.menu_none)
        }

        val action: String? = intent?.action
        val data: Uri? = intent?.data

        if (action == Intent.ACTION_VIEW && data != null) {
            val id = data.getQueryParameter("id")
            if (id != null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<ExhibitFragment>(binding.fragmentContainer.id,
                        args = bundleOf(ExhibitFragment.BUNDLE_KEY to id))
                    addToBackStack(null)
                }
                binding.navView.setCheckedItem(R.id.menu_none)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_app_bar, menu)

        menu?.findItem(R.id.search)?.setOnMenuItemClickListener{
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SearchFragment>(binding.fragmentContainer.id)
                addToBackStack(null)
            }
            binding.navView.setCheckedItem(R.id.menu_none)
            true
        }
        return true
    }

    companion object {
        const val TAG = "MainActivity"
    }
}