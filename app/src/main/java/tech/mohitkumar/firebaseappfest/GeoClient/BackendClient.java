package tech.mohitkumar.firebaseappfest.GeoClient;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lenovo on 10-07-2016.
 */
public class BackendClient
{
    private static FileUploadService service;

    public static FileUploadService getService() {

        if (service == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS).retryOnConnectionFailure(true)

                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request r = chain.request();
                            r = r.newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .build();
                            return chain.proceed(r);
                        }
                    })
                    .build();

            Retrofit r = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/geocode/").
            //Retrofit r = new Retrofit.Builder().baseUrl("http://192.168.1.8:3000").
                    addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().create())).client(client)
                    .build();
            service = r.create(FileUploadService.class);
        }
        return service;
    }
}
