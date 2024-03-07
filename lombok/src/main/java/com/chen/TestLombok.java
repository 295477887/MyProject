package com.chen;

import com.chen.data.UserData;
import com.chen.setget.UserSetGet;
import org.junit.Test;

/**
 * @author:  ChenJie
 * @date 2018/8/31
 */

public class TestLombok {

    @Test
    public void testData(){
        UserData userData = new UserData();
        //set
        userData.setName("chenjie");
        userData.setMale(true);
        userData.setAge(3);
        //get
        System.out.println("name: "+ userData.getName());
        //toString
        System.out.println("userData: "+ userData);

        //equal
        UserData userData1 = new UserData();
        userData1.setName("chenjie");
        userData1.setMale(true);
        userData1.setAge(3);
        System.out.println("userData.equals(userData): "+ userData.equals(userData));
        System.out.println("userData.equals(userData1): "+ userData.equals(userData1));
        userData1.setAge(2);
        System.out.println("userData.equals(new userData1): "+ userData.equals(userData1));

        //hashcode
        System.out.println("hashCode: "+ userData.hashCode());

        String str1 = null;
        String str2 = "a";
        System.out.println(str1 == null ? str2 == null : str1.equals(str2));

    }

    @Test
    public void testSetGet(){
        UserSetGet user = new UserSetGet();
        //set
        user.setName("chenjie");
        user.setMale(true);
        user.setAge(3);
        //get
        System.out.println("name: "+ user.getName());

    }

}
