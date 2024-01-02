package com.test.walmartcountrylist.domain.mapper

interface DataMapper<T, R> {
   suspend fun map(input: T): R
}
