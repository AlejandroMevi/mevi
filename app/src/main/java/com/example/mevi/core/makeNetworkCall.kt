package com.example.mevi.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> makeNetworkCall(call: suspend () -> T): ApiResponceStatus<T> =
    withContext(Dispatchers.IO) {
        try {
            ApiResponceStatus.Success(call())
        } catch (exception: Exception) {
            ApiResponceStatus.Error(exception.message.toString())
        }
    }

fun evaluateResponce(kind: String, errorMessage: String? = null) {
    if (kind != null) {
        if (kind.isNotEmpty()) {
            throw Exception(kind)
        } else {
            if (!errorMessage.isNullOrEmpty()) {
                throw Exception(errorMessage)
            } else {
                throw Exception("")
            }
        }
    }
}