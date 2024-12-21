package sk.zuray.storageviewer

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Customer::class, Product::class, CustomersProducts::class],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {

    abstract val daoCustomer: CustomerDao
    abstract val daoProduct: ProductDao
    abstract val daoCustomersProductDao: CustomersProductsDao
}