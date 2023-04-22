package cen3024C;

import java.io.FileReader;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Final 
{

	public static void main(String[] args) throws Exception
	{
		FileReader textFile = new FileReader("The Raven.txt");
        Scanner scanner = new Scanner(textFile);
        String string;
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
        HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
        
        for (int i = 0; i < wordsArray.length; i++)
        {
			String key = wordsArray[i];
			int freq = freqMap.getOrDefault(key, 0);
			freqMap.put(key, ++freq);
        }

        Map<String, Integer> sortedFreqMap = sortByValue(freqMap);

		getConnection(sortedFreqMap);
        
        printMap(sortedFreqMap);

        scanner.close();
	}

	private static Map<String, Integer> sortByValue(Map<String, Integer> unsortFreqMap)
	{
		List<Entry<String, Integer>> list = new LinkedList<>(unsortFreqMap.entrySet());

		list.sort((object1, object2) -> object1.getValue().compareTo(object2.getValue()) == 0
			? object1.getKey().compareTo(object2.getKey())
			: object1.getValue().compareTo(object2.getValue()) == 0
			? object2.getKey().compareTo(object1.getKey())
			: object2.getValue().compareTo(object1.getValue()));
		return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
	}

	private static void printMap(Map<String, Integer> map)
	{
		map.forEach((word, count) -> System.out.println("The word \"" + word + "\" appears " + count + " time(s)."));
	}
	
	public static Connection getConnection(Map<String, Integer> map) throws Exception
	{
		try
		{
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/Word_Occurrances";
			String username = "root";
			String password = "root";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			
			Statement stmt = null;
			PreparedStatement p = null;
	        ResultSet rs = null;
			stmt = (Statement) conn.createStatement();
			
			for (Map.Entry<String, Integer> set : map.entrySet()) 
			{
				String query = "INSERT INTO Words " + "VALUES ('" + set.getKey() + "', " + set.getValue() + ")";
				stmt.executeUpdate(query);
	        }
						
			return conn;
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return null;
	}
}

