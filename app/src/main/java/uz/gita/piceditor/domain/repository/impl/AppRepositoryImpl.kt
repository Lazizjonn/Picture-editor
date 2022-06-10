package uz.gita.piceditor.domain.repository.impl

import uz.gita.piceditor.R
import uz.gita.piceditor.data.model.model.common.ImageData
import uz.gita.piceditor.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor() : AppRepository {
    override val imageList = ArrayList<ImageData>()

    override fun loadData() {
        imageList.add(ImageData(1, R.drawable.glasses, 64, 128, "Glasses"))
        imageList.add(ImageData(2, R.drawable.logo, 64, 64, "Book"))
        imageList.add(ImageData(3, R.drawable.share_icon, 64, 64, "share"))
        imageList.add(ImageData(4, R.drawable.emoji_icon, 64, 64, "Emoji"))
    }
}