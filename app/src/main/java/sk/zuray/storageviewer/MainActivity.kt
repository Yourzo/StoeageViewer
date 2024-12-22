package sk.zuray.storageviewer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import java.io.BufferedReader
import java.io.File

class MainActivity : AppCompatActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "storage.db"
        ).build()
    }
    val dbQueryLibViewModel by viewModels<DatabaseQueryLib>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DatabaseQueryLib(db.daoCustomersProductDao, db.daoCustomer, db.daoProduct) as T
                }
            }
        }
    )
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data!!.getBooleanExtra("isChecked", true)) {
                dbQueryLibViewModel.setServed(data.getStringExtra("name").toString())
            } else {
                dbQueryLibViewModel.setNotServed(data.getStringExtra("name").toString())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun searchForCustomer(view: View) {
        val customerName: String = findViewById<EditText>(R.id.customer_name_field).text.toString()
        findViewById<EditText>(R.id.customer_name_field).text.clear();
        if (customerName.isBlank()) {
            return
        }
        val customersProducts = dbQueryLibViewModel.getCustomersProducts(customerName)
        val customer = dbQueryLibViewModel.getCustomer(customerName)
        val intent = Intent(this, ShowStorageData::class.java)
        intent.putExtra("products",customersProducts)
        intent.putExtra("name", customerName)
        intent.putExtra("isChecked", customer[0].served)
        getResult.launch(intent)
    }

    fun searchForProduct(view: View) {
        val productName: String = findViewById<EditText>(R.id.product_name_field).text.toString()
        if (productName.isBlank()) {
            return
        }
        val products = dbQueryLibViewModel.getNotServedProducts(productName)
    }

    fun showAllOrders(view: View) {
        val allOrders = dbQueryLibViewModel.getCustomersNotServedProducts()
    }

    fun loadFakabase(view: View) {
        val data= listOf(
            CustomersProducts("Kebab", "Jozo",5),
            CustomersProducts("Zavin", "Fero", 8),
            CustomersProducts("VB1000", "Kupka", 4),
            CustomersProducts("Rozok", "Jozo", 6)
        )
        dbQueryLibViewModel.fillDatabase(data)
    }

    fun loadDatabaseFromCSV(view: View) {
        val delimiter = ';'
        val reader: BufferedReader = File("example.txt").bufferedReader()
        val header = reader.readLine()
        val records = reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (customerName, productName, count) = it.split(delimiter, ignoreCase = false, limit = 3)
                CustomersProducts(
                    productName.trim().removeSurrounding("\""),
                    customerName.trim().removeSurrounding("\""),
                    count = count.trim().toInt())
            }.toList()
        dbQueryLibViewModel.fillDatabase(records)
    }
}