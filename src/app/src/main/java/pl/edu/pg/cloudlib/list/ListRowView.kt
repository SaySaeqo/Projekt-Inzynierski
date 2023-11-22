package pl.edu.pg.cloudlib.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.ListRowViewBinding

/**
 * TODO: document your custom view class.
 */
class ListRowView : FrameLayout {

    private lateinit var binding: ListRowViewBinding

    var title
        get() = binding.title.text
        set(value) = binding.title.setText(value)

    var subtitle
        get() = binding.subtitle.text
        set(value) = binding.subtitle.setText(value)

    var imageView: ImageView
        get() = binding.image
        private set(value) {}

    var fragmentMessage: String? = null

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
        binding = ListRowViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }

}