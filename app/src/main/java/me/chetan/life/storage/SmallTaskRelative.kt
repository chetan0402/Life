package me.chetan.life.storage

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import me.chetan.life.TimeUnit
import java.time.LocalTime

@Entity
data class SmallTaskRelative(
    @PrimaryKey(autoGenerate=true) val id: Int = 0,
    val taskName: String,
    val interval: Int,
    val unit: TimeUnit,
    val time: LocalTime
)
