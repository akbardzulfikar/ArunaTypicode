package co.id.aruna.typicode.data.api

import co.id.aruna.typicode.data.model.UsersItem
import io.reactivex.Flowable
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    fun getData() : Flowable<List<UsersItem>>
}