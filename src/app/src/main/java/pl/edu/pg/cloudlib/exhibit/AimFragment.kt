package pl.edu.pg.cloudlib.exhibit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import pl.edu.pg.cloudlib.DBSingleton
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.FragmentAimBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AimFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AimFragment : Fragment() {

    private lateinit var binding: FragmentAimBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAimBinding.inflate(inflater, container, false)
        activity?.title = getString(R.string.aim_page_title)

        val message = arguments?.getString(ExhibitFragment.AIM_KEY) ?: ""
        val imageView = ImageView(requireContext())
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.adjustViewBounds = true
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.minimumHeight = 400
        binding.root.addView(imageView)

        if(message != "") {
            DBSingleton.getInstance().getImage(message).downloadUrl.addOnSuccessListener {
                Glide.with(requireContext())
                    .load(it)
                    .into(imageView)
            }.addOnFailureListener(){
                Log.d("ListFragment", "Failed to load image")
            }
        }

        return binding.root
    }

}