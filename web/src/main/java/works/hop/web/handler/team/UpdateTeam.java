package works.hop.web.handler.choice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Choice;
import works.hop.game.model.Game;
import works.hop.web.service.IChoiceService;
import works.hop.web.service.IResult;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component("UpdateChoice")
@RequiredArgsConstructor
public class UpdateChoice extends ReqHandler {

    final Gson gson;
    final IChoiceService choiceService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Game>() {
            }.getType();
            Choice choice = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            IResult<Choice> updatedGame = choiceService.updateChoice(choice);
            return gson.toJson(updatedGame);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
