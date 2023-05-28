package com.digipod.demo

import com.google.android.material.internal.ManufacturerUtils
import java.io.Serializable

data class MedicineModel(
    val image: String = "",
    val name: String = "",
    val manufacturer: String = "",
    val price: String = "100",
    ): Serializable