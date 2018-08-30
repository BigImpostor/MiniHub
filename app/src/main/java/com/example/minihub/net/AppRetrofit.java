package com.example.minihub.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.minihub.AppConfig;
import com.example.minihub.MyApplication;
import com.example.minihub.utils.Utils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.franmontiel.persistentcookiejar.persistence.SerializableCookie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 枚举类单例
 */

public enum AppRetrofit {

    INSTANCE;

    private static long CACHE_SIZE = 10 * 1024 * 1024;

    public Retrofit getRetrofit(Context context){
        /**
         * 一个打印Ohttp的请求和响应信息的拦截器
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
               Log.e(HttpLoggingInterceptor.class.getName(),message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * 设置缓存拦截器
         */
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(Utils.isNetworkConnected())
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();

                Response response = chain.proceed(request);
                if (Utils.isNetworkConnected()){
                    int maxAge = 0;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                }else {
                    int maxStale = 60 * 60 * 24;
                    response.newBuilder()
                            .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };


        File cacheFile = new File(MyApplication.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile,CACHE_SIZE);


        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new EasyCookiePersistor(context));
        
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .cookieJar(cookieJar)
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .build();




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())             //Gson解析区分大小写
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public boolean isHasCookie(Context context){
        return new EasyCookiePersistor(context).isHasCookie();
    }


    public class EasyCookiePersistor implements CookiePersistor{


        private final SharedPreferences sharedPreferences;

        public EasyCookiePersistor(Context context) {
            sharedPreferences = context.getSharedPreferences("Cookie",Context.MODE_PRIVATE);
        }

        @Override
        public List<Cookie> loadAll() {
            List<Cookie> cookies = new ArrayList<>(sharedPreferences.getAll().size());

            for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                String serializedCookie = (String) entry.getValue();
                Cookie cookie = new SerializableCookie().decode(serializedCookie);
                if (cookie != null) {
                    cookies.add(cookie);
                }
            }
            return cookies;
        }

        @Override
        public void saveAll(Collection<Cookie> cookies) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (Cookie cookie : cookies) {
                editor.putString(createCookieKey(cookie), new SerializableCookie().encode(cookie));
            }
            editor.commit();
        }

        @Override
        public void removeAll(Collection<Cookie> cookies) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (Cookie cookie : cookies) {
                editor.remove(createCookieKey(cookie));
            }
            editor.commit();
        }

        private String createCookieKey(Cookie cookie) {
            return (cookie.secure() ? "https" : "http") + "://" + cookie.domain() + cookie.path() + "|" + cookie.name();
        }

        public boolean isHasCookie(){
            List<Cookie> cookies = loadAll();
            for(Cookie c:cookies){
                String str = sharedPreferences.getString(createCookieKey(c),"");
                if(TextUtils.isEmpty(str))
                    return false;
            }
            return true;
        }

        @Override
        public void clear() {
            sharedPreferences.edit().clear().commit();
        }
    }

}
