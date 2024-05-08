package com.example.teashop.data.api

import com.example.teashop.data.enums.StatisticsSortType
import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.pagination.product.ProductAccountingResponse
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.saves.ProductSave
import com.example.teashop.data.model.statistics.Statistics
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountingApiService {

    @POST("accounting/product/actions/search-by-filter")
    suspend fun getProductsByFilter(
        @Header("Authorization") token: String?,
        @Body request: ProductPagingRequest
    ): Response<ProductAccountingResponse>

    @POST("accounting/product")
    suspend fun createNewProduct(
        @Header("Authorization") token: String?,
        @Body productSave: ProductSave
    ): Response<ProductFull>

    @Multipart
    @POST("accounting/upload-images")
    suspend fun uploadImages(
        @Part files: List<MultipartBody.Part>
    ): Response<List<Long?>>

    @PUT("accounting/orders/{id}")
    suspend fun updateStatusOrTrack(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Query("status") status: String,
        @Query("track") track: String?
    ): Response<Order>

    @GET("accounting/statistics")
    suspend fun getStatisticsByPeriod(
        @Header("Authorization") token: String,
        @Query("period") sortType: StatisticsSortType,
    ): Response<Statistics>

    companion object {
        val accountingApiService: AccountingApiService by lazy {
            retrofitBuilder.create(AccountingApiService::class.java)
        }
    }
}