package com.dixitpatel.mycoffeevenue.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *  NearbyPlace Listing Result.
 */
data class NearByPlaceResponse(
@SerializedName("meta")
@Expose val meta: Meta,
@SerializedName("response")
@Expose val response: Response
)
{
    data class Meta(
        @SerializedName("code")
        @Expose val code: Int,
        @SerializedName("requestId")
        @Expose val requestId: String,
        @SerializedName("errorType")
        @Expose val errorType: String,
        @SerializedName("errorDetail")
        @Expose val errorDetail: String,
    )

    data class Response(
        @SerializedName("headerLocation")
        @Expose val headerLocation: String,
        @SerializedName("totalResults")
        @Expose val totalResults: Int,
        @SerializedName("groups")
        @Expose val groups: List<Group>
    )

    data class Venue (
        @SerializedName("id")
        @Expose val id: String,
        @SerializedName("name")
        @Expose val name: String,
        @SerializedName("location")
        @Expose val location: Location,
        @SerializedName("categories")
        @Expose val categories: List<Category>
    )


    data class Group (
        @SerializedName("type")
        @Expose
        val type: String,
        @SerializedName("name")
        @Expose
        val name: String,
        @SerializedName("items")
        @Expose
        val items: List<Item>
    )

    data class Item (
        @SerializedName("venue")
        @Expose
        val venue: Venue
    )

    data class Icon (
        @SerializedName("prefix")
        @Expose
        val prefix: String,
        @SerializedName("suffix")
        @Expose
        val suffix: String
    )

    data class Location(
        @SerializedName("address")
        @Expose
        val address: String,
        @SerializedName("crossStreet")
        @Expose
        val crossStreet: String,
        @SerializedName("lat")
        @Expose
        val lat: Double,
        @SerializedName("lng")
        @Expose
        val lng: Double,
        @SerializedName("distance")
        @Expose
        val distance: Int,
        @SerializedName("postalCode")
        @Expose
        val postalCode: String,
        @SerializedName("cc")
        @Expose
        val cc: String,
        @SerializedName("city")
        @Expose
        val city: String,
        @SerializedName("state")
        @Expose
        val state: String,
        @SerializedName("country")
        @Expose
        val country: String,
        @SerializedName("formattedAddress")
        @Expose
        val formattedAddress: List<String>,
        @SerializedName("neighborhood")
        @Expose
        val neighborhood: String
    )
    {
        fun formattedDistance() : String
        {
            return "Distance : $distance m"
        }

        fun finalFormattedAddress() : String
        {
            return formattedAddress.toString().drop(1).dropLast(1)
        }
    }

    data class Category (
        @SerializedName("id")
        @Expose
        val id: String,
        @SerializedName("name")
        @Expose
        val name: String,
        @SerializedName("icon")
        @Expose
        val icon: Icon
    )
    {
        fun getIconUrl(): String {
            return "${icon.prefix}bg_88${icon.suffix}"
        }
    }


}
