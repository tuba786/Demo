package com.digipod.demo.Model

import com.digipod.demo.LabTestModel


class LabCartModel(
    val uid: String = "",
    val labItem: LabItem= LabItem(LabTestModel()),
    val phone: String="",
)


data class LabItem(
    val lab: LabTestModel = LabTestModel(),
    val price: Int = 0

)