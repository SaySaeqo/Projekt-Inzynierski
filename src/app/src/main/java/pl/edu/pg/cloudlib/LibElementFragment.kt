package pl.edu.pg.cloudlib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.edu.pg.cloudlib.databinding.FragmentLibElementBinding

class LibElementFragment : Fragment() {

    private lateinit var binding: FragmentLibElementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLibElementBinding.inflate(layoutInflater)

        binding.textView.text = activity?.intent?.getStringExtra("message") ?: "Error: no message arrived"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lib_element, container, false)
    }
}