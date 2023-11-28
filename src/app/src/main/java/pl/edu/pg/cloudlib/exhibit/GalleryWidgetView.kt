package pl.edu.pg.cloudlib.exhibit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintSet
import pl.edu.pg.cloudlib.R
import pl.edu.pg.cloudlib.databinding.GalleryWidgetViewBinding

/**
 * TODO: document your custom view class.
 */
class GalleryWidgetView : FrameLayout {

    private lateinit var binding: GalleryWidgetViewBinding

    var images: IntArray = intArrayOf()
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


            binding.image.setImageResource(value[0])
        }

    private fun createGalleryItemView(resourceId : Int): ImageView {
        return ImageView(context).apply {
            id = View.generateViewId()
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT
            )
            setImageResource(resourceId)
            setOnClickListener{ _ ->
                binding.image.setImageResource(resourceId)
            }
            setBackgroundResource(R.drawable.inner_border)
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

        binding = GalleryWidgetViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

    }

}