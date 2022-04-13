package com.leebaeng.lbpushupcounter.data.model

import com.leebaeng.lbpushupcounter.data.model.entity.Plan

data class SetInfo(
    val id: Int,
    var name: String,
    var parentPlan: Plan,
    var target: Int,
)
