package works.hop.web.service.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.Clue;
import works.hop.game.repository.ClueRepo;
import works.hop.web.service.IClueService;
import works.hop.web.service.IResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClueService implements IClueService {

    final ClueRepo clueRepo;
    final Validator validator;

    @Override
    public Validator validator() {
        return this.validator;
    }

    @Override
    public IResult<List<Clue>> questionClues(long questionId) {
        IResult<List<Clue>> result = new Result<>();
        try {
            result.data(clueRepo.getByRef(questionId));
        } catch (Exception e) {
            result.errors(Map.of("questionClues", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Clue> addClue(Clue clues) {
        IResult<Clue> result = new Result<>();
        try {
            Map<String, String> violations = validate(clues);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(clueRepo.createClue(clues));
        } catch (Exception e) {
            result.errors(Map.of("addClue", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Clue> updateClue(Clue clues) {
        IResult<Clue> result = new Result<>();
        try {
            Map<String, String> violations = validate(clues);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(clueRepo.updateClue(clues));
        } catch (Exception e) {
            result.errors(Map.of("updateClue", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> removeClue(int ordinal, long questionRef) {
        IResult<Void> result = new Result<>();
        try {
            clueRepo.removeClue(ordinal, questionRef);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("removeClue", e.getMessage()));
        }
        return result;
    }
}
