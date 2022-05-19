package uz.gita.piceditor.util

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*


/**
 * Extension function to convert long to date string
 */

val datFormat = SimpleDateFormat("dd,MMM yyyy", Locale.US)  // day, Month(name) year

fun Long.toData(): String {
    return datFormat.format(Date(this))
}

/**
 * Extension function to hide keyboard
 */
fun EditText.hideKeyboard() {
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

/*
fun ChildTask.toStringBuilder(): String {
    val st = StringBuilder()
    st.append("#<$id<${title}<${isDone}")
    return st.toString()
}

fun List<ChildTask>.toStringBuilder(): String {
    val st = StringBuilder()
    this.onEach {
        st.append("#<${it.id}<${it.title}<${it.isDone}")
    }
    return st.toString()
}

fun String.toChildTaskList(): List<ChildTask> {
    val a: List<String> = this.split("#")
    val b: List<List<String>> = a.map { it.split("<") }
    val list = ArrayList<ChildTask>()
    b.map {
        if (it.isNotEmpty()) {
            Log.d("TAG", "toChildTaskList, b:" + it.toString())
            */
/*list.add(
                ChildTask(
                    it[0].toInt(),
                    it[1],
                    it[2].toBoolean()))*//*

        }
    }
    return list
}*/
