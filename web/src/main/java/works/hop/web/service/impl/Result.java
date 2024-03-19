package works.hop.web.service.impl;

import works.hop.web.service.IResult;

public class Result<T> implements IResult<T> {

    T data;
    Errors errors;

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(Errors errors) {
        super();
        this.errors = errors;
    }

    @Override
    public T data() {
        return this.data;
    }

    @Override
    public void data(T data) {
        this.data = data;
    }

    @Override
    public Errors errors() {
        return this.errors;
    }

    @Override
    public void errors(Errors errors) {
        this.errors = errors;
    }
}
