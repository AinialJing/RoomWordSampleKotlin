package com.anniljing.roomwordsample.repository

import androidx.annotation.WorkerThread
import com.anniljing.roomwordsample.dao.WordDao
import com.anniljing.roomwordsample.entity.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    //查询所有的数据
    val allWorlds: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    //插入新数据
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}