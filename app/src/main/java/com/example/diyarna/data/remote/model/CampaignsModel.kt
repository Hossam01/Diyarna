package com.example.diyarna.data.remote.model

data class Campaigns(
	val campaigns: List<CampaignsItem>
)

data class CampaignsItem(
	val image: String,
	val requiredAmount: Double,
	val name: String,
	val description: String,
	val pay: Int,
	val actualAcmount: Double,
	val id: Int
)

