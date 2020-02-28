package utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Editor;
import exceptions.ParameterIsNotJsonStringExceptio;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParseUtils {

        public static String parseUrlToJsonString (String PageKeyword) throws IOException {
            String urlwithKeyword = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" + PageKeyword + "&rvprop=timestamp|user&rvlimit=30&redirects";
            URL url = new URL(urlwithKeyword);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent",
                    "Revision Tracker/0.1 (http://www.cs.bsu.edu/; jfu@bsu.edu)");
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            String result = scanner.nextLine();
            return result;
        }

        public static List<Editor> parseJsonToList(String jsonString) throws ParameterIsNotJsonStringExceptio {
                if (jsonString.charAt(0) != '{'){
                        throw new ParameterIsNotJsonStringExceptio();
                }
                JsonParser jsonParser = new JsonParser();
                JsonElement rootElement = jsonParser.parse(jsonString);
                JsonObject rootObject = rootElement.getAsJsonObject();
                String redirected=" ";
                JsonArray array = null;
                JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
                for (Map.Entry<String,JsonElement> entry : pages.entrySet()){
                        JsonObject entryObject = entry.getValue().getAsJsonObject();
                        array = entryObject.getAsJsonArray("revisions");
                        if (jsonString.contains("redirects")){
                                redirected= entryObject.getAsJsonPrimitive("title").toString();
                        }
                }
                Type listType = new TypeToken<List<Editor>>() {}.getType();
                List<Editor> List = new Gson().fromJson(array,listType);
                for (Editor editor :List){
                        editor.setPagetitle(redirected);
                }
                return List;
        }


}
