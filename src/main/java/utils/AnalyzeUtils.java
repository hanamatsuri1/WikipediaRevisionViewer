package utils;


import domain.Editor;
import java.util.*;

public class AnalyzeUtils {


    public static List sortlist (List<Editor> list) {
        //Counting user modifications
        for (Editor editor : list) {
            editor.setCount(1);
            for (Editor editor1 : list) {
                if (editor.getUser().equals(editor1.getUser())) {
                    editor.addcountonetimes();
                }
            }
            if (editor.getCount() != 1) {
                editor.minuscountonetimes();
            }
        }

        //Save the user's latest modification time
        for (int i=0; i<list.size(); i++){
            for ( int j=list.size()-1; j>i; j--) {
                    if (list.get(i).getUser().equals(list.get(j).getUser())) {
                        if (list.get(j).getTimestamp().after(list.get(i).getTimestamp()))
                            list.get(i).setTimestamp(list.get(j).getTimestamp());
                        list.remove(j);
                }
            }
        }

        //Sort users by modifications and times
        Map<Editor, Integer> map = new HashMap<>();
        for (Editor editor: list){
            map.put(editor, editor.getCount());
        }
        List<Map.Entry<Editor, Integer>> sortedlist = new ArrayList<>();
        sortedlist.addAll(map.entrySet());
        Collections.sort(sortedlist, (o1, o2) -> {
            if (o2.getValue()==o1.getValue()){
                return (o2.getKey().getTimestamp().compareTo(o1.getKey().getTimestamp()));
            }
            return (o2.getValue() - o1.getValue());
        });
        return sortedlist;
    }

}
