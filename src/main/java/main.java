import domain.Editor;
import utils.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class main {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Which Wikipedia page do you want to see the most recent changes to?");
            String PageKeyword = br.readLine();
            String url=utils.urlToJsonString(PageKeyword);
            List<Editor> list=utils.parseJsonToList(url);
            for (int i=list.size()-1; i>=0; i--){
                System.out.println("username: "+list.get(i).getUser()+
                        "   timestamp: "+list.get(i).getTimestamp());
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
}