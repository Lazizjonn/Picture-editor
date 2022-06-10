package uz.gita.piceditor.data.model.model.common

data class ImageData(
    val id: Int,
    val imgRes: Int,
    var defHeight: Int,
    var defaultWidth: Int,
    val name: String,
    var isSelected: Boolean = false
)
