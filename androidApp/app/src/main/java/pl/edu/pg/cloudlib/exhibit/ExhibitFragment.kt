package pl.edu.pg.cloudlib.exhibit

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.firebase.firestore.DocumentSnapshot
import pl.edu.pg.cloudlib.DBSingleton
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.FragmentExhibitBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Exhibit {
    lateinit var id: String; // id of exhibit
    lateinit var name: String; // name of exhibit
    lateinit var description: String; //short description
    lateinit var extra: Array<ExhibitPair>; //extra parameters
    lateinit var widgets: Array<Widget>;
    lateinit var icon: String; // path to image with icon
    lateinit var location: String; // path to image with location on exhibition

    fun init(
        /*id: String,
        name: String,
        description: String,
        extra: Array<ExhibitPair>,
        widgets: Array<Widget>,
        icon: String,
        location: String*/
    ) {
        this.id = "id"
        this.name = "name"
        this.description = "description"
        this.extra = arrayOf()//extra
        this.widgets = arrayOf()//widgets
        this.icon = "icon"
        this.location = "location"
    }

    companion object {
        fun fromFirebaseConverter(doc: DocumentSnapshot): Exhibit {
            val gson = Gson()
            val newExhibit = Exhibit()
            newExhibit.id = doc.id
            newExhibit.name = doc.data?.get("name").toString()
            newExhibit.description = doc.data?.get("description").toString()
            newExhibit.widgets = Gson().fromJson(doc.data?.get("widgets").toString(), object : TypeToken<Array<Widget>>() {}.type)
            newExhibit.extra = Gson().fromJson(doc.data?.get("extra").toString(), object : TypeToken<Array<ExhibitPair>>() {}.type)
            newExhibit.icon = doc.data?.get("icon").toString();
            newExhibit.location = doc.data?.get("location").toString();

            return newExhibit;
        }
    }

}

class Widget {
    var id: Int = 0;
    lateinit var type: String;
    lateinit var title: String;
    lateinit var data: String;
    lateinit var imagesURLs: Array<String>;

    fun init(
        id: Int,
        type: String,
        title: String,
        data: String,
        imagesURLs: Array<String>
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.data = data;
        this.imagesURLs = imagesURLs;
    }
}

class ExhibitPair {
    lateinit var value: String;
    lateinit var key: String;
    lateinit var linkId: String;

    fun init(value: String, key: String, linkId: String) {
        this.value = value;
        this.key = key;
        this.linkId = linkId;
    }
}
class ExhibitFragment : Fragment() {

    private lateinit var binding: FragmentExhibitBinding
    private lateinit var test: String
    private lateinit var id: String
    var exhibit: Exhibit = Exhibit();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExhibitBinding.inflate(inflater, container, false)

        test = arguments?.getString(BUNDLE_KEY) ?: "Error: no message arrived"
        binding.exampleText.text = test
        this.id = arguments?.getString(BUNDLE_ID) ?: "Error: no id arrived"

        if(this.id != ""){
            DBSingleton.getInstance().getOne(this.id).addOnSuccessListener { document ->
                if(document != null) {
                    exhibit = Exhibit.fromFirebaseConverter(document)
                    activity?.title = exhibit.name
                    setHasOptionsMenu(true)

                    for(w in exhibit.widgets){
                        if(w.type == "gallery"){
                            val galleryWidgetView = GalleryWidgetView(requireContext())
                            galleryWidgetView.apply{
                                images = w.imagesURLs;
                            }

                            val linearLayout: LinearLayout = binding.root.findViewById(R.id.example_container)
                            linearLayout.addView(galleryWidgetView);
                        }
                        else if(w.type == "text"){
                            val sectionWidgetView = TextWidgetView(requireContext())
                            sectionWidgetView.apply{
                                text = w.data
                                button.setOnClickListener{
                                    setFragmentResult(
                                        TextFragment.BUNDLE_KEY,
                                        sectionWidgetView.getBundle()
                                    )
                                }
                            }

                            val linearLayout: LinearLayout = binding.root.findViewById(R.id.example_container)
                            linearLayout.addView(sectionWidgetView);
                        }
                        else if(w.type == "link") {
                            val linkWidgetView = LinkWidgetView(requireContext())
                            linkWidgetView.apply{
                                title = w.title
                                iconId = R.drawable.baseline_exit_to_app_24
                                link = Uri.parse(w.data)
                            }

                            val linearLayout: LinearLayout = binding.root.findViewById(R.id.example_container)
                            linearLayout.addView(linkWidgetView);
                        }
                    }

                    for(e in exhibit.extra){
                        binding.exampleExtra.setExtra(e.key, e.value).setOnClickListener { _ ->
                            if(e.linkId != "") {
                                setFragmentResult(
                                    BUNDLE_KEY,
                                    bundleOf(BUNDLE_KEY to "",
                                        BUNDLE_ID to e.linkId)
                                )
                            }
                        }
                    }
                }
            }.addOnFailureListener(){
                activity?.title = getString(R.string.exhibit_page_title)

                setHasOptionsMenu(true)
            }
        }

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val aimItem = menu.findItem(R.id.aim)
        if(exhibit.location != ""){
            aimItem.setVisible(true)
            aimItem.setOnMenuItemClickListener {
                setFragmentResult(
                    AIM_KEY,
                    bundleOf(AIM_KEY to exhibit.location)
                )
                true
            }
        }
        else {
            aimItem.setVisible(false)
        }
    }

    companion object{
        const val BUNDLE_KEY = "ExhibitMessage"
        const val AIM_KEY = "AimMessage"
        const val BUNDLE_ID = ""
    }
}