package com.anniljing.roomwordsample

import android.app.Application
import com.anniljing.roomwordsample.dataBase.WordRoomDatabase
import com.anniljing.roomwordsample.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val dataBase by lazy {
        WordRoomDatabase.getDataBase(this, applicationScope)
    }

    val repository by lazy {
        WordRepository(dataBase.wordDao())
    }
}