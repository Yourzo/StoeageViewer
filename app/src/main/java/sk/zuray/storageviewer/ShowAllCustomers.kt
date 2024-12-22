package sk.zuray.storageviewer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ShowAllCustomers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_all_customers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tableLayout = findViewById<TableLayout>(R.id.tableData)
        val customers = intent.getSerializableExtra("customers") as? ArrayList<Customer>
        for (record in customers!!) {
            addCustomerRow(tableLayout, record.name)
        }
    }

    private fun addCustomerRow(tableLayout: TableLayout, name: String) {
        val newRow = LayoutInflater.from(this).inflate(R.layout.customer_row, null) as TableRow
        newRow.findViewById<TextView>(R.id.customerName).text = name
        tableLayout.addView(newRow)
    }

    fun showCustomer(view: View) {
        val textV = view as TextView
        val resultIntent = Intent()
        resultIntent.putExtra("name", textV.text.toString())
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}