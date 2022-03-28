package com.example.inbodyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var btn_calculate: Button
    private lateinit var ed_height: EditText
    private lateinit var ed_weight: EditText
    private lateinit var ed_age: EditText
    private lateinit var tv_weight: TextView
    private lateinit var tv_fat: TextView
    private lateinit var tv_bmi: TextView
    private lateinit var tv_progress: TextView
    private lateinit var progressBar2: ProgressBar
    private lateinit var ll_progress: LinearLayout
    private lateinit var btn_boy: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 將變數與XML元件綁定
        btn_calculate = findViewById(R.id.btn_calculate)
        ed_height = findViewById(R.id.et_height)
        ed_weight = findViewById(R.id.et_weight)
        ed_age = findViewById(R.id.et_age)
        tv_weight = findViewById(R.id.tv_result_weight)
        tv_fat = findViewById(R.id.tv_result_fat)
        tv_bmi = findViewById(R.id.tv_result_bmi)
        tv_progress = findViewById(R.id.tv_progress)
        progressBar2 = findViewById(R.id.progressBar2)
        ll_progress = findViewById(R.id.ll_progress)
        btn_boy = findViewById(R.id.radioButton_man)

        // 對計算按鈕設定監聽器
        btn_calculate.setOnClickListener {
            when {
                ed_height.length() < 1 -> showToast("請輸入身高")
                ed_weight.length() < 1 -> showToast("請輸入體重")
                ed_age.length() < 1 -> showToast("請輸入年齡")
                else -> runCoroutines()
            }
        }
    }

    private fun showToast(msg: String){
        AlertDialog.Builder(this)
            .setTitle("錯誤訊息")
            .setMessage(msg)
            .setPositiveButton("確認"){dialog, which ->
                Toast.makeText(this, "確定", Toast.LENGTH_SHORT)
            }.show()
    }

    // 用 Coroutines 模擬檢測過程
    private fun runCoroutines(){
        tv_weight.text = "標準體重\n無"
        tv_fat.text = "體脂肪\n無"
        tv_bmi.text = "BMI\n無"
        // 初始化進度條
        progressBar2.progress = 0
        tv_progress.text = "0%"
        // 顯示進度條
        ll_progress.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.Main) {
            var progress = 0
            while(progress < 100) {
                // 執行緒延遲0.5秒
                delay(50)
                // 執行進度更新
                progressBar2.progress = progress
                tv_progress.text = "${progress}%"
                // 計數加一
                progress++
            }
            ll_progress.visibility = View.GONE

            val height = ed_height.text.toString().toDouble()
            val weight = ed_weight.text.toString().toDouble()
            val age = ed_age.text.toString().toDouble()
            // 計算BMI:體重/(（身高/100）平方)
            val bmi = weight / ((height / 100).pow(2))
            // 男體脂：(身高-80）* 0.7, 1.39 * bmi + 0.16 * age - 19.34
            // 女體脂：（身高-70）*0.6, 1.39 * bmi + 0.16 * age - 9
            // 計算男女體脂率並使用Pair類別進行解構宣告
            val (stand_weight, body_fat) = if (btn_boy.isChecked) {
                Pair((height-80)*0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height-70)*0.6, 1.39 * bmi + 0.16 * age - 9)
            }

            tv_weight.text = "標準體重\n${String.format("%.2f", stand_weight)}"
            tv_fat.text = "體脂肪\n${String.format("%.2f", body_fat)}"
            tv_bmi.text = "BMI\n${String.format("%.2f", bmi)}"
        }


    }
}