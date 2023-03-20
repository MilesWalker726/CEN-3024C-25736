package application;

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

public class GUI_Tutorial extends Application implements EventHandler<ActionEvent>
{
	// Setup Button & TextArea
	Button button;
	TextArea results;
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	//Create GUI
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
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
			//Import file to string
			FileReader textFile = null;
			try 
			{
				textFile = new FileReader("The Raven.txt");
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scanner scanner = new Scanner(textFile);
			String string;
			
			//Transfer string to array and clean up punctuation
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
			
			//Count word totals
			HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
			
			for (int i = 0; i < wordsArray.length; i++) 
			{
	            String key = wordsArray[i];
	            int freq = freqMap.getOrDefault(key, 0);
	            freqMap.put(key, ++freq);
	        }
			
	        Map<String, Integer> sortedFreqMap = sortByValue(freqMap);

	        scanner.close();
			
	        //Print results to TextArea
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
        List<Entry<String, Integer>> list = new LinkedList<>(unsortFreqMap.entrySet());

        // Sorting the list based on values
        list.sort((object1, object2) -> object1.getValue().compareTo(object2.getValue()) == 0
                ? object1.getKey().compareTo(object2.getKey())
                : object1.getValue().compareTo(object2.getValue()) == 0
                ? object2.getKey().compareTo(object1.getKey())
                : object2.getValue().compareTo(object1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }
}
