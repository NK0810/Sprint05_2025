package constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static class TimeOutVariable {
        public static final int EXPLICIT_WAIT = 10000;
        public static final int IMPLICIT_WAIT = 10000;
    }

    public static class EditAddressTestData {
        public static final String NAME = "Шмек";
        public static final String SURNAME = "Мельник";
        public static final String STREET = "Травнева";
        public static final String HOUSE_NUMBER = "8";
        public static final String POST_CODE = "10987";
        public static final String CITY = "Харків";
        public static final String PHONE_NUMBER = "0967693586";

        public static String buildAddress(String... fieldNames) {
            List<String> values = new ArrayList<>();

            for (String name : fieldNames) {
                try {
                    Field field = Constant.EditAddressTestData.class.getField(name);
                    values.add((String) field.get(null));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException("Cannot access field: " + name, e);
                }
            }

            return String.join(" ", values);
        }
    }
}