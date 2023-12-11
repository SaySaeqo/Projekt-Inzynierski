package pl.edu.pg.cloudlib.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pl.edu.pg.cloudlib.DBSingleton
import pl.edu.pg.cloudlib.R
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

        activity?.title = getString(R.string.list_page_title)
        val searchText = arguments?.getString(SearchFragment.BUNDLE_KEY) ?: ""


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
                    setFragmentResult(
                        ExhibitFragment.BUNDLE_KEY,
                        bundleOf(ExhibitFragment.BUNDLE_KEY to it.fragmentMessage)
                    )
                }
        }


        val db = DBSingleton.getInstance()
        db.getAll().addOnSuccessListener { result ->
            for ( doc in result){
                if(doc.data["name"].toString().contains(searchText)){
                    addRow(doc, context);
                }
            }
        }

        return binding.root
    }

    fun addRow(doc: QueryDocumentSnapshot, context: Context?){
        val row = ListRowView(requireContext())
        row.id = doc.id
        row.title = doc.data["name"].toString();
        row.subtitle = doc.data["description"].toString();
        if(doc.data["icon"].toString() != "") {
            DBSingleton.getInstance().getImage(doc.data["icon"].toString()).downloadUrl.addOnSuccessListener {
                Glide.with(requireContext())
                    .load(it)
                    .into(row.imageView)
                row.fragmentMessage = ""
            }.addOnFailureListener(){
                Log.d("ListFragment", "Failed to load image")
            }
        }
        else {
            row.imageView.setImageDrawable(null)
        }

        row.setOnClickListener {_ ->
            setFragmentResult(ExhibitFragment.BUNDLE_KEY,
                bundleOf(ExhibitFragment.BUNDLE_KEY to row.fragmentMessage,
                    ExhibitFragment.BUNDLE_ID to row.id)
            )
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