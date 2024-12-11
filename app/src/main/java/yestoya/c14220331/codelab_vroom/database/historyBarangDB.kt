package yestoya.c14220331.codelab_vroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [historyBarang::class], version = 1)
abstract  class historyBarangDB : RoomDatabase() {
    abstract fun  funHistoryBarangDAO() : historyBarangDAO

    companion object{
        @Volatile
        private var INSTANCE: historyBarangDB? = null

        @JvmStatic
        fun getDatabase(context: Context): historyBarangDB {
            if(INSTANCE == null){
                synchronized(historyBarangDB::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        historyBarangDB::class.java, "historyBarang_db"
                    ).allowMainThreadQueries().build()
                }
            }

            return INSTANCE as historyBarangDB
        }
    }

}