package sk.zuray.storageviewer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CustomersProductsDao {
    @Upsert
    suspend fun upsertCustomersProducts(customersProducts: CustomersProducts)

    @Delete
    suspend fun deleteCustomersProducts(customersProducts: CustomersProducts)

    @Query("select * from customers_products join customer on customer.name=customers_products.customerName where productName = :productName and served=false")
    suspend fun getNotServedProduct(productName: String): List<CustomersProducts>

    @Query("select * from customers_products join customer on customer.name=customers_products.customerName where served=false")
    suspend fun getNotServedProducts(): List<CustomersProducts>

    @Query("select * from customers_products left join customer on customer.name=customers_products.customerName where customerName = :customerName")
    suspend fun getCustomersProducts(customerName: String): List<CustomersProducts>

    @Query("DELETE FROM customers_products")
    suspend fun nukeTable()
}