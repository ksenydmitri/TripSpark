package com.ksenia.tripspark.domain.model

import android.location.Location

data class Destination (
    val id: String,
    val name: String,
    val description: String,
    val location: Location,
    val imageUrl: String,
    val rating: Double,
    val vector: List<Float> = emptyList()
// Индексы в векторе:
// [0] — Культурные интересы (музеи, искусство, архитектура)
// [1] — Природа и пейзажи (горы, озёра, леса)
// [2] — Активный отдых (походы, лыжи, серфинг)
// [3] — Романтика и уединение (звёзды, рассвет, спокойствие)
// [4] — Холодный климат (снег, север, зима)
// [5] — События и гастротуризм (фестивали, еда, ярмарки)

)