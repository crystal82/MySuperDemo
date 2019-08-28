package xhh.android.com.xhh;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018-02-11.
 */

public interface GetRequest {
    @GET("regeo?key=ee57b33235a997056e2badf2f2ce421f")
    Call<rep> getCall(@Query("location") String targetSentence);
}
