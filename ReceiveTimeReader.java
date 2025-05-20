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
import java.util.ArrayList;

public class ReceiveTimeReader {
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
					tappies[totalNotes] = new TapBlock(myLevel, current[1], (int)(Double.parseDouble((current[0]))*1000), myReceivers.get(current[1]));
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
	 * Reads in the note information from a .txt file. Format is strict-->receiveTime spamTime numSpam keyName
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
		// Create an array of SpamBlocks
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
					spammers[totalNotes] = new SpamBlock(myLevel, current[3], (int)(Double.parseDouble((current[0]))*1000), myReceivers.get(current[3]), Integer.valueOf(current[2]), (int)(Double.parseDouble((current[1]))*1000));
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

	/**
	 * Reads in note information from a .txt file. Format is strict-->receiveTime holdDuration keyName
	 * @param fileWithTimes	The .txt file with hold note information
	 * @param myLevel	The level of the notes
	 * @param myReceivers	A hashmap containing the note letter and corresponding receiver
	 * @return	An array of hold blocks
	 */
	public static HoldBlock[] loadHoldBlocks(String fileWithTimes, String myLevel, HashMap<String, Receiver> myReceivers) {
		// Stuff
		File textFile = new File(fileWithTimes);
		int totalNotes=0;
		// The readers
		FileReader in;
		BufferedReader readFile;
		// Create an array of HoldBlocks
		HoldBlock[] holdies;
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
		holdies = new HoldBlock[totalNotes];
			
		try {	
			// Store the info
			in = new FileReader(textFile);
			readFile = new BufferedReader(in);
			String[] current = new String[3]; // stores info per line
			// Reuse totalNotes as a counter
			totalNotes = 0;
			while ((line = readFile.readLine()) != null) {
				try {
					current = (line.trim()).split(" "); // receiveTime holdDuration keyName
					// Create a block
					holdies[totalNotes] = new HoldBlock(myLevel, current[2], (int)(Double.parseDouble((current[0]))*1000), myReceivers.get(current[2]), (int)(Double.parseDouble((current[1]))*1000));
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
		System.out.println("I am returning an array of hold blocks with " + holdies.length + " notes.");
		return holdies;
	}

	/*
	 * Sorts 3 kinds of notes into one array list. Returns an array organized by enterTime (lowest to highest)
	 * @param tappies	Tapblocks to be sorted
	 * @param spammies	SpamBlocks to be sorted
	 * @param holdies	HoldBlocks to be sorted
	 * @return an array list of all the blocks sorted by enter time
	 */
	public static ArrayList<Block> sortBlocks(Block[] tappies, Block[] spammies, Block[] holdies) {

		ArrayList<Block> myNotes = new ArrayList<>();

		// Add tapblocks to array list of notes
		for (Block b: tappies) {
			myNotes.add(b);
		}
		System.out.println("Tap blocks added.");

		// Sort SpamBlocks by insertion sorting
		for (Block b: spammies) {
			for (int i = 0; i < myNotes.size(); i++) {
				if (b.enterTime < myNotes.get(i).enterTime) {
					if (i == 0) myNotes.add(0, b);
					else myNotes.add(i, b);
					break;
				} else if (i == myNotes.size() - 1) {
					myNotes.add(b);
					break;
				}
			}
		}
		System.out.println("Spam blocks sorted.");
		

		// Sort HoldBlocks by insertion sorting
		for (Block b: holdies) {
			for (int i = 0; i < myNotes.size(); i++) {
				if (b.enterTime < myNotes.get(i).enterTime) {
					if (i == 0) myNotes.add(0, b);
					else myNotes.add(i, b);
					break;
				} else if (i == myNotes.size() - 1) {
					myNotes.add(b);
					break;
				}
			}
		}
		System.out.println("Hold blocks sorted.");

		return myNotes;
	}

	/**
	 * Gets the blocks for a specific receiver
	 * @param allBlocks	All the blocks with unsorted note names
	 * @param noteName	The note name to look for
	 * @return	An array list of the with the requested blocks
	 */
	public static ArrayList<Block> pull(ArrayList<Block> allBlocks, String noteName) {
		ArrayList<Block> mySpecifics = new ArrayList<>();

		// Loop through all the blocks to find the right ones
		for (Block b: allBlocks) {
			if ((b.button).equals(noteName)) mySpecifics.add(b);
		}

		return mySpecifics;
	}

	/**
	 * Finds the image size for a hold block and prints it out in the terminal
	 * @param b	The hold block to find the image size for
	 * 
	 */
	public static void myHoldBlockImageSize(HoldBlock b) {
		// Find the difference in time between the head and the tail
		int myHeadTime = 0;
		int myTailTime = b.endTime - b.enterTime;

		// Find the distances using the time
		double myX = (int) (b.enterX + b.velocityX * myHeadTime);
        double myY = (int) (b.enterY + b.velocityY * myHeadTime);
        double myTailX = (int) (b.enterX + b.velocityX * myTailTime);
        double myTailY = (int) (b.enterY + b.velocityY * myTailTime);

		// Print out what kind of image is required for the hold block
		System.out.println("You need an image that is " + (b.width + Math.abs(myX - myTailX)) + "x" + (b.length + Math.abs(myY - myTailY)));
		System.out.println("Draw your image from corner to corner based on what angle it's coming from.");
	}
}
