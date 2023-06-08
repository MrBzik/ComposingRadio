package com.example.composingradio.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RadioStation (
    @PrimaryKey(autoGenerate = false)
    val stationuuid: String,

    val favicon: String?,
    val name: String?,
    val country: String?,
    val url: String?,
    val homepage : String?,
    val tags : String?,
    val language : String?,
    @ColumnInfo(name = "favouredAt", defaultValue = "0")
    val favouredAt : Long,
    @ColumnInfo(name = "state", defaultValue = "")
    val state : String?,
    @ColumnInfo(name = "bitrate", defaultValue = "0")
    val bitrate : Int?
        )

