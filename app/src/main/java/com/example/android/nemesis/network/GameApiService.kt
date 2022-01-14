package com.example.android.nemesis.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import timber.log.Timber

// Final example urls
// https://api.geekdo.com/xmlapi2/collection?username=nemesisgent
// https://api.geekdo.com/xmlapi2/thing?id=266504

private const val BASE_URL = "https://androidnemesisapi.p.rapidapi.com/"

// create moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

// Scalars Converter = converter for strings to plain text bodies
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface GameApiService {

    @Headers("x-rapidapi-key: 3f5bd416e6msh337cea95ca735b8p14ba9ajsn1ceb80d95615")
    @GET("mock/data")
    fun getGames(): Deferred<ApiGameContainer>

    /*
    * Note: the game api doesn't have a POST endpoint
    * This is just an example
    * */
    @POST("game")
    fun putGame(@Body game: ApiGame): Deferred<ApiGame>
}

object GameApi {
    // lazy properties = thread safe --> can only be initialized once at a time
    // adds extra safety to our 1 instance we need.
    val retrofitService: GameApiService by lazy {

        Timber.i("Received the retrofitService")

        retrofit.create(GameApiService::class.java)
    }

    fun GameApiService.mockPutGame(game: ApiGame): ApiGame {
        return game
    }
}