package org.esisalama.quizmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubUserService {
    @GET("search/users?")
    Call<Result> getResult(@Query("q") String keyword);
}
