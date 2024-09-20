package com.example.carer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend  fun insert (contactModel: ContactModel)

}