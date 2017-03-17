package com.kartuzov.testappgit.gittest;

import com.alorma.github.sdk.bean.dto.response.search.UsersSearch;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;

/**
 * Created by Mory4ok on 14.03.17.
 */

public interface SearchClientService {

    @GET("/search/users")
    Call<UsersSearch> users(@Query("q") String query);
}
