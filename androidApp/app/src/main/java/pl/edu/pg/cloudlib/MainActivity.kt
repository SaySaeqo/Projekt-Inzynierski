package pl.edu.pg.cloudlib

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import pl.edu.pg.cloudlib.databinding.ActivityMainBinding
import pl.edu.pg.cloudlib.exhibit.LocationFragment
import pl.edu.pg.cloudlib.exhibit.ExhibitFragment
import pl.edu.pg.cloudlib.list.SearchFragment
import pl.edu.pg.cloudlib.exhibit.TextFragment
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
                R.id.nav_list -> redirect<ListFragment>()
                R.id.nav_scanner -> redirect<QRScannerFragment>()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        ActionBarDrawerToggle(this, binding.drawerLayout,
            binding.topAppBar, R.string.open_nav, R.string.close_nav
        ).let {
            binding.drawerLayout.addDrawerListener(it)
            it.syncState()
        }

        if (savedInstanceState == null){
            redirect<ListFragment>(backStack = false)
            binding.navView.setCheckedItem(R.id.nav_list)
        }

        setRedirectListener<ExhibitFragment>(Bundles.EXHIBIT_ID)
        setRedirectListener<TextFragment>(Bundles.SECTION_TEXT)
        setRedirectListener<SearchFragment>(Bundles.REDIRECT_TO_SEARCH)
        setRedirectListener<LocationFragment>(Bundles.LOCATION_URL)
        setRedirectListener<ListFragment>(Bundles.SEARCH_QUERY){ binding.navView.setCheckedItem(R.id.nav_list) }

        if (intent?.action == Intent.ACTION_VIEW) {
            intent?.data?.getQueryParameter("id")?.let{
                redirect<ExhibitFragment>(bundleOf(Bundles.EXHIBIT_ID to it))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    private inline fun <reified T> redirect(bundle: Bundle? = null, backStack: Boolean = true) where T: Fragment {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(binding.fragmentContainer.id, args = bundle)
            if (backStack) addToBackStack(null)
        }
        binding.navView.setCheckedItem(R.id.nav_none)
    }

    private inline fun <reified T> setRedirectListener(bundleKey: String, crossinline afterRedirect: () -> Unit = {}) where T: Fragment {
        supportFragmentManager.setFragmentResultListener(bundleKey, this)
        { _, bundle ->
            redirect<T>(bundle)
            afterRedirect()
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}