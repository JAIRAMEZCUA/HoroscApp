package com.aristidevs.horoscapp.data.network.response

import com.aristidevs.horoscapp.domain.model.PredictionModel
import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("date") val date: String,
    @SerializedName("horoscope") val horoscope: String,
    @SerializedName("sign") val sign: String,
) {

    //esta es una funcion de extension para mapear datos del servidor  a dtos
    //los dtos ayudan a que se pueda reformatear el agregar nuevos datos y/o variables o solo obtener datos
    fun toDomain(): PredictionModel {
        return PredictionModel(
            horoscope = horoscope,
            sign = sign
        )
    }
}