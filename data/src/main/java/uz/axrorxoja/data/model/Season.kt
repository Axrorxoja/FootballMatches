package uz.axrorxoja.data.model

import com.squareup.moshi.Json

class Season(
    val id: Long,
    val startDate: String,
    val endDate: String,
    @field:Json(name = "currentMatchday") val currentMatchDay: String?
)