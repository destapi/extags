package works.hop.web.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Question;
import works.hop.web.service.IQuestionService;
import works.hop.web.service.IResult;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component("CreateQuestion")
@RequiredArgsConstructor
public class CreateQuestion extends ReqHandler {

    final Gson gson;
    final IQuestionService questionService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Question>() {
            }.getType();
            Question question = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            IResult<Question> newQuestion = questionService.createQuestion(question);
            return gson.toJson(newQuestion);
        } catch (IOException e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
