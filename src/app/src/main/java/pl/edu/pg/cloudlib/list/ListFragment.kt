package pl.edu.pg.cloudlib

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pl.edu.pg.cloudlib.databinding.FragmentListBinding
import pl.edu.pg.cloudlib.exhibit.ExhibitFragment
import pl.edu.pg.cloudlib.list.ListRowView

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private val dbCollectionName = "exhibits"
    private var db = Firebase.firestore

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

        val db1 = DBSingleton.getInstance()
        db1.add("dodane", "opis dodanego")

        db.collection(dbCollectionName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    addRow(document.data["name"].toString(), document.data["description"].toString(), "dbExampleMessage")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(DBSingleton.TAG, "Error getting documents.", exception)
            }

        return binding.root
    }

    fun addRow(title: String, subtitle: String, fragmentMessage: String) {
        val row = ListRowView(requireContext())
        row.title = title
        row.subtitle = subtitle
        row.imageView.setImageDrawable(null)
        row.fragmentMessage = fragmentMessage

        row.setOnClickListener {_ ->
            setFragmentResult(ExhibitFragment.BUNDLE_KEY,
                bundleOf(ExhibitFragment.BUNDLE_KEY to row.fragmentMessage))
        }

        binding.root.addView(row)

//        binding.root.forEach {
//            if (it is ListRowView)
//                it.setOnClickListener {_ ->
//                    setFragmentResult(ExhibitFragment.BUNDLE_KEY,
//                        bundleOf(ExhibitFragment.BUNDLE_KEY to it.fragmentMessage))
//                }
//        }
    }
}