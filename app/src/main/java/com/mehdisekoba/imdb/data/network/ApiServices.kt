package com.mehdisekoba.imdb.data.network

import com.mehdisekoba.imdb.data.model.detail.ResponseActor
import com.mehdisekoba.imdb.data.model.detail.ResponseDetail
import com.mehdisekoba.imdb.data.model.detail.ResponsePlayerVideo
import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar
import com.mehdisekoba.imdb.data.model.home.ResponseMovies
import com.mehdisekoba.imdb.data.model.home.ResponsePopular
import com.mehdisekoba.imdb.data.model.home.ResponseUpComing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/top_rated")
    suspend fun getTopRatedList(): Response<ResponsePopular>

    @GET("movie/popular")
    suspend fun getPopularList(
        @Query("page") page: Int,
    ): Response<ResponsePopular>

    @GET("discover/tv")
    suspend fun getTvList(): Response<ResponseMovies>

    @GET("tv/on_the_air")
    suspend fun getOnTheAir(): Response<ResponseMovies>

    @GET("movie/upcoming")
    suspend fun getUpComing(): Response<ResponseUpComing>

    // details
    @GET("movie/{id}")
    suspend fun getMoviesDetail(
        @Path("id") id: Int,
    ): Response<ResponseDetail>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Int,
    ): Response<ResponseSimilar>

    @GET("movie/{movie_id}/videos")
    suspend fun getPlayVideo(
        @Path("movie_id") id: Int,
    ): Response<ResponsePlayerVideo>

    // ResponseSearch
    @GET("search/movie")
    suspend fun searchByKeyword(
        @Query("query") keyWord: String,
    ): Response<ResponseSimilar>

    // Actor
    @GET("person/{person_id}/movie_credits")
    suspend fun getPopularActor(
        @Path("person_id") id: Int,
    ): Response<ResponseActor>

    // with genres
    @GET("discover/movie###/{with_genres}")
    suspend fun moviesWithGenres(
        @Query("with_genres") id: Int,
    ): Response<ResponseSimilar>
}
