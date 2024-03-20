package works.hop.web.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Choice;
import works.hop.game.model.Game;
import works.hop.game.model.Player;
import works.hop.web.service.IChoiceService;
import works.hop.web.service.IResult;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component("CreateChoice")
@RequiredArgsConstructor
public class CreateChoice extends ReqHandler {

    final Gson gson;
    final IChoiceService choiceService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Player>() {
            }.getType();
            Choice choice = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            IResult<Choice> newChoice = choiceService.addChoice(choice);
            return gson.toJson(newChoice);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
