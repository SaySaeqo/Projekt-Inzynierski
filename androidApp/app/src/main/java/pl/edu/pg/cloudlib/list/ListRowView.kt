package pl.edu.pg.cloudlib.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.os.bundleOf
import pl.edu.pg.cloudlib.Bundles
import pl.edu.pg.cloudlib.databinding.ViewListRowBinding
import pl.edu.pg.cloudlib.exhibit.ExhibitFragment

/**
 * TODO: document your custom view class.
 */
class ListRowView : FrameLayout {

    private lateinit var binding: ViewListRowBinding
    var id = ""
    var title
        get() = binding.title.text
        set(value) = binding.title.setText(value)

    var subtitle
        get() = binding.subtitle.text
        set(value) = binding.subtitle.setText(value)

    var imageView: ImageView
        get() = binding.image
        private set(value) {}

    val bundle get() = bundleOf(Bundles.EXHIBIT_ID to id)

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
        binding = ViewListRowBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }
}