package works.hop.web.service.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.Choice;
import works.hop.game.repository.ChoiceRepo;
import works.hop.web.service.IChoiceService;
import works.hop.web.service.IResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChoiceService implements IChoiceService {

    final ChoiceRepo choiceRepo;
    final Validator validator;

    @Override
    public Validator validator() {
        return this.validator;
    }

    @Override
    public IResult<List<Choice>> questionChoices(long questionId) {
        IResult<List<Choice>> result = new Result<>();
        try {
            result.data(choiceRepo.getByRef(questionId));
        } catch (Exception e) {
            result.errors(Map.of("questionChoices", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Choice> addChoice(Choice choice) {
        IResult<Choice> result = new Result<>();
        try {
            Map<String, String> violations = validate(choice);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(choiceRepo.createChoice(choice));
        } catch (Exception e) {
            result.errors(Map.of("addChoice", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Choice> updateChoice(Choice choice) {
        IResult<Choice> result = new Result<>();
        try {
            Map<String, String> violations = validate(choice);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(choiceRepo.updateChoice(choice));
        } catch (Exception e) {
            result.errors(Map.of("updateChoice", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> removeChoice(int ordinal, long questionRef) {
        IResult<Void> result = new Result<>();
        try {
            choiceRepo.removeChoice(ordinal, questionRef);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("removeChoice", e.getMessage()));
        }
        return result;
    }
}
