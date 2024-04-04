//package com.example.cocoin.common.utils;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Slf4j
//public class DataUtils {
//    public static <T> T getData(List<Object> list, Class<T> obj) {
//
//        for(Object data : list) {
//            if( data.getClass().isAssignableFrom(ArrayList.class) ) {
//                List<Object> _list = (ArrayList)data;
//
//                if( _list.size() > 0 && !obj.isAssignableFrom( Collections.class) && _list.get(0) != null && obj.isAssignableFrom( _list.get(0).getClass() ) ){
//                    return (T)_list.get(0);
//                }
//            }
//        }
//        try {
//            return obj.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static <T> List<T> getList(List<Object> list, Class<T> obj) {
//        if(list != null){
//            for(Object data : list) {
//                try{
//                    if( data.getClass().isAssignableFrom(ArrayList.class) ) {
//                        List<Object> _list = (ArrayList)data;
//                        if( _list.size() > 0 && _list.get(0) != null && obj.isAssignableFrom( _list.get(0).getClass() ) ){
//                            return (List<T>)_list;
//                        }
//                    }
//                }catch (Exception ex){
//                    log.debug(ex.getMessage());
//                }
//            }
//        }
//
//        return new ArrayList<>();
//    }
//}
