package com.mentalmachines.compose.di

import android.content.Context
import androidx.room.Room
import com.mentalmachines.travel_app.database.AppDatabase
import com.mentalmachines.travel_app.database.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Users"
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): UsersDao {
        return appDatabase.usersDao
    }

}
