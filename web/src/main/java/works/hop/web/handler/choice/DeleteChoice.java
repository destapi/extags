package works.hop.web.handler.choice;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.web.service.IChoiceService;

import java.util.Objects;

@Component("RemoveChoice")
@RequiredArgsConstructor
public class DeleteChoice extends ReqHandler {

    final Gson gson;
    final IChoiceService choiceService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            int ordinal = Integer.parseInt(Objects.requireNonNull(param("ordinal"), "ordinal is a required path parameter"));
            long questionId = Long.parseLong(Objects.requireNonNull(param("questionId"), "question id is a required path parameter"));
            return gson.toJson(choiceService.removeChoice(ordinal, questionId));
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
