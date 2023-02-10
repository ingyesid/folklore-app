package com.folklore.app.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventModel(
    val id: String,
    val title: String,
    val shortDescription: String,
    val imageUrl: String = "https://media.istockphoto.com/id/613897214/es/foto/fiesta-del-evento-del-festival-con-hipsters-de-fondo-borroso.jpg?s=2048x2048&w=is&k=20&c=dwK2aP3E1jXvsBciI3uU7kNDcqfVMc1k2njFdefq2Ok=",
    val goingCount: Int,
    val likes: Int,
    val startAt: String,
    val endsAt: String,
    val city: String,
    val state: String,
) : Parcelable
