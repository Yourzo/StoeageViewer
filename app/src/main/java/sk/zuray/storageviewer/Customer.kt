package sk.zuray.storageviewer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "customer",
)
data class Customer(
    val served: Boolean,
    @PrimaryKey(autoGenerate = true)
    val name: String,
)
