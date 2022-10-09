import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hungman {

    String process0 = String.join("\n",
            "       ╔══════╗",
                      "       ║      ║",
                      "              ║",
                      "              ║",
                      "              ║",
                      "              ║");

    String process1 = String.join("\n",
            "       ╔══════╗",
                      "       ║      ║",
                      "      ( )     ║",
                      "              ║",
                      "              ║",
                      "              ║");

    String process2 = String.join("\n",
            "       ╔══════╗",
                      "       ║      ║",
                      "      ( )     ║",
                      "       |      ║",
                      "              ║",
                      "              ║");

    String process3 = String.join("\n",
            "       ╔══════╗",
                      "       ║      ║",
                      "      ( )     ║",
                      "      /|      ║",
                      "              ║",
                      "              ║");

    String process4 = String.join("\n",
            "       ╔══════╗",
                      "       ║      ║",
                      "      ( )     ║",
                      "      /|\\     ║",
                      "              ║",
                      "              ║");

    String process5 = String.join("\n",
            "       ╔══════╗",
                      "       ║      ║",
                      "      ( )     ║",
                      "      /|\\     ║",
                      "      /       ║",
                      "              ║");

    String process6 = String.join("\n",
            "       ╔══════╗",
                      "       ║      ║",
                      "      ( )     ║",
                      "      /|\\     ║",
                      "      / \\     ║",
                      "              ║");

    String gameWon = String.join("\n",
            "╔══════════════╗",
                      "║              ║",
                      "║    WIN <3    ║",
                      "║              ║",
                      "╚══════════════╝");
    String gameOver = String.join("\n",
            "╔══════════════╗",
                      "║              ║",
                      "║   Game Over  ║",
                      "║              ║",
                      "╚══════════════╝");

    String alreadyExists = String.join("\n",
            "╔════════════════╗",
                      "║ Already Exists ║",
                      "╚════════════════╝");

    Map<Integer, String> processes = new HashMap<>() {{
        put(1, process0);
        put(2, process1);
        put(3, process2);
        put(4, process3);
        put(5, process4);
        put(6, process5);
        put(7, process6);
    }};

    int currentPosition=1;
    int maxPosition = 7;
    String nextPattern() {
        if (currentPosition <= maxPosition) {
            String current = processes.get(currentPosition);
            currentPosition++;
            return current;
        } else {
            return null;
        }
    }

    void centeredPanel(String word) {
        String space = " ".repeat((16 - word.length()) / 2);
        ArrayList<String> lines = new ArrayList<>();
        lines.add("╔════════════════╗");
        if (word.length() % 2 == 0) {
            lines.add("║" + space + word + space + "║");
        } else {
            lines.add("║" + space + word + space + " ║");
        }
        lines.add("╚════════════════╝");
        System.out.println(String.join("\n", lines));
    }

    void clearScreen() {
       //
    }

    void printWon() {
        System.out.println(this.gameWon);
    }

    void printGameOver() {
        System.out.println(this.gameOver);
    }

    void printAlreadyExists() {
        System.out.println(this.alreadyExists);
    }

    Boolean checkHanged() {
        return currentPosition > maxPosition;
    }

}
