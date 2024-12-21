package sk.zuray.storageviewer

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "customers_products",
    primaryKeys = ["productName", "customerName"],
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["name"],
            childColumns = ["productName"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Customer::class,
            parentColumns = ["name"],
            childColumns = ["customerName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CustomersProducts(
    val productName: String,
    val customerName: String,
    val count: Int
)
