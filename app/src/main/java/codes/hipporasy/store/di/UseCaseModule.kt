package codes.hipporasy.store.di

import android.annotation.SuppressLint
import codes.hipporasy.store.data.repository.PostRepository
import codes.hipporasy.store.data.service.DummyApi
import codes.hipporasy.store.domain.repository.PostRepositoryImpl
import codes.hipporasy.store.domain.useCase.FetchPost
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideFetchPost(repository: PostRepository): FetchPost {
        return FetchPost(repository)
    }

}