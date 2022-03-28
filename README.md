# BMI App (Background thread: use Coroutines and ProgressBar)

### 說明：
運用Coroutines模擬耗時的檢測過程，用ProgressBar顯示進度。

### 上畫面：
![](https://i.imgur.com/onlawNW.gif)


### 關鍵程式碼：
1. progress.visibility = View.VISIBLE / View.GONE
2. GlobalScope.launch(Dispatchers.Main) {}
3. Pair類別->(stand_weight, body_fat)
4. 將EditText轉成Double -> ed_height.text.toString().toDouble()
5. 算BMI時要用到平方-> .pow(2)
6. 將Double轉字串，並顯示小數點到第二位->String.format("%.2f", stand_weight)
7. 讓 ProgressBar 進度條動作 -> progressBar.progress = 100 (Int)

### 還沒搞懂的部分：
1. progress的三個設定：
 - elevation = "3dp"
 - focusable = "true"
 - clickable = "true"
