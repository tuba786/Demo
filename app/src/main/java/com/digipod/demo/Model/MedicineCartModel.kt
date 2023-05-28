package com.digipod.demo.Model

import com.digipod.demo.MedicineModel

data class MedicineCartModel(
    val uid: String = "",
    val medicineItem: MedicineItem= MedicineItem(MedicineModel()),
    val phone: String="",
)


data class MedicineItem(
    val medicine: MedicineModel= MedicineModel(),
    val qty: Int = 1,
    val price: Int = 0
)

data class MedicineOrderModel(
    val orders: List<MedicineItem>,
    val phone: String = ""
)
