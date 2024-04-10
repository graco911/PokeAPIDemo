package com.gracodev.pokeapidemo.modules

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.gracodev.data.database.AppDatabase
import com.gracodev.data.database.IPokemonRoom
import com.gracodev.data.database.PokemonRoomDataSource
import com.gracodev.data.database.PokemonRoomDatabase
import com.gracodev.data.remote.IPokemonAPI
import com.gracodev.data.remote.PokeAPI
import com.gracodev.data.remote.PokeAPIDataSource
import com.gracodev.data.remote.RetrofitPokeAPI
import com.gracodev.data.repository.PokemonPagingMediatorRepository
import com.gracodev.data.repository.PokemonPagingRepository
import com.gracodev.data.repository.PokemonRepository
import com.gracodev.domain.usecase.FetchPokemonListUseCase
import com.gracodev.domain.usecase.FetchPokemonPagingListUseCase
import com.gracodev.pokeapidemo.factories.PokemonViewModelFactory
import com.gracodev.pokeapidemo.viewmodel.PokemonListViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

fun createAppModules(): Module = module {

    single {
        createWebService<PokeAPI>(
            okHttpClient = createHttpClient(get()),
            factory = CoroutineCallAdapterFactory(),
            baseUrl = "https://pokeapi.co/api/v2/"
        )
    }

    single {
        Room.databaseBuilder(
            androidApplication(), AppDatabase::class.java,
            "pokemon_db"
        )
            .build()
    }

    single {
        val database = get<AppDatabase>()
        database.pokemonDAO()
    }

    single { Dispatchers.IO }

    single<IPokemonAPI> { RetrofitPokeAPI(get()) }
    single<IPokemonRoom> { PokemonRoomDatabase(get()) }
    single { PokeAPIDataSource(get(), get()) }
    single { PokemonRoomDataSource(get(), get()) }
    single { PokemonRepository(get(), get()) }
    single { PokemonPagingRepository(get(), get()) }
    single { PokemonPagingMediatorRepository(get()) }
    single { FetchPokemonListUseCase(get(), get()) }
    single { FetchPokemonPagingListUseCase(get(), get()) }

    factory { PokemonViewModelFactory(get(), get()) }

    viewModel { PokemonListViewModel(get(), get()) }
}

fun createHttpClient(context: Context): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.MINUTES)
        .retryOnConnectionFailure(true)
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory,
    baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}