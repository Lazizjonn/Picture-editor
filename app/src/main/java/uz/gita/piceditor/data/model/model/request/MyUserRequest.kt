package uz.gita.piceditor.data.model.model.request

import uz.gita.piceditor.data.model.model.common.MyUserData
import java.io.Serializable



data class MyUserRequest(
    var id: String,
    var name: String,
    var surname: String,
    var phone: String,
    var email: String? = null,
    val geoPoint: String? = null,
    val code: String,
    val timestamp: String
): Serializable {
    fun toMyUserData(): MyUserData = MyUserData(id, name, surname, phone, email, geoPoint, code, timestamp)
}