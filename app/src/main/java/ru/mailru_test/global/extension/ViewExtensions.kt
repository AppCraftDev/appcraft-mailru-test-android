package ru.mailru_test.global.extension

import android.animation.Animator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.*
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import ru.mailru_test.domain.global.grill
import ru.mailru_test.global.utils.RxViewAfterTextChange

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.invisibleIf(condition: Boolean) {
    visibility = if (condition) View.INVISIBLE else View.VISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun CheckBox.disableCheck() {
    setOnCheckedChangeListener { _, checked -> isChecked = !checked }
}

fun BottomSheetBehavior<*>.hide() {
    state = BottomSheetBehavior.STATE_HIDDEN
}

fun BottomSheetBehavior<*>.expand() {
    state = BottomSheetBehavior.STATE_EXPANDED
}

fun BottomSheetBehavior<*>.collapse() {
    state = BottomSheetBehavior.STATE_COLLAPSED
}

fun TextView.makeLink() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun EditText.setTextDiff(newText: String) {
    if (this.text.toString() != newText)
        setText(newText)
}

fun View.setBackgroundColorRes(@ColorRes color: Int) {
    setBackgroundColor(getColor(context, color))
}

fun Spannable.stripUnderlines() {
    val spans = getSpans(0, length, URLSpan::class.java)
    spans.forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        removeSpan(span)
        setSpan(span, start, end, 0)
    }
}


fun Editable?.filledText(): String? = this?.toString()

fun EditText.filledText() = text.toString()

fun EditText.filledAndTrimText() = filledText().trim()

fun EditText.keyboardAction(keyId: Int, action: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == keyId) {
            action.invoke()
            return@setOnEditorActionListener true
        } else {
            return@setOnEditorActionListener false
        }
    }
}

fun TextView.rxAfterTextChanged(): RxViewAfterTextChange {
    return RxViewAfterTextChange(this)
}

fun getColorState(@ColorInt color: Int): ColorStateList {
    val states = arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_pressed))
    val colors = intArrayOf(color, color, color, color)
    return ColorStateList(states, colors)
}

fun parseShortColor(color: String) = Color.parseColor(grill().plus(color))


inline fun <reified V : View> doOnNextLayouts(vararg views: V, crossinline toDo: (views: Array<out V>) -> Unit) {
    var createdViewsCount = 0
    views.forEach { view ->
        view.doOnNextLayout {
            createdViewsCount++
            if (createdViewsCount == views.size) toDo(views)
        }
    }
}

fun <T : RecyclerView.ViewHolder> T.setHeightAndWidthFloat(
    rows: Int = -1,
    columns: Double = -1.0,
    @DimenRes itemDecorationHeight: Int = -1,
    @DimenRes itemDecorationWidth: Int = -1,
    @DimenRes maxHeight: Int? = null,
    @DimenRes maxWidth: Int? = null,
    square: Boolean = false
) {
    val decorationHeight = if (itemDecorationHeight != -1) {
        itemView.context.resources.getDimensionPixelSize(itemDecorationHeight)
    } else {
        0
    }
    val decorationWidth = if (itemDecorationWidth != -1) {
        itemView.context.resources.getDimensionPixelSize(itemDecorationWidth)
    } else {
        0
    }
    val maxHeightInPx = maxHeight?.let { itemView.context.resources.getDimensionPixelSize(it) } ?: Integer.MAX_VALUE
    val maxWidthInPx = maxWidth?.let { itemView.context.resources.getDimensionPixelSize(it) } ?: Integer.MAX_VALUE

    val itemWidth = ((itemView.parent as RecyclerView).width / columns - (decorationWidth * 2)).toInt()
    val itemHeight = if (square) itemWidth else (itemView.parent as RecyclerView).height / rows - (decorationHeight * 2)
    itemView.layoutParams = itemView.layoutParams.apply {
        if (rows != -1)
            this.height = if (itemHeight > maxHeightInPx) maxHeightInPx else itemHeight
        if (columns >= 1)
            this.width = if (itemWidth > maxWidthInPx) maxWidthInPx else itemWidth
    }
}

inline fun <reified L : Any> applyAll(vararg lists: L, toDo: L.() -> Unit) = lists.forEach { it.toDo() }

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawables.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}

fun TextView.setDrawable(left: Drawable? = null, top: Drawable? = null, right: Drawable? = null, bottom: Drawable? = null) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom)
}

fun View.rotateArrow(isExpanded: Boolean) {
    rotationX = if (isExpanded) 180.0f else 0f
}

fun TextView.coloredText(textToColor: String, @ColorRes color: Int) {
    val wordToSpan = SpannableString(text)
    wordToSpan.colorText(context, textToColor, color)
    text = wordToSpan
}

fun Spannable.colorText(context: Context, textToColor: String, @ColorRes color: Int) {
    val index = this.indexOf(textToColor, ignoreCase = true)
    if (index == -1 || textToColor.isEmpty()) return
    setSpan(
        ForegroundColorSpan(getColor(context, color)),
        index, index + textToColor.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

inline fun TabLayout.addTabListener(
    crossinline onTabSelected: (
        tab: TabLayout.Tab
    ) -> Unit = { _ -> }
): TabLayout.OnTabSelectedListener {
    val tabListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let { onTabSelected(it) }
        }
    }
    addOnTabSelectedListener(tabListener)
    return tabListener
}

fun View.subscribeTopMarginInset(vararg childs: View) {
    setOnApplyWindowInsetsListener { v, insets ->
        v.onApplyWindowInsets(insets)
        childs.forEach { child ->
            child.updatePadding(top = insets.systemWindowInsetTop)
        }
        insets
    }
}

fun View.updateMargin(
    @Px left: Int = marginLeft,
    @Px top: Int = marginTop,
    @Px right: Int = marginRight,
    @Px bottom: Int = marginBottom
) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        setMargins(left, top, right, bottom)
    }
}

inline fun LottieAnimationView.addListener(
    crossinline onAnimationEnd: () -> Unit = { }
) {
    addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }
    })
}

inline fun Animation.setListener(
    crossinline onAnimationRepeat: () -> Unit = { },
    crossinline onAnimationStart: () -> Unit = { },
    crossinline onAnimationEnd: () -> Unit = { }
) {
    setAnimationListener(object : Animation.AnimationListener {

        override fun onAnimationRepeat(animation: Animation?) {
            onAnimationRepeat()
        }

        override fun onAnimationEnd(animation: Animation?) {
            onAnimationEnd()
        }

        override fun onAnimationStart(animation: Animation?) {
            onAnimationStart()
        }
    })
}

fun AppCompatImageView.setTint(@ColorInt color: Int) {
    imageTintList = ColorStateList.valueOf(color)
}

fun View.setBgTint(@ColorInt color: Int) {
    backgroundTintList = ColorStateList.valueOf(color)
}

fun TextView.setIconTint(@ColorInt color: Int) {
    compoundDrawableTintList = ColorStateList.valueOf(color)
}

fun Context.clipBoardText(text: String?): Boolean {
    val clipboard: ClipboardManager? = ContextCompat.getSystemService(this, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard?.setPrimaryClip(clip)
    return clipboard != null
}

inline fun BottomSheetBehavior<*>.addBottomSheetCallback(
    crossinline onSlide: (bottomSheet: View, slideOffset: Float) -> Unit = { _, _ -> },
    crossinline onStateChanged: (bottomSheet: View, newState: Int) -> Unit = { _, _ -> }
) {
    addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            onSlide(bottomSheet, slideOffset)
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            onStateChanged(bottomSheet, newState)
        }
    })
}