package com.example.apifetch.databse

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update



@Dao
abstract class PostDao {

    @Query("DELETE FROM tbl_post")
    abstract fun deleteAllRow()

    @Insert(onConflict = REPLACE)
    abstract fun save(item: Rows): Long

    @Insert(onConflict = REPLACE)
    abstract fun save(item: List<Rows>)

    @Update
    abstract fun update(item: Rows?): Int

    @Query("SELECT * FROM tbl_post")
    abstract fun selectAll(): Cursor

    @Query("select * from tbl_post")
    abstract fun getNumberOfRows(): Int

    @Query("select * from tbl_post")
    abstract fun getAllData(): MutableList<Rows>

    @Query("SELECT * FROM tbl_post LIMIT :limit OFFSET :offset")
    abstract fun getAllData(limit: Int, offset: Int): MutableList<Rows>

}