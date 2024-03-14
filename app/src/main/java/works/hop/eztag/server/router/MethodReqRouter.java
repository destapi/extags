package works.hop.eztag.server.router;

import works.hop.eztag.server.handler.ReqHandler;

import java.util.EnumMap;
import java.util.Map;

public class MethodReqRouter implements ReqRouter{

    public enum MethodType {POST, GET, PUT, DELETE, PATCH}

    Map<MethodType, ReqRouter> handlers = new EnumMap<>(MethodType.class);

    public MethodReqRouter(){
        for(MethodType type : MethodType.values()){
            handlers.put(type, new PathReqRouter());
        }
    }

    @Override
    public void store(String method, String path, ReqHandler handler) {
        MethodType type = MethodType.valueOf(method.toUpperCase());
        handlers.get(type).store(method, path, handler);
    }

    @Override
    public ReqHandler fetch(String method, String path) {
        MethodType type = MethodType.valueOf(method.toUpperCase());
        return handlers.get(type).fetch(method, path);
    }
}
