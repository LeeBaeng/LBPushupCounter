package com.leebaeng.lbpushupcounter

import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    data class FilterData(val code: String, val text: String, val optDataMap: HashMap<String, FilterOptionData>, var selectedOpt: FilterOptionData? = null)
    data class FilterOptionData(val code: String, val text: String)

    lateinit var filterInfoMap: HashMap<String, FilterData> // Key = FilterCd

    @Test
    fun test() {
        initMenu()

        println("")
        println("")

        filterInfoMap["1"]!!.selectedOpt = filterInfoMap["1"]!!.optDataMap["100"]
        filterInfoMap["2"]!!.selectedOpt = filterInfoMap["2"]!!.optDataMap["400"]
        filterInfoMap["3"]!!.selectedOpt = filterInfoMap["3"]!!.optDataMap["900"]

        fun createFilterString(sb: StringBuffer): String =
            Optional.ofNullable(sb)
                .filter { it.isNotEmpty() && it.endsWith("|") }
                .map { it.substring(0, it.length - 1) }
                .orElse(sb.toString())

        val sbFilterCd = StringBuffer()
        val sbFilterOpt = StringBuffer()
        val iterator = filterInfoMap.iterator()
        while (iterator.hasNext()) {
            val filter = iterator.next()
            filter.value.selectedOpt?.let {
                println("filter.value.selectedOpt >> ${filter.key} = ${it.code}")
                sbFilterCd.append("${filter.key}|")
                sbFilterOpt.append("${it.code}|")
            }
        }
        val filterCd = createFilterString(sbFilterCd)
        val filterOpt = createFilterString(sbFilterOpt)

        println("==============================")
        println("filterCd : $filterCd")
        println("filterOpt : $filterOpt")
    }

    fun initMenu() {
        // Parse filter info and convert to data map
        val ocFilterList = ArrayList<OCFilter>()
        for (i in 1 until 6) {
            ocFilterList.add(OCFilter(filterCd = "$i", filterOptionCdList = "${i * 100}|${i * 200}|${i * 300}",
                filterText = "Filter$i", filterOptionTextList = "TT${i * 100}|TT${i * 200}|TT${i * 300}"
            ))
        }

        filterInfoMap = HashMap()
        ocFilterList.forEach {
            runNotNull(it.filterCd, it.filterText, it.filterOptionCdList, it.filterOptionTextList) setFilter@{
                val splitOptCd = it.filterOptionCdList!!.split("|")
                val splitOptText = it.filterOptionTextList!!.split("|")

                if (splitOptCd.size != splitOptText.size) {
                    println("occur error while parse filter info. cuz different size between splitOptCd & splitOptText")
                    return@setFilter
                }

                val optMap = HashMap<String, FilterOptionData>()
                splitOptCd.forEachIndexed { index, code ->
                    optMap[code] = FilterOptionData(code, splitOptText[index])
                }

                val filterData = FilterData(
                    code = it.filterCd!!,
                    text = it.filterText!!,
                    optDataMap = optMap,
                    selectedOpt = null
                )

                filterInfoMap[filterData.code] = filterData
                println("AddNewFilter > [${filterData.code}] > $filterData")
            }
        }
    }

    data class OCFilter(
        val catId: String? = null,
        val catGb: String? = null,
        val filterCd: String? = null,
        val filterText: String? = null,
        val filterOptionCdList: String? = null,
        val filterOptionTextList: String? = null
    )
}

inline fun <T : Any> runNotNull(vararg args: Any?, block: () -> T) {
    args.forEach {
        if (it == null) return
    }
    block()
}