package works.hop.web.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface IValidator<T> {

    default Map<String, String> validate(@Valid T model) {
        Set<ConstraintViolation<T>> violations = validator().validate(model);
        if (!violations.isEmpty()) {
            return violations.stream().map(v -> new String[]{v.getPropertyPath().toString(), v.getMessage()})
                    .collect(Collectors.toMap(v -> v[0], v -> v[1]));
        }
        return Collections.emptyMap();
    }

    Validator validator();
}
