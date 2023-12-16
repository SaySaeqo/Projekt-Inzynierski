package pl.edu.pg.cloudlib.exhibit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.startActivity
import pl.edu.pg.cloudlib.databinding.ViewWidgetLinkBinding


class LinkWidgetView: FrameLayout {

    private lateinit var binding: ViewWidgetLinkBinding

    var link: Uri = Uri.EMPTY
        set(value) {
            field = value
            binding.root.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, value)
                startActivity(context, browserIntent, null)
            }
        }

    var title: String = ""
        set(value) {
            field = value
            binding.textView.text = value
        }

    var iconId: Int = 0
        set(value) {
            field = value
            binding.icon.setImageResource(value)
        }

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

        binding = ViewWidgetLinkBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

    }
}