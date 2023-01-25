package codes.hipporasy.store.di

import codes.hipporasy.store.data.repository.PostRepository
import codes.hipporasy.store.data.service.DummyApi
import codes.hipporasy.store.domain.repository.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(
        api: DummyApi
    ): PostRepository = PostRepositoryImpl(api)

}