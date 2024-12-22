package sk.zuray.storageviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ShowStorageProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_storage_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tableLayout = findViewById<TableLayout>(R.id.tableData)
        val customerName = findViewById<TextView>(R.id.customerName)
        customerName.text = intent.getStringExtra("name")
        val products = intent.getSerializableExtra("customers") as? HashMap<String, Int>
        for (record in products!!.keys) {
            addProductRow(tableLayout, record, products[record]!!)
        }
    }

    private fun addProductRow(tableLayout: TableLayout, name: String, count: Int) {
        val newRow = LayoutInflater.from(this).inflate(R.layout.product_row, null) as TableRow

        newRow.findViewById<TextView>(R.id.productName).text = name
        newRow.findViewById<TextView>(R.id.productCount).text = count.toString()

        tableLayout.addView(newRow)
    }
}