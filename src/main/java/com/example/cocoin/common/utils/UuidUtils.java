package com.example.cocoin.common.utils;

import java.util.UUID;

public class UuidUtils {
    public static UUID getUuid3(String name){
        UUID uuid3 = UUID.nameUUIDFromBytes(name.getBytes());
        return uuid3;
    }
    public static UUID getUuid4(){
        UUID uuid4 = UUID.randomUUID();
        return uuid4;
    }
}
