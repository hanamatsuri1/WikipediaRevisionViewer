package utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Editor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class utils {

    public static String urlToJsonString (String PageKeyword) {
        try {
            String urlwithKeyword = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" + PageKeyword + "&rvprop=timestamp|user&rvlimit=30&redirects";
            URL url = new URL(urlwithKeyword);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent",
                    "Revision Tracker/0.1 (http://www.cs.bsu.edu/; jfu@bsu.edu)");
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            String result = scanner.nextLine();
            return result;
        }catch (IOException a) {
            return "IOException";
        }
    }

    public static List<Editor> parseJsonToList(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonArray array = null;
        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
        for (Map.Entry<String,JsonElement> entry : pages.entrySet()){
            JsonObject entryObject = entry.getValue().getAsJsonObject();
            array = entryObject.getAsJsonArray("revisions");
        }
        Type listType = new TypeToken<List<Editor>>() {}.getType();
        List<Editor> List = new Gson().fromJson(array,listType);
        return List;
    }

    public static List sortlist (List<Editor> list) {
        Map<String,Integer> map=new HashMap<>();
        for (Editor editor: list) {
            Integer count = map.get(editor.getUser());
            if (null != count) {
                map.put(editor.getUser(), count + 1);
            } else {
                map.put(editor.getUser(), 1);
            }
        }
        List<Map.Entry<String, Integer>> sortedlist = new ArrayList<>();
        sortedlist.addAll(map.entrySet());
        Collections.sort(sortedlist, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        return sortedlist;
    }



}
