import java.io.IOException;
import java.util.Map;

import God.CardLogic;
import Player.Player;
import Player.Worker;
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
    private CardLogic gl0;
    private CardLogic gl1;
    private Template template;

    public App() throws IOException {
        super(8080);

        this.game = new Game();
        this.gl0 = new GameLogic(game, game.getP0());
        this.gl1 = new GameLogic(game, game.getP1());

        // for now, I am hardcoding the initial drops
        // we can change that here
        this.game.dropWorker(0, 0, 0, this.game.getP0());
        this.game.dropWorker(1, 3, 1, this.game.getP0());
        this.game.dropWorker(4, 4, 0, this.game.getP1());
        this.game.dropWorker(3, 2, 1, this.game.getP1());

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
//            Map<String, String> paramWorker = session.getParms();
            if (uri.equals("/newgame")) {
                this.game = new Game();
                this.gl0 = new GameLogic(game, game.getP0());
                this.gl1 = new GameLogic(game, game.getP1()); // have to re-hardcode initial drop tiles

                this.game.dropWorker(0, 0, 0, this.game.getP0());
                this.game.dropWorker(1, 3, 1, this.game.getP0());
                this.game.dropWorker(4, 4, 0, this.game.getP1());
                this.game.dropWorker(3, 2, 1, this.game.getP1());
            }
            else if (uri.equals("/play")) {
                Player turn = this.game.getCurrentPlayer();
                int x = Integer.parseInt(params.get("x"));
                int y = Integer.parseInt(params.get("y"));
//                int id = Integer.parseInt(paramWorker.get("workerId"));
//                int id2 = Integer.parseInt(paramWorker.get("workerId"));

                // init dropping of workers
//                boolean tag1 = false;
//                for (Worker w: game.getP0().getWorkerList()) {
//                    if (null != w.getCurrentTile()) tag1 = true;
//                }
//                if (!tag1) {
//                    game.dropWorker(x, y, id, game.getP0());
//                }
//
//                boolean tag2 = false;
//                for (Worker w: game.getP1().getWorkerList()) {
//                    if (null != w.getCurrentTile()) tag2 = true;
//                }
//                if (!tag2) {
//                    game.dropWorker(x, y, id, game.getP1());
//                }

                // move and build
                if (turn == this.game.getP0()) {
                    if (this.gl0.getCanMove()) gl0.relocateWorker(x, y, 0);
                    else if (this.gl0.getCanBuild()) gl0.buildTower(x, y, 0);
                } else if (turn == this.game.getP1()) {
                    if (this.gl1.getCanMove()) gl1.relocateWorker(x, y, 0);
                    else if (this.gl1.getCanBuild()) gl1.buildTower(x, y, 0);
                }
            }
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