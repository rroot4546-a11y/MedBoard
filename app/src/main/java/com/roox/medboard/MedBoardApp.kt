package com.roox.medboard

import android.app.Application
import com.roox.medboard.data.MedBoardDatabase

class MedBoardApp : Application() {
    val database: MedBoardDatabase by lazy { MedBoardDatabase.getDatabase(this) }
}
