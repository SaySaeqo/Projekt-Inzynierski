package pl.edu.pg.cloudlib.exhibit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.FragmentTextBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TextFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TextFragment : Fragment() {

    private lateinit var binding: FragmentTextBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextBinding.inflate(inflater, container, false)

        activity?.title = getString(R.string.section_page_title)

        binding.textView.text = arguments?.getString(BUNDLE_KEY) ?: "Error: no message arrived"

        return binding.root
    }

    companion object {
        const val BUNDLE_KEY = "section"
    }
}