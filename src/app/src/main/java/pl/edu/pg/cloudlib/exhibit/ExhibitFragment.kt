package pl.edu.pg.cloudlib.exhibit

import android.net.Uri
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.FragmentExhibitBinding


class ExhibitFragment : Fragment() {

    private lateinit var binding: FragmentExhibitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExhibitBinding.inflate(inflater, container, false)

        activity?.title = getString(R.string.exhibit_page_title)

        val test = arguments?.getString(BUNDLE_KEY) ?: "Error: no message arrived"
        binding.exampleText.text = test

        binding.exampleGallery.images = intArrayOf(
            R.drawable.ic_action_filter,
            R.drawable.ic_action_close,
            R.drawable.ic_action_star_outline,
            R.drawable.ic_action_star,
            R.drawable.ic_action_share,
            R.drawable.ic_action_menu,
            R.drawable.ic_action_filter,
            R.drawable.ic_action_close,
            R.drawable.ic_action_star_outline,
            R.drawable.ic_action_star,
            R.drawable.ic_action_share,
            R.drawable.ic_action_menu,
        )

        binding.exampleSection.apply {
            text = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        """.trimIndent()
            button.setOnClickListener{
                setFragmentResult(
                    SectionFragment.BUNDLE_KEY,
                    binding.exampleSection.getBundle()
                )
            }
        }


        binding.exampleLink.apply {
            title = "Google"
            iconId = R.drawable.baseline_exit_to_app_24
            link = Uri.parse("https://google.com")
        }

        // add 15 example keys to the extra map
        for (i in 1..15) {
            binding.exampleExtra.setExtra("Key $i", "Value $i")
        }


        return binding.root
    }

    companion object{
        const val BUNDLE_KEY = "ExhibitMessage"
    }
}