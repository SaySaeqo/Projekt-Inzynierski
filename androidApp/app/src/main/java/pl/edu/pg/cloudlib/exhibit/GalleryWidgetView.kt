package pl.edu.pg.cloudlib.exhibit

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.setMargins
import pl.edu.pg.cloudlib.database.DBSingleton
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.ViewWidgetGalleryBinding

/**
 * TODO: document your custom view class.
 */
class GalleryWidgetView : FrameLayout {

    private lateinit var binding: ViewWidgetGalleryBinding

    var images: Array<String> = arrayOf()
        set(value) {
            field = value

            val constraintSet = ConstraintSet().apply { clone(binding.scrollable) }
            var lastImageId = ConstraintSet.PARENT_ID

            value.forEach {
                val imageView = createGalleryItemView(it)
                binding.scrollable.addView(imageView)

                constraintSet.apply {
                    setDimensionRatio(imageView.id, "1:1")
                    connect(
                        imageView.id, ConstraintSet.START, lastImageId,
                        if (lastImageId == ConstraintSet.PARENT_ID) ConstraintSet.START else ConstraintSet.END
                    )
                    connect(
                        imageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP
                    )
                    connect(
                        imageView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
                    )
                }

                lastImageId = imageView.id
            }
            constraintSet.apply {
                connect(
                    lastImageId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END
                )
                applyTo(binding.scrollable)
            }

            DBSingleton.loadImageInto(value[0], binding.image)
        }

    private fun createGalleryItemView(image: String): ImageView {
        return ImageView(context).apply {
            id = View.generateViewId()
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = ConstraintLayout.LayoutParams(
                0,
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
            )
            DBSingleton.loadImageInto(image, this)
            setOnClickListener{ _ -> DBSingleton.loadImageInto(image, binding.image) }
            val padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4f,
                resources.displayMetrics).toInt()
            setPadding(padding, padding, padding, padding)
//            val margin = TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                2f,
//                resources.displayMetrics).toInt()
//            (layoutParams as ConstraintLayout.LayoutParams).setMargins(0, margin, 0, margin)
            background = AppCompatResources.getDrawable(context, R.drawable.widget_element_background)
        }
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

        binding = ViewWidgetGalleryBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }

    companion object {
        private const val TAG = "GalleryWidgetView"
    }

}