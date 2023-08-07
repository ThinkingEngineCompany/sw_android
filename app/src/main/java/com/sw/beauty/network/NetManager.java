package com.sw.beauty.network;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    public static NetManager sInstance;
    private OkHttpClient client;
    private RestService mRestService;
    public static final long CONNECT_TIMEOUT_MILLIS = 20 * 1000;
    public static final long READ_TIMEOUT_MILLIS = 30 * 1000;
    public static String getBaseUrl() {

        return "http://39.100.119.156:8080/";
    }

    public static RestService getService() {
        if (sInstance == null) {
            sInstance = new NetManager();
        }

        if (sInstance.mRestService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(sInstance.client)
                    .baseUrl(getBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
            sInstance.mRestService = retrofit.create(RestService.class);
        }
        return sInstance.mRestService;
    }

    public NetManager() {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
//            loggingInterceptor.setLevel(BODY);
//        } else {
//            HttpLoggingInterceptor.Logger logger = HLog::i;
//            loggingInterceptor = new HttpLoggingInterceptor(logger);
//            loggingInterceptor.setLevel(HEADERS);
//        }

        try {
            client = new OkHttpClient.Builder()
//                    .sslSocketFactory(new TLSSocketFactory(), new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] chain,
//                                                       String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] chain,
//                                                       String authType) {
//                        }
//
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[] {};
//                        }
//                    })
                    .hostnameVerifier((hostname, session) -> true)
//                    .authenticator(new TokenAuthenticator())
//                    .addInterceptor(loggingInterceptor)
//                    .addInterceptor(chain -> {
//                        Request original = chain.request();
//                        Request.Builder header = original.newBuilder()
//                                .header("Content-Type", "application/x-www-form-urlencoded");
//                        if (!TextUtils.isEmpty(LoginRepository.getInstance().getLoginToken())) {
//                            header.addHeader("Authorization",
//                                    "JWT " + LoginRepository.getInstance().getLoginToken());
//                        }
//                        Request request = header
//                                .method(original.method(), original.body())
//                                .build();
//                        return chain.proceed(request);
//                    })
                    .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
