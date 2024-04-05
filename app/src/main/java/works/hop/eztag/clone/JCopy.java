package works.hop.eztag.clone;

import com.google.gson.Gson;
import works.hop.eztag.parser.JElement;

public class JCopy {

    static final Gson gson = new Gson();

    public static <T> T cloneObject(Object obj, Class<T> target) {
        String json = gson.toJson(obj);
        return gson.fromJson(json, target);
    }
}
