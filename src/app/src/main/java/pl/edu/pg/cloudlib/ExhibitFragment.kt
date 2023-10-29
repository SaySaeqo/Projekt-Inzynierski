package pl.edu.pg.cloudlib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.textView.text = arguments?.getString("QRScannerResult") ?: "Error: no message arrived"
        return binding.root
    }
}