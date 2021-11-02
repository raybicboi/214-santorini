import java.io.IOException;
import java.util.Map;

import God.CardLogic;
import God.Cards.Athena;
import God.Cards.Demeter;
import God.Cards.Minotaur;
import God.Cards.Pan;
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
        // Option to hardcode gamelogic/god cards (game, player object)
        // GameLogic = base logic, can also choose Pan, Demeter, Minotaur, or Athena
//        this.gl0 = new GameLogic(game, game.getP0());
//        this.gl1 = new GameLogic(game, game.getP1());

        // Option to hardcode the drops-pregame (x coord, y coord, worker id, player object)
//        this.game.dropWorker(0, 0, 0, this.game.getP0());
//        this.game.dropWorker(1, 3, 1, this.game.getP0());
//        this.game.dropWorker(4, 4, 0, this.game.getP1());
//        this.game.dropWorker(3, 2, 1, this.game.getP1());

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
                gl0 = null;
                gl1 = null;
                // hardcode game logic on new game
//                this.gl0 = new GameLogic(game, game.getP0());
//                this.gl1 = new GameLogic(game, game.getP1());

                // hard dropping workers on new game
//                this.game.dropWorker(0, 0, 0, this.game.getP0());
//                this.game.dropWorker(1, 3, 1, this.game.getP0());
//                this.game.dropWorker(4, 4, 0, this.game.getP1());
//                this.game.dropWorker(3, 2, 1, this.game.getP1());

            } else if (uri.equals("/base")) {
                if (gl0 == null) gl0 = new GameLogic(game, game.getP0());
                else if (gl1 == null) gl1 = new GameLogic(game, game.getP1());
                System.out.println("Created a base game");
            } else if (uri.equals("/athena")) {
                if (gl0 == null) gl0 = new Athena(game, game.getP0());
                else if (gl1 == null) gl1 = new Athena(game, game.getP1());
                System.out.println("Created Athena");
            } else if (uri.equals("/demeter")) {
                if (gl0 == null) gl0 = new Demeter(game, game.getP0());
                else if (gl1 == null) gl1 = new Demeter(game, game.getP1());
                System.out.println("Created Demeter");
            } else if (uri.equals("/minotaur")) {
                if (gl0 == null) gl0 = new Minotaur(game, game.getP0());
                else if (gl1 == null) gl1 = new Minotaur(game, game.getP1());
                System.out.println("Created Minotaur");
            } else if (uri.equals("/pan")) {
                if (gl0 == null) gl0 = new Pan(game, game.getP0());
                else if (gl1 == null) gl1 = new Pan(game, game.getP1());
                System.out.println("Created Pan");
            } else if (uri.equals("/play")) {
                Player turn = this.game.getCurrentPlayer();
                int x = Integer.parseInt(params.get("x"));
                int y = Integer.parseInt(params.get("y"));
                int id = Integer.parseInt(params.get("id"));

                // testing if all workers have been dropped
                boolean tag1 = true;
                for (Worker w: game.getP0().getWorkerList()) {
                    if (null == w.getCurrentTile()) tag1 = false;
                }
                boolean tag2 = true;
                for (Worker w: game.getP1().getWorkerList()) {
                    if (null == w.getCurrentTile()) tag2 = false;
                }
                // init dropping of workers
                if (!tag1) {
                    if (game.dropWorker(x, y, id, game.getP0())) System.out.println("Dropped p0 worker");
                }
                else if (!tag2) {
                    if (game.dropWorker(x, y, id, game.getP1())) System.out.println("Dropped p1 worker");
                }
                // move and build
                else if (turn == this.game.getP0()) {
                    if (this.gl0.getCanMove()) gl0.relocateWorker(x, y, id);
                    else if (this.gl0.getCanBuild()) gl0.buildTower(x, y, id);
                } else if (turn == this.game.getP1()) {
                    if (this.gl1.getCanMove()) gl1.relocateWorker(x, y, id);
                    else if (this.gl1.getCanBuild()) gl1.buildTower(x, y, id);
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