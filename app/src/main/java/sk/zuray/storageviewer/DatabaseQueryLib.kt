package sk.zuray.storageviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DatabaseQueryLib(
    private val customersProductsDao: CustomersProductsDao,
    private val customersDao: CustomerDao,
    private val productDao: ProductDao
) :ViewModel() {
    fun getCustomer(customerName: String): List<Customer> {
        val data: List<Customer> = runBlocking {
            customersDao.getCustomer(customerName)
        }
        return data
    }

    fun getCustomersNotServedProducts() : HashMap<String, Int> {
        val res = HashMap<String, Int>()
        val data: List<CustomersProducts> = runBlocking {
            customersProductsDao.getNotServedProducts()
        }
        for (record in data) {
            res[record.productName] = record.count
        }
        return res;
    }

    fun getCustomersProducts(customerName: String) :HashMap<String, Int> {
        val res = HashMap<String, Int>()
        val data: List<CustomersProducts> = runBlocking {
            customersProductsDao.getCustomersProducts(customerName)
        }
        for (record in data) {
            res[record.productName] = record.count
        }
        return res;
    }

    fun getNotServedProducts(productName: String) : HashMap<String, Int>? {
        val res = HashMap<String, Int>()
        val data: List<CustomersProducts> = runBlocking {
            customersProductsDao.getNotServedProduct(productName)
        }

        for (record in data) {
            res[record.customerName] = record.count;
        }
        return res
    }

    fun setServed(customerName: String) {
        viewModelScope.launch {
            customersDao.upsertCustomer(Customer(true, customerName))
        }
    }

    fun setNotServed(customerName: String) {
        viewModelScope.launch {
            customersDao.upsertCustomer(Customer(false, customerName))
        }
    }

    fun fillDatabase(data: List<CustomersProducts>) {
        for (record in data) {
            val customer = Customer(name = record.customerName)
            viewModelScope.launch {
                customersDao.upsertCustomer(customer)
            }
            val product = Product(record.productName)
            viewModelScope.launch {
                productDao.upsertProduct(product)
            }
            viewModelScope.launch {
                customersProductsDao.upsertCustomersProducts(record)
            }
        }
    }

    fun emptyTables() {
        viewModelScope.launch {
            customersDao.nukeTable()
        }
        viewModelScope.launch {
            customersProductsDao.nukeTable()
        }
        runBlocking {
            productDao.nukeTable()
        }
    }
}