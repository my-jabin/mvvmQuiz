package com.jiujiu.question;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiujiu.question.data.AppPreference;
import com.jiujiu.question.data.model.CategoryEntity;
import com.jiujiu.question.data.remote.RemoteDataSource;
import com.jiujiu.question.util.AppConstant;
import com.jiujiu.question.util.Util;

import org.hamcrest.core.IsEqual;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.reactivex.schedulers.Schedulers;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RemoteDataSourceTest {
    private static final String TAG = "RemoteDataSourceTest";

    @Mock
    Context context ;

    AppPreference appPreference ;

    RemoteDataSource remoteDataSource;

    @Before
    public void setup(){
        appPreference = new AppPreference(context, AppConstant.PREFERENCENAME);
        remoteDataSource = new RemoteDataSource(appPreference);
    }

    @Test
    public void testloadToken() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        remoteDataSource.loadToken().subscribeOn(Schedulers.io())
                .subscribe((apiTokenResponse, throwable) -> {
                    assertThat(apiTokenResponse.getResponseCode(),equalTo(0));
                    System.out.println("token = "+apiTokenResponse.getToken());
                    latch.countDown();
                });
        latch.await();
    }



    @Test
    public void generateCategory(){

        String[] names = {"General Knowledge","Entertainment: Books","Entertainment: Film","Entertainment: Music","Entertainment: Musicals & Theatres",
                "Entertainment: Television","Entertainment: Video Games","Entertainment: Board Games","Science & Nature","Science: Computers","Science: Mathematics",
                "Mythology","Sports","Geography","History","Politics","Art","Celebrities","Animals","Vehicles","Entertainment: Comics","Science: Gadgets","Entertainment: Japanese Anime & Manga","Entertainment: Cartoon & Animations"};

        int[] count = new int[]{208,69,179,270,22,127,727,45,168,131,41,42,75,217,227,47,20,42,54,64,34,18,138,67};

        assertThat(names.length,equalTo(count.length));

//        CategoryEntity any = new CategoryEntity();
//        any.id = 0;
//        any.name = "Any";
//        any.totalNumberQuestions=3032;
//        ArrayList<CategoryEntity> categoryEntities = new ArrayList<>();
//        for(int i = 0 ; i < names.length; i++){
//            CategoryEntity entity = new CategoryEntity();
//            entity.id = i+9;
//            entity.name = names[i];
//            entity.totalNumberQuestions = count[i];
//            categoryEntities.add(entity);
//        }
//
//        Gson gson = new Gson();
//        String result = gson.toJson(categoryEntities);
//        System.out.println(result);

    }

    @Test
    public void testReadCategory() throws IOException {
        Gson gson  = new Gson();
        Type type = new TypeToken<List<CategoryEntity>>() {
        }.getType();
        List<CategoryEntity> categoryEntities =  gson.fromJson( Util.loadJSONFromAsset(context,"categories.json"), type);
        System.out.println(categoryEntities.size());
    }

}
