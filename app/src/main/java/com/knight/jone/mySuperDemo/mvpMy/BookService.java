package com.knight.jone.mySuperDemo.mvpMy;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：HWQ on 2018/3/30 18:01
 * 描述：
 */

public interface BookService {

    String baseUrl = "https://api.douban.com/v2/";

    @GET("book/search/")
    Observable<BookSearchBean> search(@Query("q") String q,
                                      @Query("tag") String tag,
                                      @Query("start") int startNum,
                                      @Query("count") int count);
}
