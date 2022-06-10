package uz.gita.piceditor.domain.repository

import uz.gita.piceditor.data.model.model.common.ImageData


interface AppRepository {
    val imageList : ArrayList<ImageData>

    fun loadData()
}