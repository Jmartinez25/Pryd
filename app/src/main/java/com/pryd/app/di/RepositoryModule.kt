package com.pryd.app.di

import com.pryd.app.data.repository.ActivityRepositoryImpl
import com.pryd.app.data.repository.PomodoroSessionRepositoryImpl
import com.pryd.app.domain.repository.ActivityRepository
import com.pryd.app.domain.repository.PomodoroSessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindActivityRepository(impl: ActivityRepositoryImpl): ActivityRepository

    @Binds
    @Singleton
    abstract fun bindPomodoroSessionRepository(impl: PomodoroSessionRepositoryImpl): PomodoroSessionRepository
}