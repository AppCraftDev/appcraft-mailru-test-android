package ru.mailru_test.global.extension

import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.mailru_test.R
import ru.mailru_test.global.glide.GlideApp
import java.io.File

fun ImageView.loadImage(@DrawableRes res: Int) {
    GlideApp.with(this)
        .load(res)
        .into(this)
}

fun ImageView.loadImageCorner(url: String?, @DimenRes corner: Int = R.dimen.corner_default, @DrawableRes holder:Int? = null, @DrawableRes error:Int? = null) {
    GlideApp.with(this)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(context.resources.getDimensionPixelOffset(corner)))
        .apply {
            if(holder != null) placeholder(holder)
            if(error != null) error(error)
        }
        .into(this)
}