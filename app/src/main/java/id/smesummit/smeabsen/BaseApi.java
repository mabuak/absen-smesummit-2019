/*
 * Created By Fachruzi Ramadhan
 *
 * @Filename     BaseApi.java
 * @LastModified 4/22/19 8:07 PM.
 *
 * Copyright (c) 2019. All rights reserved.
 */

package id.smesummit.smeabsen;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import id.smesummit.smeabsen.Services.AttendaceService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApi {

    public final static String BASE_URL = "https://api.smesummit.id/";

    public static AttendaceService Route() {

        OkHttpClient.Builder okClient = new OkHttpClient.Builder();
        okClient.readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS);

        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder();

                requestBuilder.method(original.method(), original.body());

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });

        // IF YOU NEED DISPLAY LOG NETWORK REQUEST AND RESPONSE
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okClient.addInterceptor(logging);  // <-- this is the important line!

        //END LOG DISPLAY

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();


        return client.create(AttendaceService.class);
    }

}
