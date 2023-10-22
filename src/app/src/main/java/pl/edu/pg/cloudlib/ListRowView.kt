package pl.edu.pg.cloudlib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import pl.edu.pg.cloudlib.databinding.ActivityListBinding
import pl.edu.pg.cloudlib.databinding.ActivityQrscannerBinding
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

        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ListRowView, defStyle, 0
        )

        a.recycle()
    }

}