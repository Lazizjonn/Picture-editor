package uz.gita.piceditor.data.model.model.common

import uz.gita.piceditor.data.model.model.request.MyUserRequest
import uz.gita.piceditor.data.model.model.response.MyUserResponse
import java.io.Serializable
import javax.inject.Singleton


@Singleton
data class MyUserData(
    var id: String,
    var name: String,
    var surname: String,
    var phone: String,
    var email: String? = null,
    val geoPoint: String? = null,
    val code: String,
    val timestamp: String
): Serializable {
    fun toMyUserRequest(): MyUserRequest = MyUserRequest(id, name, surname, phone, email, geoPoint, code, timestamp)

    fun toMyUserResponse(): MyUserResponse = MyUserResponse(id, name, surname, phone, email, geoPoint, code, timestamp)

}

