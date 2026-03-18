package com.roox.medboard

import android.app.Application
import com.roox.medboard.data.database.AppDatabase

class MedBoardApp : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
    val dao by lazy { db.medDao() }
}
