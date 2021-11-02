import java.io.IOException;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import fi.iki.elonen.NanoHTTPD;
import GameSystem.Game;
import GameSystem.GameLogic;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;
    private GameLogic gl0;
    private GameLogic gl1;
    private Template template;

    public App() throws IOException {
        super(8080);

        this.game = new Game();
        this.gl0 = new GameLogic(game, game.getP0());
        this.gl1 = new GameLogic(game, game.getP1());

        Handlebars handlebars = new Handlebars();
        this.template = handlebars.compile("main");

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            String uri = session.getUri();
            Map<String, String> params = session.getParms();
            if (uri.equals("/newgame")) {
                this.game = new Game();
            }
//            else if (uri.equals("/play")) {
//                this.game = this.game.play(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
//            }
            // Extract the view-specific data from the game and apply it to the template.
            UIProxy gameplay = UIProxy.forGame(this.gl0, this.gl1);
            String HTML = this.template.apply(gameplay);
            return newFixedLengthResponse(HTML);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}