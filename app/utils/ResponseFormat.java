package utils;

import java.util.*;

public class ResponseFormat {


    public static Map<String, Object> format(List<?> l, String entityName) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put(entityName, l);
        m.put("limit", null);
        m.put("offset", null);
        return m;
    }


}
