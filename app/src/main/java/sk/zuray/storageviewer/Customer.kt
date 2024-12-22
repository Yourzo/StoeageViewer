package sk.zuray.storageviewer

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "customer",
    primaryKeys = ["name"]
)
data class Customer(
    val served: Boolean = false,
    val name: String,
):Serializable
