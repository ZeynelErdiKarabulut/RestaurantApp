

package com.zeynelerdi.pastryshop.repository.network

import com.google.gson.annotations.SerializedName
import com.zeynelerdi.pastryshop.bin.Contact
import com.zeynelerdi.pastryshop.bin.Pages

internal data class GetDataResponse(

        @field:SerializedName("pages")
        val pagesItem: ArrayList<PagesItem>,

        @field:SerializedName("contact")
        val contactInfo: ContactItem,

        @field:SerializedName("background")
        val background: String
)

internal data class PagesItem(

        @field:SerializedName("id")
        val id: Long,

        @field:SerializedName("image")
        val image: ArrayList<String>,

        @field:SerializedName("description")
        val description: String,

        @field:SerializedName("title")
        val title: String
) {

    fun toPage(): Pages {
        return Pages(id, title, description, image)
    }
}

internal data class ContactItem(

        @field:SerializedName("phone")
        val phone: String,

        @field:SerializedName("email")
        val email: String,

        @field:SerializedName("twitter")
        val twitter: String
) {

    fun toContact(): Contact {
        return Contact(phone, email, twitter)
    }
}