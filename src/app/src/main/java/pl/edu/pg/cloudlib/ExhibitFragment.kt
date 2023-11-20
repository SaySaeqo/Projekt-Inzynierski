package pl.edu.pg.cloudlib

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Gallery
import android.widget.ImageView
import androidx.fragment.app.Fragment
import pl.edu.pg.cloudlib.databinding.FragmentExhibitBinding


class ExhibitFragment : Fragment() {

    private lateinit var binding: FragmentExhibitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExhibitBinding.inflate(inflater, container, false)

        activity?.title = getString(R.string.exhibit_page_title)

        binding.textView.text = arguments?.getString(BUNDLE_KEY) ?: "Error: no message arrived"


        val images = intArrayOf(
            R.drawable.ic_action_filter,
            R.drawable.ic_action_close
        )

        binding.gallery.adapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return images.size
            }

            override fun getItem(p0: Int): Any {
                return p0
            }

            override fun getItemId(p0: Int): Long {
                return p0.toLong()
            }

            override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
                val imageView = ImageView(p2?.context)
                imageView.setImageResource(images[p0])
                imageView.layoutParams = Gallery.LayoutParams(200, 200)
                return imageView
            }
        }

        binding.gallery.setOnItemClickListener { _, _, position, _ ->
            binding.imageView.setImageResource(images[position])
        }

        binding.cardViewButton.setOnClickListener{
            if (binding.hiddenTextView.getVisibility() == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.hiddenTextView.setVisibility(View.GONE)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.hiddenTextView.setVisibility(View.VISIBLE)
            }
        }

        return binding.root
    }

    companion object{
        const val BUNDLE_KEY = "ExhibitMessage"
    }
}