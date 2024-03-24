package works.hop.web.handler.choice;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Choice;
import works.hop.game.model.Player;
import works.hop.web.service.IChoiceService;
import works.hop.web.service.IQuestionService;
import works.hop.web.service.IResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionChoices extends ReqHandler {

    final Gson gson;
    final IChoiceService choiceService;
    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String questionId = request.getParameter("questionId");
            IResult<List<Choice>> currentChoices = choiceService.questionChoices(Long.parseLong(questionId));
            return gson.toJson(currentChoices);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
