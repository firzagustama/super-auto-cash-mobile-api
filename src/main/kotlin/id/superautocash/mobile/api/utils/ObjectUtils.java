package id.superautocash.mobile.api.utils;

import java.lang.reflect.Field;

public class ObjectUtils {
    public static String toString(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getSimpleName());
        sb.append("[");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            sb.append(field.getName());
            sb.append("=");
            try {
                sb.append(field.get(clazz));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sb.append(";");
        }

        sb.deleteCharAt(sb.length() - 1); // remove last ';' character
        sb.append("]");
        return sb.toString();
    }
}
