import domain.Editor;
import exceptions.PageNotFoundException;
import exceptions.ParameterIsNotJsonStringExceptio;
import utils.CheckUtils;
import utils.ParseUtils;
import utils.AnalyzeUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class main {

    public static void main(String[] args) throws IOException, PageNotFoundException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                //Request page name from the user
                System.out.println("Which Wikipedia page do you want to see the most recent changes to? (type 'q' to exit)");
                String input = br.readLine();
                if (input.equals("q")) {
                    break;
                } else {
                    //Check network connection
                    if (CheckUtils.checkNetworkConnection()==false){
                        System.out.println("Network connection cannot be established to Wikipedia");
                        break;
                    }
                    String url = ParseUtils.parseUrlToJsonString(input);
                    //Check if there is no wikipedia page?
                    if (url.contains("missing")){
                        System.out.println("No Wikipedia Page Founded");
                        break;
                    }
                    List<Editor> list = ParseUtils.parseJsonToList(url);
                    //Check if directed
                    if (url.contains("redirects")){
                        System.out.println("You are redirect from "+"\""+input+ "\""+ " to "+list.get(0).getPagetitle());
                    }
                    //Print who most recently changed a Wikipedia article in reverse-chronological order
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println("username: " + list.get(i).getUser() +
                                "   timestamp: " + list.get(i).getTimestamp());
                    }
                    //Print who has been most active editing the page
                    List<Map.Entry<Editor, Integer>> sortedlist = AnalyzeUtils.sortlist(list);
                    System.out.println("----------------------------------------------------------------------");
                    System.out.println("Here are the editors who has been most active editing a Wikipedia page");
                    for (Map.Entry<Editor, Integer> editor : sortedlist) {
                        System.out.println("username: " + editor.getKey().getUser());
                        System.out.println("number of recent changes: " + editor.getValue());
                        System.out.println();
                    }
                }
            }
        }catch (IOException e){
            System.out.println("IO Exception.");
        }catch (ParameterIsNotJsonStringExceptio e){
            System.out.println("ParameterIsNotJsonStringExceptio");
        }
    }
}
