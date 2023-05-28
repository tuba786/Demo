package com.digipod.demo

class DoctorModel(
    var doctorResourceId: Int, var location: String, var doctorName: String,
    var specialization: String, var post: String, var language: String,
    var experience: String, var fees: Float = 1000.0f
) {

}