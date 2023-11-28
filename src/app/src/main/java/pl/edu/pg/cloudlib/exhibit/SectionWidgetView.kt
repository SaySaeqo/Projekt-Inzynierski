package pl.edu.pg.cloudlib.exhibit

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.GalleryWidgetViewBinding
import pl.edu.pg.cloudlib.databinding.SectionWidgetViewBinding

class SectionWidgetView: FrameLayout {

    private lateinit var binding: SectionWidgetViewBinding


    var text: String = ""
        set(value) {
            field = value
            binding.textView.text = value
        }

    var button: Button
        get() = binding.button
        private set(value) {}


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        binding = SectionWidgetViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

    }

    fun getBundle(): Bundle {
        return bundleOf(SectionFragment.BUNDLE_KEY to text)
    }
}