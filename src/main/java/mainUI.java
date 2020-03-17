import domain.Editor;
import exceptions.ParameterIsNotJsonStringExceptio;
import utils.AnalyzeUtils;
import utils.CheckUtils;
import utils.ParseUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class mainUI extends JFrame implements ActionListener {

    JLabel term;
    JTextField input;
    JButton fetch;
    JTextArea recentTable;
    JTextArea activeTable;

    String url;
    List<Editor> list;
    List<Map.Entry<Editor, Integer>> sortedlist;
    String recentEditor="Here are the editors who has recently editing this Wikipedia page"+"\r\n";
    String activeEditor="Here are the editors who has been most active editing this Wikipedia page"+"\r\n";

    //Resetting methods
    public void resetUrl(){
        url="";
    }

    public void resetList(){
        list.clear();
    }

    public void resetSortedlist(){
        sortedlist.clear();
    }

    public void resetRecentEditor(){
        recentEditor="Here are the editors who has recently editing this Wikipedia page"+"\r\n";
    }

    public void resetActiveEditor(){
        activeEditor="Here are the editors who has been most active editing this Wikipedia page"+"\r\n";
    }

    //Checking methods
    public boolean checkIfPageExists(String url){
        if (url.contains("missing")) {
            return false;
        } else {
            return true;
        }
    }

    public void checkIfRedirects(String url){
        if (url.contains("redirects")) {
            recentEditor += "You are redirect from " + input.getText() + " to " + list.get(0).getPagetitle() + "\r\n";
            activeEditor += "You are redirect from " + input.getText() + " to " + list.get(0).getPagetitle() + "\r\n";
        }
    }

    //Displaying methods
    public void DisplayModificationRecord(List<Editor> list){
        for (int i = 0; i < list.size(); i++) {
            recentEditor += "username: " + list.get(i).getUser() + "   timestamp: " + list.get(i).getTimestamp() + "\r\n";
        }
        recentTable.setText(recentEditor);
    }

    public void DisplayMostActiveEditors(List<Map.Entry<Editor, Integer>> sortedlist){
        for (Map.Entry<Editor, Integer> editor : sortedlist) {
            activeEditor += "username: " + editor.getKey().getUser() + "   number of recent changes: " + editor.getValue() + "\r\n";
        }
        activeTable.setText(activeEditor);
    }

    public mainUI() {
        super("Wikipedia Revision Viewer with Swing UI");

        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        fetch = new JButton("Fetch");
        var fetchConstraints = new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        fetch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///Check network connection
                if (!CheckUtils.checkNetworkConnection()) {
                    recentTable.setText("Network connection failed");
                    activeTable.setText("Network connection failed");
                } else {
                    //Clear data(message for network connection failed)
                    resetActiveEditor();
                    resetRecentEditor();
                    try {
                        url = ParseUtils.parseUrlToJsonString(input.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    //Check if there is no wikipedia page
                    if (!checkIfPageExists(url)) {
                        recentTable.setText("No Wikipedia Page Founded");
                        activeTable.setText("No Wikipedia Page Founded");
                    } else {
                        //Clear data(message for no wikipedia page)
                        resetActiveEditor();
                        resetRecentEditor();
                        try {
                            list = ParseUtils.parseJsonToList(url);
                        } catch (ParameterIsNotJsonStringExceptio parameterIsNotJsonStringExceptio) {
                            parameterIsNotJsonStringExceptio.printStackTrace();
                        }
                        //Check if directed
                        checkIfRedirects(url);
                        //Print who most recently changed a Wikipedia article in reverse-chronological order
                        DisplayModificationRecord(list);
                        //Display most active editors
                        sortedlist = AnalyzeUtils.sortlist(list);
                        DisplayMostActiveEditors(sortedlist);
                        //Clear data
                        resetList();
                        resetUrl();
                        resetSortedlist();
                        resetActiveEditor();
                        resetRecentEditor();
                    }
                }
            }
        });
        fetch.setSize(10,10);
        panel.add(fetch, fetchConstraints);

        input = new JTextField();
        var inputConstraints = new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(input, inputConstraints);
        input.setSize(10,10);

        recentTable = new JTextArea("This will show the most recently modified records");
        var recentConstraints = new GridBagConstraints(0, 3, 1,1 , 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0);
        panel.add(recentTable, recentConstraints);
        recentTable.setSize(100,100);

        activeTable = new JTextArea("This will show the most active editors");
        var activeConstraints = new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0);
        panel.add(activeTable, activeConstraints);
        activeTable.setSize(100,100);

        term = new JLabel("Which Wikipedia page do you want to see the most recent changes to?");
        var termConstraints = new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0);
        panel.add(term, termConstraints);

        setPreferredSize(new Dimension(1500, 1500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new mainUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}