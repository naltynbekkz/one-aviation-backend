package com.naltynbekkz.oneaviation.util.entity

class Page<T> constructor(
    val results: List<T>,
    val page: Int? = 0,
    val size: Int? = 20,
    val totalPages: Int,
)