package works.hop.web.handler.clue;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Clue;
import works.hop.web.service.IClueService;
import works.hop.web.service.IResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionClues extends ReqHandler {

    final Gson gson;
    final IClueService clueService;
    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String questionId = request.getParameter("questionId");
            IResult<List<Clue>> currentClues = clueService.questionClues(Long.parseLong(questionId));
            return gson.toJson(currentClues);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
