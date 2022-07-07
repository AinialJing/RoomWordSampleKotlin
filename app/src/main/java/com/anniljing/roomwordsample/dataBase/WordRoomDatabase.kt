package com.anniljing.roomwordsample.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.anniljing.roomwordsample.dao.WordDao
import com.anniljing.roomwordsample.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { wordRoomDatabase ->
                scope.launch {
                    var wordDao = wordRoomDatabase.wordDao()
                    wordDao.deleteAll()
                    var word = Word("Hello")
                    wordDao.insert(word)
                    word = Word("Word")
                    wordDao.insert(word)

                    word = Word("TODO!")
                    wordDao.insert(word)
                }
            }
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDataBase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}