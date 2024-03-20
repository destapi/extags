package works.hop.web.handler.clue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Clue;
import works.hop.game.model.Game;
import works.hop.web.service.IClueService;
import works.hop.web.service.IResult;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component("UpdateClue")
@RequiredArgsConstructor
public class UpdateClue extends ReqHandler {

    final Gson gson;
    final IClueService clueService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Game>() {
            }.getType();
            Clue clue = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            IResult<Clue> updatedGame = clueService.updateClue(clue);
            return gson.toJson(updatedGame);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
