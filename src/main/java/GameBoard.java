import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class GameBoard {

    Boolean playable = true,
            win = false,
            changeToNextFrame = false;
    Word word;
    Hungman hungman;
    Scanner scan;
    HttpAPI httpAPI;
    String lastHungmanProcess;

    void initialise() throws IOException, ParseException {
        this.word = new Word();
        this.hungman = new Hungman();
        this.scan = new Scanner(System.in);
        this.httpAPI = new HttpAPI();
        httpAPI.startFetching();
        word.generateWord(this.httpAPI);
        httpAPI.generateWordMeaning(word);
        lastHungmanProcess = hungman.nextPattern();
    }

    void modify(char givenLetter) {
        switch (word.checkLetter(givenLetter)) {
            case -1:
                hungman.printAlreadyExists();
            case 1:
                this.changeToNextFrame = false;
                break;
            case 2:
                this.changeToNextFrame = true;
                break;
        }
    }

    void result() {
        switch (word.checkGameStatus(hungman.checkHanged())) {
            case 0:
                break;
            case 1:
                win = true;
            case 2:
                playable = false;
                break;
        }
    }

    void finish() {
        if (this.win) {
            hungman.printWon();
        } else {
            hungman.printGameOver();
        }
        hungman.centeredPanel(word.requestedWord);
    }
    void start() {

        char givenLetter;
        while (this.playable) {

            if (this.changeToNextFrame) {
                this.lastHungmanProcess = hungman.nextPattern();
            }
            System.out.println(this.lastHungmanProcess);

            System.out.println("Word      : " + word.displayWord);
            System.out.println("Meaning   : " + word.wordMeaning);
            System.out.println("Genre     : " + word.wordGenre);

            word.printForbiddenList();

            System.out.print("Enter a letter : ");
            givenLetter = this.scan.next().charAt(0);

            this.modify(givenLetter);

            this.result();

        }

        this.finish();

    }

}
