package com.digipod.demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digipod.demo.network.Api
import com.digipod.demo.network.Disease
import com.digipod.demo.network.RequestInp
import kotlinx.coroutines.launch

enum class ResponseStatus { LOADING, ERROR, DONE }

class DiseaseViewModel : ViewModel() {

    private val _status = MutableLiveData<ResponseStatus>()
    val status: LiveData<ResponseStatus> = _status

    private val _diseases = MutableLiveData<List<List<Disease>>>()
    val diseases get() = _diseases

    public fun getResult(symptoms: String) {
        getDiseasePrediction(symptoms)
    }


    fun getDiseasePrediction(inputs: String) {
        val requestBody = RequestInp(inputs)
        viewModelScope.launch {
            try{
                val response = Api.retrofitService.getDiseasePrediction(requestBody)
                _diseases.value = response
                Log.d("SymptomViewModel", "getDiseasePrediction: ${_diseases.value?.get(0)}")
            }
            catch (e:Exception){
                Log.d("SymptomViewModel",e.message.toString())
            }
        }
    }
}