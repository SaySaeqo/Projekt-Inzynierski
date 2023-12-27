package pl.edu.pg.cloudlib.exhibit

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.ViewWidgetExtraBinding

class ExtraWidgetView : FrameLayout {
    private lateinit var binding: ViewWidgetExtraBinding


    private var extra: Map<String, String> = mapOf()

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

        binding = ViewWidgetExtraBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

        val baseHeight = binding.scrollView.layoutParams.height

        binding.btnToggle.setOnClickListener {
            val params = binding.scrollView.layoutParams
            if (params.height == baseHeight) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.btnToggle.text = context.getString(R.string.hide)
            } else {
                params.height = baseHeight
                binding.btnToggle.text = context.getString(R.string.show)
            }
            binding.scrollView.layoutParams = params
        }

        // shown when there is no extra data
        binding.header.text = context.getString(R.string.no_extra_information)
        binding.btnToggle.visibility = GONE

    }

    /**
     * Sets extra data to be displayed in the widget
     * @return the button created for value for purpose of setting onClickListener
     */
    fun setExtra(key: String, value: String): Button {
        val button: Button
        if (extra.containsKey(key)) {
            val index = extra.keys.indexOf(key)
            val row = binding.table.getChildAt(index) as TableRow
            button = row.getChildAt(1) as Button
            button.text = value
        } else {
            button = appendExtra(key, value)
        }
        extra += Pair(key, value)

        if (extra.isNotEmpty()) {
            binding.header.text = context.getString(R.string.extra_information)
            binding.btnToggle.visibility = VISIBLE
        }

        return button
    }

    private fun appendExtra(key: String, value: String): Button {
        extra += Pair(key, value)
        val button = Button(context)
        button.isEnabled = false

        // add new row to table
        val row = TableRow(context).apply {
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            weightSum = 2f
            dividerDrawable = ContextCompat.getDrawable(context, R.drawable.space_horizontal)
            showDividers = TableRow.SHOW_DIVIDER_MIDDLE
            addView(applyStyling(key, TextView(context)))
            addView(applyStyling(value, button))
        }
        binding.table.addView(row)
        return button
    }

    private fun applyStyling(withText: String, textView: TextView): TextView {
        return textView.apply {
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
            )
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            setTextIsSelectable(true)
            // set padding to 5dp
//            val padding = TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                3f,
//                resources.displayMetrics
//            ).toInt()
            //setPadding(padding, padding, padding, padding)
            //set background color to 1 of predefined colors
            textAlignment = TEXT_ALIGNMENT_CENTER
            setTextColor(ContextCompat.getColor(context, R.color.black))
            text = withText
        }
    }
}