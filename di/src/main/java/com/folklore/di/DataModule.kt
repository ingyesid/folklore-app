package com.folklore.di

import com.folklore.data.datasource.LocalPreferencesDataSource
import com.folklore.data.repository.UserPreferencesRepositoryImpl
import com.folklore.domain.datasource.UserPreferencesDataSource
import com.folklore.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindUserPreferencesDataSource(
        localDatasource: LocalPreferencesDataSource,
    ): UserPreferencesDataSource

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        repository: UserPreferencesRepositoryImpl,
    ): UserPreferencesRepository
}
