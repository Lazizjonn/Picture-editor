package uz.gita.piceditor.data.model.model.common

import androidx.annotation.DrawableRes

data class ImageData(
    val id: Int,
    @DrawableRes val imageRes: Int,
    val defHeight: Int,
    val defWidth: Int,
    var isSelect: Boolean = false,
    val imageName: String
)