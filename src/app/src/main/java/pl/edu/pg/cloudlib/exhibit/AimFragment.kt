package pl.edu.pg.cloudlib.exhibit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        // abrakadabra, zgarniesz obrazek z message i wstawisz go do ImageView
        val imageView = ImageView(requireContext())
        imageView.setImageResource(R.drawable.ic_action_star)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.adjustViewBounds = true
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        binding.root.addView(imageView)

        return binding.root
    }

}