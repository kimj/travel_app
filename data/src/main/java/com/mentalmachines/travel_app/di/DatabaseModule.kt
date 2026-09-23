package com.mentalmachines.travel_app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mentalmachines.travel_app.database.AppDatabase
import com.mentalmachines.travel_app.database.dao.PackItemDao
import com.mentalmachines.travel_app.database.dao.TripDao
import com.mentalmachines.travel_app.database.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
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
        )
        .fallbackToDestructiveMigration(true)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                try {
                    val sqlScript = appContext.assets.open("database_seed.sql")
                        .bufferedReader()
                        .use { it.readText() }
                    
                    sqlScript.split(";")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                        .forEach { sqlStatement ->
                            db.execSQL(sqlStatement)
                        }
                } catch (e: Exception) {
                    Timber.e(e, "Error populating database from SQL asset script")
                }
            }
        })
        .build()
    }

    @Provides
    fun provideUsersDao(appDatabase: AppDatabase): UsersDao {
        return appDatabase.usersDao
    }

    @Provides
    fun provideTripDao(appDatabase: AppDatabase): TripDao {
        return appDatabase.tripDao
    }

    @Provides
    fun providePackItemDao(appDatabase: AppDatabase): PackItemDao {
        return appDatabase.packItemDao
    }

}
