package uz.gita.piceditor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.piceditor.domain.repository.AppRepository
import uz.gita.piceditor.domain.repository.impl.AppRepositoryImpl
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface AppModule {

    @[Binds Singleton]
    fun getAppModule(impl: AppRepositoryImpl): AppRepository
}