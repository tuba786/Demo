package com.digipod.demo.Model

import com.digipod.demo.InsuranceModel

class InsuranceCartModel(
                         val uid: String = "",
                         val insuranceItem: InsuranceItem= InsuranceItem(InsuranceModel()),
                         val phone: String="",
)
data class InsuranceItem(
    val insurance: InsuranceModel = InsuranceModel(),
    val price: Int = 0

)