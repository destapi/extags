package works.hop.web.service;

import java.util.Map;

public interface IResult<T> {

    T data();

    void data(T data);

    Map<String, String> errors();

    void errors(Map<String, String> errors);
}
