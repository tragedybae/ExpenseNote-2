package com.example.expensenote_ex

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    //สร้างตัวเเปรสำหรับ UI
    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnCler: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    //ตัวเเปรสำหรับเก็บค่ายอดรวมทั้งหมด
    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เชื่อมโยงตัวเเปร เข้ากับ ID ในหน้า XML
        etAmount = findViewById(R.id.etAmount)
        etNote = findViewById(R.id.etNote)
        btnAdd = findViewById(R.id.btnAdd)
        btnCler = findViewById(R.id.btnCler)
        tvLastItem = findViewById(R.id.tvLastItem)
        tvTotal = findViewById(R.id.tvTotal)
        //กำหนดการทำงานเมื่อผู้ใช้ทำการกดปุ่มเพิ่มรายการ
        btnAdd.setOnClickListener {
            //สร้างตัวเเปรท่ี่เอาไว้เก็บข้อมูลจากช่อง
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            //ทำการตรวจสอบว่าผู้ใช้ทำการกรอกจำนวนเงินมาเเล้วหรือยัง
            if (amountText.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //ทำการตรวจสอบว่าผู้ใช้ทำการกรอจำนวนเงินมาเเล้วหรือยัง
            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount < 0.0) {
                Toast.makeText(this, "กรุณากรอกจำนวนเงินให้ถูกต้อง", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            total += amount

            val displayNote = if (noteText.isNotEmpty()) noteText else "-"
            val formattedAmount = String.format(Locale.US, "%.2f", amount)
            tvLastItem.text = "รายการล่าสุด: $displayNote ($formattedAmount บาท)"

            val formattedTotal = String.format(Locale.US, "%.2f", total)
            tvTotal.text = "ยอดรวมวันนี้: $formattedTotal บาท"

            etAmount.text.clear()
            etNote.text.clear()
        }

        btnCler.setOnClickListener {
            total = 0.0

            etAmount.text.clear()
            etNote.text.clear()

            tvLastItem.text = "รายการล่าสุด:-"
            tvTotal.text = "ยอดรวมวันนี้:0.00บาท"

        }
    }
}
