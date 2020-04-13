package ua.cn.stu.laba4.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("api/id/gh4g-9sfh.json/?$query=select%20*%2C%20%3Aid%20where%20(%60year%60%20%3E%3D%20%272002-04-01T00%3A00%3A00%27%20and%20%60year%60%20%3C%3D%20%272020-04-01T00%3A00%3A00%27)%20limit%20100")
    Call<List<Data>> getAll();
}
