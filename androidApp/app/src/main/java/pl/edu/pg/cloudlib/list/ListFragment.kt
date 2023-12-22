package pl.edu.pg.cloudlib.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.bumptech.glide.Glide
import pl.edu.pg.cloudlib.Bundles
import pl.edu.pg.cloudlib.database.DBSingleton
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.database.Exhibit
import pl.edu.pg.cloudlib.databinding.FragmentListBinding
import pl.edu.pg.cloudlib.exhibit.ExhibitFragment

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

        setHasOptionsMenu(true)
        val searchText = arguments?.getString(Bundles.SEARCH_QUERY) ?: ""
        activity?.title = if (searchText.isBlank()) getString(R.string.list_page_title) else "\"${searchText}\""

        DBSingleton.forAll {
            if(it.name.lowercase().contains(searchText.lowercase())) {
                addRow(it, context);
            }
        }

        return binding.root
    }

    private fun addRow(exh: Exhibit, context: Context?){
        val row = ListRowView(requireContext())
        row.id = exh.id
        row.title = exh.name
        row.subtitle = exh.description
        if(exh.icon != "") DBSingleton.loadImageInto(exh.icon, row.imageView)
        row.setOnClickListener {_ -> setFragmentResult(Bundles.EXHIBIT_ID, row.bundle) }

        binding.root.addView(row)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        menu.findItem(R.id.search).apply {
            isVisible = true
            setOnMenuItemClickListener {
                setFragmentResult(Bundles.REDIRECT_TO_SEARCH, bundleOf())
                true
            }
        }
    }
}