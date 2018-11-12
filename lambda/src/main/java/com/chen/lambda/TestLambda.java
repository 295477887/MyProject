package com.chen.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author: ChenJie
 * @date 2018/8/22
 */
public class TestLambda {
    @Test
    public void testEasy(){
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer","Roger Federer",
                "Andy Murray","Tomas Berdych",
                "Juan Martin Del Potro"};
        List<String> players =  Arrays.asList(atp);

        // 以前的循环方式
//        for (String player : players) {
//            System.out.print(player + "; ");
//        }
        players.forEach(
                (play) -> System.out.println(play)
        );
    }
    
}
