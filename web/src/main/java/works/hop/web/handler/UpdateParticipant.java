package works.hop.web.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.eztag.server.handler.AbstractReqHandler;
import works.hop.game.model.Participant;
import works.hop.game.repository.ParticipantRepo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Service("createParticipant")
@RequiredArgsConstructor
public class UpdateParticipant extends AbstractReqHandler {

    final Gson gson;
    final ParticipantRepo participantRepo;

    @Override
    public String handle(HttpServletRequest req, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Participant>() {
            }.getType();
            Participant participantInfo = gson.fromJson(
                    new InputStreamReader(req.getInputStream()), mapType);
            return gson.toJson(participantRepo.updatePlayer(participantInfo));
        } catch (IOException e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
