package com.example.teashop.admin_screen.statistics

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.teashop.R
import com.example.teashop.ui.theme.blueColor
import com.example.teashop.ui.theme.greenColor
import com.example.teashop.ui.theme.redColor
import com.example.teashop.ui.theme.yellowColor
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun PieChart() {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .size(320.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Crossfade(targetState = getPieChartData, label = "") { pieChartData ->
                AndroidView(factory = { context ->
                    PieChart(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                        this.description.isEnabled = false
                        this.isDrawHoleEnabled = false
                        this.legend.isEnabled = true
                        this.legend.textSize = 14F
                        this.legend.horizontalAlignment =
                            Legend.LegendHorizontalAlignment.CENTER
                        this.setEntryLabelColor(resources.getColor(R.color.white))
                    }
                },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp), update = {
                        updatePieChartWithData(it, pieChartData)
                    })
            }
        }
    }
}

fun updatePieChartWithData(
    chart: PieChart,
    data: List<PieChartData>
) {
    val entries = ArrayList<PieEntry>()

    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.browserName ?: ""))
    }
    val ds = PieDataSet(entries, "")
    ds.colors = arrayListOf(
        greenColor.toArgb(),
        blueColor.toArgb(),
        redColor.toArgb(),
        yellowColor.toArgb(),
    )
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.sliceSpace = 2f
    ds.valueTextSize = 18f
    ds.valueTypeface = Typeface.DEFAULT_BOLD

    val d = PieData(ds)
    chart.data = d
    chart.invalidate()
}