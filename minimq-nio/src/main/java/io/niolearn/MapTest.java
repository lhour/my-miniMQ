package io.niolearn;

import java.util.HashMap;

public class MapTest {
    public static void main(String[] args) {
        long now = System.currentTimeMillis();

        HashMap<String, Integer> map = new HashMap<>();

        for(long i = 0; i < 1000000000; i ++){
            map.put("topic" + i, 100000);
        }

        System.out.println(System.currentTimeMillis() - now);
    }
}
