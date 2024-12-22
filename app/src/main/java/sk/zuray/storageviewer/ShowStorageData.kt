package sk.zuray.storageviewer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ShowStorageData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_storage_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tableLayout = findViewById<TableLayout>(R.id.tableData)
        val customerName = findViewById<TextView>(R.id.customerName)
        customerName.text = intent.getStringExtra("name")
        val products = intent.getSerializableExtra("products") as? HashMap<String, Int>
        for (record in products!!.keys) {
            addProductRow(tableLayout, record, products[record]!!)
        }

        val gogo =intent.getBooleanExtra("isChecked", false)
        findViewById<CheckBox>(R.id.checkBox2).isChecked = gogo

        findViewById<CheckBox>(R.id.checkBox2).setOnCheckedChangeListener { _, isChecked ->
            val resultIntent = Intent()
            resultIntent.putExtra("isChecked", isChecked)
            resultIntent.putExtra("name", findViewById<TextView>(R.id.customerName).text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun addProductRow(tabLayout: TableLayout, name: String, count: Int) {
        val newRow = LayoutInflater.from(this).inflate(R.layout.product_row, null) as TableRow

        newRow.findViewById<TextView>(R.id.productName).text = name
        newRow.findViewById<TextView>(R.id.productCount).text = count.toString()

        tabLayout.addView(newRow)
    }
}