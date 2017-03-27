package com.example.rishabh.curotest.API;

import com.example.rishabh.curotest.Helpers.AppSettings;
import com.example.rishabh.curotest.Utils.Constants;
import com.example.rishabh.curotest.BuildConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by rishabh on 27/03/2017.
 */

  public class RestClient {
  public static final String BASE_URL = "https://qa-aws.lifeincontrol.com/";
  public static final String BASE_URL_RETRO = "https://qa-aws.lifeincontrol.com/";
    //private static final String BASE_URL = BuildConfig.BASE_URL_RETRO;
    private static AuthorizedApi apiService;
    private static final long CONNECTION_TIMEOUT = 120;
    private static RestClient restClient;
    private static Retrofit fitBitRestAdapter;

    public static RestClient getInstance() {
      if (restClient == null) {
        restClient = new RestClient();
      }
      return restClient;
    }

    private RestClient() {
      Retrofit restAdapter = new Retrofit.Builder().baseUrl(BASE_URL)
          .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()))
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .client(getOkHttpClient())
          .build();
      apiService = restAdapter.create(AuthorizedApi.class);
    }

    private static ObjectMapper getObjectMapper() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.setDateFormat(new SimpleDateFormat(Constants.DATEFORMAT));
      return mapper;
    }

    public static AuthorizedApi getApiService() {
      if (apiService == null) {
        restClient = new RestClient();
      }
      return apiService;
    }

    //public static FitBitAuthorizedApi getFitBitApiService() {
    //  if (fitBitApiService == null) {
    //    Interceptor fitBitHeaderAuthorizationInterceptor = new Interceptor() {
    //      @Override public okhttp3.Response intercept(Chain chain) throws IOException {
    //        okhttp3.Request request = chain.request();
    //
    //        if(TextUtils.isEmpty(request.headers().get("Authorization"))){
    //          Headers headers = request.headers()
    //              .newBuilder()
    //              .add("Authorization", "Bearer " + IntegratedAppsDBO.getAccessToken(IntegratedAppsDBO.FITBIT_APP_NAME))
    //              .build();
    //          request = request.newBuilder().headers(headers).build();
    //        }
    //        okhttp3.Response response = chain.proceed(request);
    //        int code = response.code();
    //        return response;
    //      }
    //    };
    //
    //    OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
    //    okClientBuilder.addInterceptor(fitBitHeaderAuthorizationInterceptor);
    //    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    //    httpLoggingInterceptor.setLevel(
    //        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
    //    okClientBuilder.addInterceptor(httpLoggingInterceptor);
    //    okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
    //    okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
    //    okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
    //
    //    fitBitRestAdapter = new Retrofit.Builder().baseUrl(FITBIT_BASE_URL)
    //        .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()))
    //        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    //        .client(okClientBuilder.build())
    //        .build();
    //    fitBitApiService= fitBitRestAdapter.create(FitBitAuthorizedApi.class);
    //  }
    //  return fitBitApiService;
    //}

    public static Retrofit getFitBitRestAdapter(){
      if(fitBitRestAdapter==null){
        //getFitBitApiService();
      }
      return fitBitRestAdapter;
    }

    //public static WithingsAuthorizedApi getWithingsApiService() {
    //  OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
    //  okClientBuilder.addInterceptor(new Oauth1SigningInterceptor.Builder()
    //      .consumerKey(BuildConfig.WITHINGS_API_KEY)
    //      .consumerSecret(BuildConfig.WITHINGS_API_SECRET)
    //      .accessToken(IntegratedAppsDBO.getAccessToken(IntegratedAppsDBO.WITHINGS_APP_NAME))
    //      .accessSecret(IntegratedAppsDBO.getAccessSecret(IntegratedAppsDBO.WITHINGS_APP_NAME))
    //      .userid(IntegratedAppsDBO.getAppUserId(IntegratedAppsDBO.WITHINGS_APP_NAME))
    //      .build());
    //
    //  HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    //  httpLoggingInterceptor.setLevel(
    //      BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    //  okClientBuilder.addInterceptor(httpLoggingInterceptor);
    //  okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
    //  okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
    //  okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
    //
    //  if (withingsAuthorizedApi == null) {
    //    Retrofit withingsRestAdapter = new Retrofit.Builder().baseUrl(WITHINGS_BASE_URL)
    //        .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()))
    //        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    //        .client(okClientBuilder.build())
    //        .build();
    //    withingsAuthorizedApi = withingsRestAdapter.create(WithingsAuthorizedApi.class);
    //  }
    //  return withingsAuthorizedApi;
    //}

    private OkHttpClient getOkHttpClient() {
      OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
      okClientBuilder.addInterceptor(headerAuthorizationInterceptor);
      HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
      httpLoggingInterceptor.setLevel(
          BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE);
      okClientBuilder.addInterceptor(httpLoggingInterceptor);
      okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
      okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
      okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
      return okClientBuilder.build();
    }

    private Interceptor headerAuthorizationInterceptor = new Interceptor() {
      @Override public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Request request = chain.request();
        Headers headers = request.headers()
            .newBuilder()
            .add("Authorization", "Token token=" + AppSettings.getAuthToken())
            .add("application-version", BuildConfig.VERSION_NAME)
            .add("platform", "android")
            .build();
        request = request.newBuilder().headers(headers).build();
        okhttp3.Response response = chain.proceed(request);
        int code = response.code();
        //if (code == TOKEN_EXPIRED || code == APP_VERSION_MISMATCH) {
        //  if (AppSettings.getCurrentUser() != 0
        //      && AppSettings.getUserLoggedInStatus()) {
        //    new AccountPresenter().logout();
        //    Intent navigateIntent = new Intent(App.getAppContext(), SignInActivity.class);
        //    navigateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //    navigateIntent.putExtra("startsignin", true);
        //    navigateIntent.putExtra("error_code", code);
        //    App.getAppContext().startActivity(navigateIntent);
        //  }
        //}
        return response;
      }
    };

  }

