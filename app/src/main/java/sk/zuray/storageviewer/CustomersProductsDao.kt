package sk.zuray.storageviewer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CustomersProductsDao {
    @Upsert
    suspend fun upsertCustomersProducts()

    @Delete
    suspend fun deleteCustomersProducts()

    @Query("""
        select *
        from customers_products
        join customer on customer.name=customerName
        where productName = :productName and served=false
        """)
    suspend fun getNotServedProducts(productName: String): List<CustomersProducts>

    @Query("""
        select *
        from customers_products
        left join customer on customer.name=customerName
        where customerName = :customerName and served=false
        """)
    suspend fun getNotServedCustomers(customerName: String): List<CustomersProducts>
}