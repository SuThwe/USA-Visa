package com.su.purple.usvisapreparation.model

import java.io.Serializable

data class PopularQuestion(
    var id: Int = 0,
    var title: String = "",
    var explanation: String = "",
    var example: String = "",
    var saved_answer: String = ""

): Serializable