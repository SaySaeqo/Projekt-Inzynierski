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
import pl.edu.pg.cloudlib.Bundles
import pl.edu.pg.cloudlib.database.DBSingleton
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.FragmentExhibitBinding
import pl.edu.pg.cloudlib.database.Exhibit

class ExhibitFragment : Fragment() {

    private lateinit var binding: FragmentExhibitBinding
    lateinit var exhibit: Exhibit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExhibitBinding.inflate(inflater, container, false)

        activity?.title = getString(R.string.exhibit_page_title)
        val id = arguments?.getString(Bundles.EXHIBIT_ID) ?: "Error: no id arrived"
        binding.exampleText.text = id

        if(id != ""){
            DBSingleton.forOne(id) { exhibit ->
                this.exhibit = exhibit
                setHasOptionsMenu(true)
                activity?.title = exhibit.name

                for(w in exhibit.widgets){
                    when (w.type) {
                        "gallery" -> GalleryWidgetView(requireContext()).apply{
                            images = w.imagesURLs;
                        }
                        "text" -> TextWidgetView(requireContext()).apply{
                            text = w.data
                            title = w.title
                            button.setOnClickListener{
                                setFragmentResult(Bundles.SECTION_TEXT, this.bundle)
                            }
                        }
                        "link" -> LinkWidgetView(requireContext()).apply{
                            title = w.title
                            link = Uri.parse(w.data)
                        }
                        else -> { null }
                    }?.let { binding.widgetsContainer.addView(it) }

                }

                for(e in exhibit.extra){
                    binding.exampleExtra.setExtra(e.key, e.value).setOnClickListener { _ ->
                        if(e.linkId != "") {
                            setFragmentResult(Bundles.EXHIBIT_ID, bundleOf(Bundles.EXHIBIT_ID to e.linkId))
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if(exhibit.location == "") return

        menu.findItem(R.id.aim).apply {
            isVisible = true
            setOnMenuItemClickListener {
                setFragmentResult(Bundles.LOCATION_URL, bundleOf(Bundles.LOCATION_URL to exhibit.location))
                true
            }
        }
    }
}