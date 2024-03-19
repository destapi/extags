package works.hop.web.service;

import works.hop.web.service.impl.Errors;

import java.util.Map;

public interface IResult<T> {

    T data();

    void data(T data);

    Errors errors();

    void errors(Errors errors);

    default void errors(Map<String, String> errors) {
        Errors errs = new Errors();
        errs.putAll(errors);
        this.errors(errs);
    }
}
