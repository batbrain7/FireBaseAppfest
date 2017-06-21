package tech.mohitkumar.firebaseappfest.GeoClient;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 31-10-2016.
 */

public interface FileUploadService {

  @GET("json")
  Call<GeoResponse> getLatLong(@Query("address") String uid,@Query("key") String APIKey);


}
