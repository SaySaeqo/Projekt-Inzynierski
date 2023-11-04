package pl.edu.pg.cloudlib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import pl.edu.pg.cloudlib.databinding.FragmentListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        activity?.title = getString(R.string.list_page_title)

        // for exhibit in db
        val row = ListRowView(requireContext())
        row.title = "test title"
        row.subtitle = "test subtitle"
        row.imageView.setImageDrawable(null)
        row.fragmentMessage = "test message"
        binding.root.addView(row)

        binding.root.forEach {
            if (it is ListRowView)
                it.setOnClickListener {_ ->
                    setFragmentResult(ExhibitFragment.BUNDLE_KEY,
                        bundleOf(ExhibitFragment.BUNDLE_KEY to it.fragmentMessage))
                }
        }

        return binding.root
    }
}