package pl.edu.pg.cloudlib.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.FragmentSearchBinding

/* A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        activity?.title = getString(R.string.search_page_title)

        binding.searchButton.setOnClickListener {
            val query = binding.searchInput.text.toString()
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY, query)
            setFragmentResult(BUNDLE_KEY, bundle)
        }

        return binding.root
    }

    companion object {
        const val BUNDLE_KEY = "search"
    }

}