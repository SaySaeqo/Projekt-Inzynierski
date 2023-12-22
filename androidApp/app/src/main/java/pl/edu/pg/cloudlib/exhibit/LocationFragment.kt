package pl.edu.pg.cloudlib.exhibit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import pl.edu.pg.cloudlib.Bundles
import pl.edu.pg.cloudlib.database.DBSingleton
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.FragmentLocationBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLocationBinding.inflate(inflater, container, false)
        activity?.title = getString(R.string.aim_page_title)

        arguments?.getString(Bundles.LOCATION_URL)?.let {
            DBSingleton.loadImageInto(it, binding.locationImageView)
        }

        return binding.root
    }

}