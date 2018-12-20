package com.jiujiu.question;

import com.jiujiu.question.util.Util;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UtilTest {

    @Test
    public void testDiff() {
        String hard = "Hard";
        String hardQueryString = Util.matchDifffToUrlQueryString(hard);
        System.out.println(hard);
        System.out.println(hardQueryString);

        String some = "some";
        String someString = Util.matchDifffToUrlQueryString(some);
        System.out.println(some);
        System.out.println(someString);
    }

    @Test
    public void testType() {
        String some = "some";
        String someString = Util.matchTypeToUrlQueryString(some);
        System.out.println(some);
        System.out.println(someString);


        String multiple = "Multiple Choice";
        String mul = Util.matchTypeToUrlQueryString(multiple);
        System.out.println(multiple);
        System.out.println(mul);
    }

    @Test
    public void testReduce() {
        List<String> answers = new ArrayList<>();
        answers.add("sss");
        answers.add("dddd");
        answers.add("ccc");

        String result = answers.stream().reduce("", (s1, s2) -> s1 + "," + s2);
        System.out.println(result.substring(1,result.length()));
    }

}
