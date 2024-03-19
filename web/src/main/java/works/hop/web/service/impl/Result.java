package works.hop.web.service.impl;

import works.hop.web.service.IResult;

import java.util.Map;

public class Result<T> implements IResult<T> {

    T data;
    Map<String, String> errors;

    @Override
    public T data() {
        return this.data;
    }

    @Override
    public void data(T data) {
        this.data = data;
    }

    @Override
    public Map<String, String> errors() {
        return this.errors;
    }

    @Override
    public void errors(Map<String, String> errors) {
        this.errors = errors;
    }
}
