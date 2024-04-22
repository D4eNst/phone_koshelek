package com.d4enst.laba_1_koshelek

fun String.addParams(vararg params: String): String {
    return if (params.isNotEmpty()) {
        this + "/" + params.joinToString("&")
    } else {
        this
    }
}
