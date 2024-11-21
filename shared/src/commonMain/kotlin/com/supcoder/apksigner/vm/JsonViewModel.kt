package com.supcoder.apksigner.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.russhwolf.settings.ExperimentalSettingsApi
import utils.update

/**
 * JsonViewModel
 *
 * @author lee
 * @date 2024/11/21
 */
class JsonViewModel @OptIn(ExperimentalSettingsApi::class) constructor() : ViewModel() {

    val gson = GsonBuilder().setPrettyPrinting().create()


    private var _rawJson = mutableStateOf("")
    val rawJson by _rawJson

    private var _formattedJson = mutableStateOf("")
    val formattedJson by _formattedJson


    fun updateFormatJson(json: String) {
        _formattedJson.update {
            json
        }
    }

    fun updateRawJson(json: String) {
        _rawJson.update {
            json
        }
    }

    fun convertJson(isExpand: Boolean) {
        val json = try {
            System.out.printf("rawJson = $rawJson")
            val jsonObject = JsonParser.parseString(rawJson)
            gson.toJson(jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            "无效的 JSON"
        }
        if (isExpand){
            updateFormatJson(json)
        } else{
            updateRawJson(json)
        }
    }


}