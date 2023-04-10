package cen3024C;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
 
/**
* 
 * @author Miles Walker
* @version 2.03 04.06.2023
*
*/

public class wordcounter_GUI extends Application implements EventHandler<ActionEvent>
{

	/**
     * This is a program to analyze a preselected word document and return a list of all the 
      * words contained in the document and their frequency.
     */
     
     /**
     *  Setup Button & TextArea
     */
     Button button;
     TextArea results;
     
     public static void main(String[] args) 
     {
            /**
            * This is the main method that launches the GUI.
            * @param args
            */
            
            launch(args);
     }
     
     /**
     * Create GUI
     */
     @Override
     public void start(Stage primaryStage) throws Exception 
     {
            /**
            * This section is creating the GUI.
            */
            
            primaryStage.setTitle("Word Counter");
            
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10,10,10,10));
            grid.setVgap(8);
            grid.setHgap(10);
            
            button = new Button();
            button.setText("Click to Analyze \"The Raven\"");
            button.setOnAction(this);
            
            results = new TextArea("Word Count:");
            results.setPrefWidth(275); 
            results.setPrefHeight(500);
                         
            GridPane.setConstraints(button, 1, 1);
            GridPane.setConstraints(results, 1, 2);

            grid.getChildren().addAll(button, results);
            
            Scene scene = new Scene(grid, 300, 600);
            
            primaryStage.setScene(scene);
            primaryStage.show();
            
     }
     
     @Override
     public void handle(ActionEvent event)
     {
            if(event.getSource()==button)
            {
                   /**
                   * This section is the bulk of the program.  It imports the selected file
                   * and transfers it to a string.  It then gets rid of any punctuation and 
                    * makes ever letter capitalized.  Next it transfers all of the words to 
                    * an array and then to a hash map.  Once the hash map is sorted, it outputs
                   * the results to the text area in the GUI.
                   */
                   
                   /**
                   * Import file to string
                   */
                   FileReader textFile = null;
                   try 
                   {
                         textFile = new FileReader("The Raven.txt");
                   } 
                   catch (FileNotFoundException e) 
                   {
                         e.printStackTrace();
                   }
                   Scanner scanner = new Scanner(textFile);
                   String string;
                   
                   /**
                   * Transfer string to array and clean up punctuation
                   */
                   List<String> wordsList = new ArrayList<String>();
                   while (scanner.hasNext())
                   {
                         string = scanner.next();
                         string = string.toUpperCase();
                         string = string.replaceAll("-" ," ");
                         string = string.replaceAll("\\s"," ");
                         string = string.replaceAll("[^a-zA-Z ]","");
                         wordsList.add(string);
                   }
                                
                   String[] wordsArray = wordsList.toArray(new String[0]);
                   
                   /**
                   * Counting word totals
                   */
                   HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
                   
                   for (int i = 0; i < wordsArray.length; i++) 
                   {
                 String key = wordsArray[i];
                 int freq = freqMap.getOrDefault(key, 0);
                 freqMap.put(key, ++freq);
             }
                   
             Map<String, Integer> sortedFreqMap = sortByValue(freqMap);

             scanner.close();
                   
             /**
              * Printing results to TextArea
              */
                   StringBuilder str = new StringBuilder();
                   for (String key : sortedFreqMap.keySet()) 
                   {
                         str.append("The word \"")
                                .append(key)
                                .append("\" appears ")
                                .append(sortedFreqMap.get(key))
                                .append(" times.\n");
                   }
                   results.setText(str.toString());
            }
     }
     
     private static Map<String, Integer> sortByValue(Map<String, Integer> unsortFreqMap)
  {
      /**
       * This method is used to sort the hash map into a descending order.
       * @param Unsorted hash map
       * @return Sorted hash map.
       */
            
            List<Entry<String, Integer>> list = new LinkedList<>(unsortFreqMap.entrySet());

      /**
       *  Sorting the list based on values
       */
      list.sort((object1, object2) -> object1.getValue().compareTo(object2.getValue()) == 0
              ? object1.getKey().compareTo(object2.getKey())
              : object1.getValue().compareTo(object2.getValue()) == 0
              ? object2.getKey().compareTo(object1.getKey())
              : object2.getValue().compareTo(object1.getValue()));
      return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
  }

}
