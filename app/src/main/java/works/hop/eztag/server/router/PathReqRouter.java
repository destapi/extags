package works.hop.eztag.server.router;

import works.hop.eztag.server.handler.IReqHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathReqRouter implements ReqRouter {

    String pathParamRegex = "(\\{.+?\\})";
    String paramNameRegex = "\\{(.+?)\\}";
    Pattern storagePattern = Pattern.compile(pathParamRegex);
    Map<String, IReqHandler> pathRegexHandlers = new HashMap<>();

    @Override
    public void store(String method, String path, IReqHandler handler) {
        path = path.endsWith("/") ? path : path.concat("/");
        Matcher matcher = storagePattern.matcher(path);
        if (!matcher.find()) {
            checkDuplicatePathMatch(path);
            this.pathRegexHandlers.put(path, handler);
        } else {
            String mapKeyRegex = path.replaceAll("(\\{.+?\\})", "(.+?)").replaceAll("/", "\\\\/");
            checkDuplicatePathMatch(mapKeyRegex);
            this.pathRegexHandlers.put(mapKeyRegex, handler);
        }
    }

    @Override
    public IReqHandler fetch(String method, String path) {
        path = path.endsWith("/") ? path : path.concat("/");
        for (String pathRegex : pathRegexHandlers.keySet()) {
            IReqHandler handler = pathRegexHandlers.get(pathRegex);
            Matcher pathParamMatcher = Pattern.compile(pathRegex).matcher(path);
            Matcher paramNameMatcher = Pattern.compile(paramNameRegex).matcher(handler.path());
            if (pathParamMatcher.find()) {
                int group = 1;
                while (paramNameMatcher.find()) {
                    String paramName = paramNameMatcher.group(1);
                    String pathParam = pathParamMatcher.group(group++);
                    handler.params().put(paramName, pathParam);
                }
                return handler;
            }
        }
        throw new RuntimeException(String.format("Could not find a handler configured for the '%s' path", path));
    }

    private void checkDuplicatePathMatch(String path) {
        if (this.pathRegexHandlers.containsKey(path)) {
            throw new RuntimeException(String.format("The current path '%s' path is already matched",
                    path));
        }
    }
}
