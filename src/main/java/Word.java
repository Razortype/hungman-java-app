import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class Word {

    String requestedWord;
    String displayWord;
    String wordMeaning;
    String wordGenre;
    List<Character> forbiddenCharacters = new ArrayList<>();

    public void generateWord(HttpAPI httpAPI) {
        String word = httpAPI.generateRandomWord();
        this.requestedWord = word;
        this.displayWord = "_".repeat(word.length());
    }

    void printForbiddenList() {
        System.out.println("Forbidden : " + this.forbiddenCharacters);
    }

    int checkLetter(char letter) {
        String containLetter = String.valueOf(letter);
        if (displayWord.contains(containLetter) || this.forbiddenCharacters.contains(letter)) {
            return -1;
        } else if (requestedWord.contains(containLetter)) {
            replaceExistsLetter(letter);
            return 1;
        } else {
            this.addLetterToForbidden(letter);
            return 2;
        }
    }

    void addLetterToForbidden(char letter) {
        this.forbiddenCharacters.add(letter);
    }

    void replaceExistsLetter(char letter) {
        List<Character> newDisplayCharacters = new ArrayList<>();
        for (int i=0; i<this.displayWord.length(); i++) {
            if (letter == this.requestedWord.charAt(i)) {
                newDisplayCharacters.add(letter);
            } else {
                newDisplayCharacters.add(this.displayWord.charAt(i));
            }
        }
        String newDisplayWord = newDisplayCharacters.stream().map(String::valueOf).collect(Collectors.joining());
        this.displayWord = newDisplayWord;
    }

    int checkGameStatus(Boolean hunged) {
        if (!this.displayWord.contains("_")) {
            return 1;
        } else if (hunged) {
            return 2;
        } else {
            return 0;
        }
    }

}
