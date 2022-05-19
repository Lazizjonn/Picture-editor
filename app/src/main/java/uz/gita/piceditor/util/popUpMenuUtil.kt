package uz.gita.piceditor.util

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.MenuRes

/*
   Author: Zukhriddin Kamolov
   Created: 19.05.2022 at 11:55
   Project: ChatApp
*/

fun popUp(context: Context, @MenuRes menuId: Int, view: View?): PopupMenu {
    val popupMenu = PopupMenu(context, view)
    popupMenu.inflate(menuId)
    try {
        val popUp = PopupMenu::class.java.getDeclaredField("mPopup")
        popUp.isAccessible = true

        val menu = popUp.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(menu, true)
    } catch (e: Throwable){
        e.printStackTrace()
    } finally {
        popupMenu.show()
    }
    return popupMenu
}