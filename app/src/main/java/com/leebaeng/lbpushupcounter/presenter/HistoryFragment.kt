package com.leebaeng.lbpushupcounter.presenter

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.leebaeng.lbpushupcounter.R
import com.leebaeng.lbpushupcounter.data.chart.DateXAxisValueFormatter
import com.leebaeng.lbpushupcounter.databinding.FragmentHistoryBinding
import com.leebaeng.lbpushupcounter.presenter.view.GraphMarkerView
import java.util.*
import kotlin.properties.Delegates

class HistoryFragment : BaseFragment(R.layout.fragment_history) {
    private lateinit var binding: FragmentHistoryBinding
    private var textColorPrimary by Delegates.notNull<Int>()
    private var textColorSecond by Delegates.notNull<Int>()
    private var is30DayGraph = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
        binding.fragment = this

        textColorPrimary = requireContext().getColorThemeRes(android.R.attr.textColorPrimary)
        textColorSecond = requireContext().getColorThemeRes(android.R.attr.textColorTertiary)

        initChartSetting(textColorPrimary)
        load30DaysGraph()
    }

    fun onClick(view: View) {
        when (view) {
            binding.btnSwapGraphMode -> {
                if (is30DayGraph) {
                    load1yearGraph()
                    binding.txtHistoryMode.text = resources.getString(R.string.history_view_mode_1year)
                    binding.btnSwapGraphMode.text = resources.getString(R.string.history_view_mode_30days)
                } else {
                    load30DaysGraph()
                    binding.txtHistoryMode.text = resources.getString(R.string.history_view_mode_30days)
                    binding.btnSwapGraphMode.text = resources.getString(R.string.history_view_mode_1year)
                }
                is30DayGraph = !is30DayGraph
            }
            binding.btnGoHome -> navController.navigate(R.id.action_historyFragment_to_mainFragment)
        }
    }

    private fun initChartSetting(@ColorInt textColorPrimary: Int) {
        val chart = binding.combinedChart
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.textColor = textColorPrimary
        chart.axisRight.textColor = textColorPrimary
        chart.axisLeft.textColor = textColorPrimary
        chart.setNoDataTextColor(textColorPrimary)
        chart.legend.textColor = textColorPrimary
        chart.description.textColor = textColorPrimary
        chart.setDrawGridBackground(false)
//        chart.xAxis.axisMinimum = -0.5f
//        chart.xAxis.axisMaximum = 30f

        chart.setTouchEnabled(true)
        chart.marker = GraphMarkerView(requireContext(), R.layout.view_graph_marker)

        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.axisRight.setDrawLabels(false)
        chart.isScaleYEnabled = false

        chart.invalidate()
    }

    private fun load30DaysGraph() {
        // TODO: room을 통해 얻어온 데이터 삽입. 현재는 가라 데이터
        val entryList = mutableListOf<BarEntry>()
        val calendar = Calendar.getInstance()

        for (i in 0 .. 30) {
            calendar.time = Date()
            calendar.add(Calendar.DATE, -(30-i))
            val x = calendar.timeInMillis.toFloat() / 1000f
            entryList.add(BarEntry(x, i + (10..130).random().toFloat()))
        }
        showGraph(entryList)
    }

    fun load1yearGraph() {
        // TODO: room을 통해 얻어온 데이터 삽입. 현재는 가라 데이터
        val entryList = mutableListOf<BarEntry>()
        val calendar = Calendar.getInstance()

        for (i in 0 until 365) {
            calendar.time = Date()
            calendar.add(Calendar.DATE, -(365-i))
            val x = calendar.timeInMillis.toFloat() / 1000f
            entryList.add(BarEntry(x, (10..130).random().toFloat()))
        }
        showGraph(entryList)
    }

    private fun showGraph(barDataEntry: MutableList<BarEntry>) {
        fun createBarData(@ColorInt textColor: Int, @ColorInt gradientStart: Int, @ColorInt gradientEnd: Int): BarData {
            val barDataSet = BarDataSet(barDataEntry, "")
            barDataSet.color = textColor
            barDataSet.setDrawValues(false)
            barDataSet.setGradientColor(gradientStart, gradientEnd)

            val barData = BarData(barDataSet)
            barData.barWidth = 0.3f

            return barData
        }

        fun createLineData(@ColorInt textColor: Int): LineData {
            val entryListLine = mutableListOf<Entry>()
            barDataEntry.forEach { barEntry ->
                entryListLine.add(Entry(barEntry.x, barEntry.y))
            }

            val lineDataSet = LineDataSet(entryListLine, "MyBarDataSet")
            lineDataSet.color = textColor
            lineDataSet.valueFormatter = DefaultValueFormatter(0)
            lineDataSet.setDrawCircles(true)
            lineDataSet.lineWidth = 1f
            lineDataSet.valueTextSize = 11f
            lineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            lineDataSet.setDrawValues(false)

            return LineData(lineDataSet)
        }

        val barData = createBarData(textColorPrimary, textColorPrimary, textColorSecond)
        val lineData = createLineData(textColorPrimary)

        val combineData = CombinedData()
        combineData.setData(barData)
        combineData.setData(lineData)

        val chart = binding.combinedChart
        chart.clear()
//        chart.xAxis.axisMaximum = barDataEntry.size.toFloat()
        chart.xAxis.valueFormatter = DateXAxisValueFormatter()
        chart.scaleX = 1f
        chart.data = combineData

        chart.invalidate()
    }

    @ColorInt
    fun Context.getColorThemeRes(@AttrRes id: Int): Int {
        val resolvedAttr = TypedValue()
        this.theme.resolveAttribute(id, resolvedAttr, true)
        return this.getColor(resolvedAttr.resourceId)
    }
}