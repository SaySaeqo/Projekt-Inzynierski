package pl.edu.pg.cloudlib.exhibit

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import pl.edu.pg.cloudlib.Bundles
import pl.edu.pg.cloudlib.databinding.ViewWidgetTextBinding

class TextWidgetView: FrameLayout {

    private lateinit var binding: ViewWidgetTextBinding


    var text: String = ""
        set(value) {
            field = value
            binding.textView.text = value
        }

    var title: String = ""

    var button: Button
        get() = binding.button
        private set(value) {}

    val bundle get() = bundleOf(Bundles.SECTION_TEXT to text, Bundles.SECTION_TITLE to title)

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

        binding = ViewWidgetTextBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

    }
}