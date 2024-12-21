package sk.zuray.storageviewer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "product"
)
data class Product(
    @PrimaryKey
    val name: String,
)
