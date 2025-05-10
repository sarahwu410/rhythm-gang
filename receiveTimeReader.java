/*
 * Eleora Jacob
 * April 17, 2025
 * Reads in receive times from a .txt file and returns a 2D array with them
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ReceiveTimeReader {
	
	/**
	 * Reads in the receive times from a .txt file. Format is strict-->receiveTime keyName
	 * @param keys	The keys in the order they will fill the 2D array
	 * @param fileWithTimes	The .txt file with receive times and note names
	 * @return	The receive times of all the notes in a double 2D array (receiveTimes[keyName][notes from end to beginning])
	 */
	public static double[][] find(String keys, String fileWithTimes) {
		// Stuff
		File textFile = new File(fileWithTimes);
		double[][] receiveTimes = null; // Will store all the receive times
		int totalNotes=0;
		HashMap<String, Integer> myKey = new HashMap<String, Integer>(); // Links a key to a location in the 2D array
		// The readers
		FileReader in;
		BufferedReader readFile;

		// Check if the file exists or create it
		if (textFile.exists()) {
			System.out.println("I already exist.");
		} else {
			System.out.println("Bruh.");
			System.out.println("It ain't even there.");
			System.exit(0);
		}
		
		// Read the file
		try {
			String line;
			in = new FileReader(textFile); // I am a reader of a file, you created a file object, use that
			readFile = new BufferedReader(in);
			
			// first read in the total number of notes
			while ((line = readFile.readLine()) != null) {
				System.out.println(line);
				try {
					totalNotes++;
				} catch (Exception e) {
					System.out.println("Bleh.");
				}
			}
			
			// Let the user know how many notes they are so they can assure they match
			System.out.println("Total Notes: " + totalNotes);
			// Initialize the array with receive times
			receiveTimes = new double[(keys.split("")).length][totalNotes];
			// Associate each key to a position
			for (int i = 0; i < (keys.split("")).length; i++) {
				myKey.put((keys.split(""))[i], i);
			}
			
			// Store the info
			in = new FileReader(textFile);
			readFile = new BufferedReader(in);
			String[] current = new String[2];
			// Reuse totalNotes as a counter
			totalNotes = 0;
			while ((line = readFile.readLine()) != null) {
				try {
					// Store the receive times
					current = (line.trim()).split(" "); // The first half should be the receive time and the second half should be the note
					receiveTimes[myKey.get(current[1])][totalNotes] = Double.parseDouble((current[0]));
					totalNotes++;
				} catch (Exception e) {
					System.out.println("Uhhhh, ain't a number, next!");
					System.out.println("IOException e: " + e.getMessage());
				}
			}
			
			readFile.close();
			in.close();
		} catch (IOException k) {
			System.out.println("I can't...I can't do it...");
			System.out.println("IOException e: " + k.getMessage());
		}
		
		// Return all the receive times
		System.out.println("I am returning a double 2d array with " + receiveTimes.length + " dif. notes and " + receiveTimes[0].length + " notes");
		return receiveTimes;
	}

	/**
	 * Reads in the receive times from a .txt file. Format is strict-->receiveTime endTime numSpam keyName
	 * @param keys	The keys in the order they will fill the 2D array (Ex: "ABC" --> A [0][?], B[1][?], C[O][?])
	 * @param fileWithTimes	The .txt file with receive times and note names
	 * @return	The receive times of all the notes in a double 2D array (receiveTimes[keyName][receiveTime noteLength spamAmount KeyName])
	 */
	public static String[][] findSpam(String keys, String fileWithTimes) {
		// Stuff
		File textFile = new File(fileWithTimes);
		String[][] receiveTimes = null; // Will store all the receive times
		int totalNotes=0;
		HashMap<String, Integer> myKey = new HashMap<String, Integer>(); // Links a key to a location in the 2D array
		// The readers
		FileReader in;
		BufferedReader readFile;

		// Check if the file exists or create it
		if (textFile.exists()) {
			System.out.println("I already exist.");
		} else {
			System.out.println("Bruh.");
			System.out.println("It ain't even there.");
			System.exit(0);
		}

		// Read the file
		try {
			String line;
			in = new FileReader(textFile); // I am a reader of a file, you created a file object, use that
			readFile = new BufferedReader(in);
			
			// first read in the total number of notes
			while ((line = readFile.readLine()) != null) {
				System.out.println(line);
				try {
					totalNotes++;
				} catch (Exception e) {
					System.out.println("Bleh.");
				}
			}
			
			// Let the user know how many notes they are so they can assure they match
			System.out.println("Total Notes: " + totalNotes);
			// Initialize the array with receive times
			receiveTimes = new String[(keys.split("")).length][totalNotes];
			// Associate each key to a position
			for (int i = 0; i < (keys.split("")).length; i++) {
				myKey.put((keys.split(""))[i], i);
			}
			
			// Store the info
			in = new FileReader(textFile);
			readFile = new BufferedReader(in);
			String[] current = new String[2];
			// Reuse totalNotes as a counter
			totalNotes = 0;
			while ((line = readFile.readLine()) != null) {
				try {
					// Store the receive times
					current = (line.trim()).split(" "); // The first half should be the receive time and the second half should be the note
					receiveTimes[myKey.get(current[3])][totalNotes] = current[0] + " " + current[1] + " " + current[2];
					totalNotes++;
				} catch (Exception e) {
					System.out.println("Uhhhh, ain't a number, next!");
					System.out.println("IOException e: " + e.getMessage());
				}
			}
			
			readFile.close();
			in.close();
		} catch (IOException k) {
			System.out.println("I can't...I can't do it...");
			System.out.println("IOException e: " + k.getMessage());
		}
		
		// Return all the receive times
		System.out.println("I am returning a double 2d array with " + receiveTimes.length + " dif. notes and " + receiveTimes[0].length + " notes");
		return receiveTimes;
	}

	
	/**
	 * Reads in the receive times from a .txt file. Format is strict-->receiveTime keyName
	 * @param fileWithTimes	The .txt file with receive times and note names
	 * @param myLevel	The level of the notes
	 * @param myReceivers	A hashmap containing the note letter and corresponding receiver
	 * @return	The receive times of all the notes in a double 2D array (receiveTimes[keyName][notes from end to beginning])
	 */
	public static TapBlock[] loadTapBlocks(String fileWithTimes, String myLevel, HashMap<String, Receiver> myReceivers) {
		// Stuff
		File textFile = new File(fileWithTimes);
		int totalNotes=0;
		// The readers
		FileReader in;
		BufferedReader readFile;
		// Create an array of TapBlocks
		TapBlock[] tappies;
		String line;

		// Check if the file exists or quit
		if (textFile.exists()) {
			System.out.println("I already exist.");
		} else {
			System.out.println("Bruh.");
			System.out.println("It ain't even there.");
			System.exit(0);
		}
		
		// Read the file
		try {
			in = new FileReader(textFile); // I am a reader of a file, you created a file object, use that
			readFile = new BufferedReader(in);
			
			// first read in the total number of notes
			while ((line = readFile.readLine()) != null) {
				System.out.println(line);
				try {
					totalNotes++;
				} catch (Exception e) {
					System.out.println("Bleh.");
				}
			}
		} catch (IOException e) {
			System.out.println("Cool idk.");
		}
			
		// Let the user know how many notes they are so they can assure they match
		System.out.println("Total Notes: " + totalNotes);
		// Set size of tap block array
		tappies = new TapBlock[totalNotes];
			
		try {	
			// Store the info
			in = new FileReader(textFile);
			readFile = new BufferedReader(in);
			String[] current = new String[2]; // stores info per line
			// Reuse totalNotes as a counter
			totalNotes = 0;
			while ((line = readFile.readLine()) != null) {
				try {
					current = (line.trim()).split(" "); // The first half should be the receive time and the second half should be the note
					// Create a block
					tappies[totalNotes] = new TapBlock(myLevel, current[1], (int) Double.parseDouble((current[0]))*1000, myReceivers.get(current[1]));
					
					System.out.println("Enter Time: " + tappies[totalNotes].enterTime + " " + tappies[totalNotes].button);
					totalNotes++;
				} catch (Exception e) {
					System.out.println("Could not load block.");
					System.out.println("IOException e: " + e.getMessage());
				}
			}
			
			readFile.close();
			in.close();
		} catch (IOException k) {
			System.out.println("Something went wrong with loading the blocks.");
			System.out.println("IOException e: " + k.getMessage());
		}
		
		// Return all the receive times
		System.out.println("I am returning an array of tap blocks with " + tappies.length + " notes.");
		return tappies;
	}

	/**
	 * Reads in the receive times from a .txt file. Format is strict-->receiveTime endTime numSpam keyName
	 * @param fileWithTimes	The .txt file with spam note information
	 * @param myLevel	The level of the notes
	 * @param myReceivers	A hashmap containing the note letter and corresponding receiver
	 * @return	An array of spam blocks
	 */
	public static SpamBlock[] loadSpamBlocks(String fileWithTimes, String myLevel, HashMap<String, Receiver> myReceivers) {
		// Stuff
		File textFile = new File(fileWithTimes);
		int totalNotes=0;
		// The readers
		FileReader in;
		BufferedReader readFile;
		// Create an array of TapBlocks
		SpamBlock[] spammers;
		String line;

		// Check if the file exists or quit
		if (textFile.exists()) {
			System.out.println("I already exist.");
		} else {
			System.out.println("Bruh.");
			System.out.println("It ain't even there.");
			System.exit(0);
		}
		
		// Read the file
		try {
			in = new FileReader(textFile); // I am a reader of a file, you created a file object, use that
			readFile = new BufferedReader(in);
			
			// first read in the total number of notes
			while ((line = readFile.readLine()) != null) {
				System.out.println(line);
				try {
					totalNotes++;
				} catch (Exception e) {
					System.out.println("Bleh.");
				}
			}
		} catch (IOException e) {
			System.out.println("Cool idk.");
		}
			
		// Let the user know how many notes they are so they can assure they match
		System.out.println("Total Notes: " + totalNotes);
		// Set size of tap block array
		spammers = new SpamBlock[totalNotes];
			
		try {	
			// Store the info
			in = new FileReader(textFile);
			readFile = new BufferedReader(in);
			String[] current = new String[4]; // stores info per line
			// Reuse totalNotes as a counter
			totalNotes = 0;
			while ((line = readFile.readLine()) != null) {
				try {
					current = (line.trim()).split(" "); // receiveTime endTime numSpam keyName
					// Create a block
					spammers[totalNotes] = new SpamBlock(myLevel, current[3], (int) Double.parseDouble((current[0]))*1000, myReceivers.get(current[1]), Integer.valueOf(current[2]), (int) Double.parseDouble((current[1]))*1000);
					System.out.println("Enter Time: " + spammers[totalNotes].enterTime + " " + spammers[totalNotes].button);
					totalNotes++;
				} catch (Exception e) {
					System.out.println("Could not load block.");
					System.out.println("IOException e: " + e.getMessage());
				}
			}
			
			readFile.close();
			in.close();
		} catch (IOException k) {
			System.out.println("Something went wrong with loading the blocks.");
			System.out.println("IOException e: " + k.getMessage());
		}
		
		// Return all the receive times
		System.out.println("I am returning an array of spam blocks with " + spammers.length + " notes.");
		return spammers;
	}
}