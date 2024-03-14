package works.hop.eztag.server.router;

import java.util.LinkedHashMap;

public class PathParams extends LinkedHashMap<String, String> {

    public String param(String name){
        return get(name);
    }
}
