package works.hop.web.service;

import jakarta.validation.Valid;

import java.util.Map;

public interface Validator<T> {

    Map<String, String> validate(@Valid T model);
}
