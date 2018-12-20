package com.jiujiu.question.data.remote;

import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.common.Priority;
import com.jiujiu.question.data.AppPreference;
import com.jiujiu.question.util.AppConstant;
import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class RemoteDataSource {
    private static final String TAG = "RemoteDataSource";
    private AppPreference mPrference;

    @Inject
    public RemoteDataSource(AppPreference appPreference) {
        mPrference = appPreference;
    }

    public Single<ApiTokenResponse> loadToken() {
        return Rx2AndroidNetworking.get("https://opentdb.com/api_token.php?command=request")
                .setPriority(Priority.HIGH)
                .build()
                .getObjectSingle(ApiTokenResponse.class)
                .subscribeOn(Schedulers.io());
    }

    private Single<String> getToken() {
        if (TextUtils.isEmpty(mPrference.getToken())) {
            return loadToken().flatMap(apiTokenResponse -> {
                mPrference.setToken(apiTokenResponse.getToken());
                return Single.just(apiTokenResponse.getToken());
            });
        } else {
            return Single.just(mPrference.getToken()).subscribeOn(Schedulers.io());
        }
    }

    public Single<ApiResponse> loadQuestions(int categoryID, String difficulty, String type, int amount) {
       return getToken()
                .flatMap(token -> {
                    Rx2ANRequest request = buildRequest(categoryID, difficulty, type, amount, token);
                    Log.d(TAG, "loadQuestions: " + request.getUrl());
                    return request.getObjectSingle(ApiResponse.class);
                })
                .flatMap(apiResponse -> {
                    if (apiResponse.getResponseCode() == ApiResponse.TOKEN_EMPTY || apiResponse.getResponseCode() == apiResponse.TOKEN_NOT_FOUND) {
                        return loadToken().flatMap(apiTokenResponse -> {
                            mPrference.setToken(apiTokenResponse.getToken());
                            return Single.just(apiTokenResponse.getToken());
                        }).flatMap(token -> {
                            Rx2ANRequest request = buildRequest(categoryID, difficulty, type, amount, token);
                            Log.d(TAG, "loadQuestions: " + request.getUrl());
                            return request.getObjectSingle(ApiResponse.class);
                        });
                    }else{
                        return Single.just(apiResponse);
                    }
                });
    }


    private Rx2ANRequest buildRequest(int categoryID, String difficulty, String type, int amount, String token) {
        Rx2ANRequest.GetRequestBuilder getRequestBuilder = new Rx2ANRequest.GetRequestBuilder(AppConstant.API_QUESTION_REQUEST);
        if (amount > 0) {
            getRequestBuilder.addQueryParameter("amount", String.valueOf(amount));
        }
        if (categoryID > 0) {
            getRequestBuilder.addQueryParameter("category", String.valueOf(categoryID));
        }
        if (!TextUtils.isEmpty(difficulty)) {
            getRequestBuilder.addQueryParameter("difficulty", difficulty.toLowerCase());
        }
        if (!TextUtils.isEmpty(type)) {
            getRequestBuilder.addQueryParameter("type", type);
        }
        getRequestBuilder.addQueryParameter("token", token);
        getRequestBuilder.addQueryParameter("encode", "url3986");
        getRequestBuilder.setPriority(Priority.HIGH);
        return getRequestBuilder.build();
    }

//    public Single<ApiCategoryResponse> loadCatetories() {
//        return Rx2AndroidNetworking.get(AppConstant.API_CATEGORY)
//                .setPriority(Priority.HIGH)
//                .build()
//                .getObjectSingle(ApiCategoryResponse.class)
//                .subscribeOn(Schedulers.io());
//    }
//
//    public Observable<String> loadCountGlobal(){
//        return Rx2AndroidNetworking.get(AppConstant.API_COUNT_GLOBAL)
//                .setPriority(Priority.HIGH)
//                .build()
//                .getStringObservable()
//                .subscribeOn(Schedulers.io());
//    }


}
