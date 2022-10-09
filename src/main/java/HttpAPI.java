import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpAPI {

    String APIUrl = "https://random-word-api.herokuapp.com/word?number=100";
    String wordAPIUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    HttpURLConnection conn;
    URL url;
    Object object;
    Random random = new Random();
    List<String> words;

    public void createConnection(String apiUrl) throws IOException {
        this.url = new URL(apiUrl);
        this.conn = (HttpURLConnection) this.url.openConnection();
        this.conn.setRequestMethod("GET");
    }

    public void requestParser() throws IOException, ParseException {
        int responseCode = this.conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            this.startJsonParser();
        }
    }

    public void requestParserMeaning(Word word) throws IOException, ParseException {
        int responseCode = this.conn.getResponseCode();

        if (responseCode != 200) {
            System.out.println("ERROR : "+word.requestedWord);
            word.generateWord(this);
            this.createConnection(this.wordAPIUrl+word.requestedWord);
            requestParserMeaning(word);
        } else {
            this.startJsonParser();
        }
    }

    public void startJsonParser() throws IOException, ParseException {
        String content = "";
        Scanner scanner = new Scanner(this.url.openStream());
        while (scanner.hasNext()) {
            content += scanner.nextLine();
        }
        scanner.close();
        JSONParser parser = new JSONParser();
        Object object = parser.parse(content);
        this.object = object;
    }

    String generateRandomWord() {
        this.words = (List<String>) convertObjectToList(object);
        return this.words.get(this.random.nextInt(this.words.toArray().length));
    }

    void generateWordMeaning(Word word) throws IOException, ParseException {

        this.createConnection(this.wordAPIUrl+word.requestedWord);
        this.requestParserMeaning(word);

        JSONObject meaningSection;
        List<JSONObject> data = (List<JSONObject>) convertObjectToList(this.object);
        List<Object> meanings = (List<Object>) data.get(0).get("meanings");
        meaningSection = (JSONObject) meanings.get(0);

        if (meaningSection != null) {
            word.wordGenre = meaningSection.get("partOfSpeech").toString();
            JSONObject definition;
            if (meaningSection.keySet().contains("definitions")) {
                Object definitions = meaningSection.get("definitions");
                List<JSONObject> definitionArray = (List<JSONObject>) convertObjectToList(definitions);
                definition = definitionArray.get(0);
            } else {
                JSONObject wordDefinition = (JSONObject) meaningSection.get("definition");
                definition = wordDefinition;
            }
            word.wordMeaning = definition.get("definition").toString();
        } else {
            word.wordMeaning = "No meaning found";
        }
    }

    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }

    public void startFetching() throws IOException, ParseException {
        System.out.println("Value is trying to gathering ..");
        this.createConnection(this.APIUrl);
        System.out.println("Starting API parsing ..");
        this.requestParser();
        System.out.println("Initialising completed!");
    }

}
