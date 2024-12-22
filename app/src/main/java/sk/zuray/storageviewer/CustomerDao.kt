package sk.zuray.storageviewer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Upsert
    suspend fun upsertCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("SELECT * FROM customer WHERE customer.name=:customerName")
    suspend fun getCustomer(customerName: String): List<Customer>

    @Query("SELECT * FROM customer where customer.served = false")
    suspend fun getNotServedCustomers(): List<Customer>

    @Query("DELETE FROM customer")
    suspend fun nukeTable()
}