import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ExpectedBrickValue {
	public static void main(String args[]) {
		if(args.length < 2) {
			System.out.println("usage: java ExpectedBrickValue <decklist> <mulligan specifications>");
		}
		String decklist = new String(args[0]).trim();
		String specs = new String(args[1]).trim();
		ArrayList<String> deck = new ArrayList<String>();
		ArrayList<Condition> conditions = new ArrayList<Condition>();
		//Read decklist from file
		//Format is CardName,Quantity
		try {
			FileReader fr = new FileReader(decklist);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine()) != null) {
	        	// Split 
	        	String[] row = (new String(line)).trim().split(",");
	            String card = row[0];
	            int number = Integer.parseInt(row[1]);
	            for(int i = 0; i < number; i++) {
	            	deck.add(card);
	            }
	        }
			br.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file: " + decklist);
		}
		catch(IOException e) {
			System.out.println("Error reading file: " + decklist);
		}
		
		try {
			FileReader fr = new FileReader(specs);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine()) != null) {
	        	// Split 
	        	String[] row = (new String(line)).trim().split(":");
	            String r = row[0];
	            String k = row[1];
	            String[] reqs = r.split(",");
	            String[] keep = k.split(",");
	            conditions.add(new Condition(reqs, keep));
	        }
			br.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file: " + specs);
		}
		catch(IOException e) {
			System.out.println("Error reading file: " + specs);
		}
		double sum = 0.0;
		int runs = 100000;
		for(int k = 0; k < runs; k++) {
			sum += getEBV(deck, conditions);
		}
		System.out.println("EBV: " + (double)(sum / runs));
	}
	
	public static double getEBV(ArrayList<String> deck, ArrayList<Condition> conditions) {
		ArrayList<String> shuffled = shuffle(deck); //Shuffle the deck
		ArrayList<String> hand = new ArrayList<String>();
		ArrayList<String> discard = new ArrayList<String>();
		boolean[] keepCard = new boolean[3];
		boolean first = false;
		int coin = (int)(Math.random() * 2);
		if(coin == 0) {
			first = true;
		}
		//Draw initial hand of 3
		while(hand.size() < 3) {
			hand.add(shuffled.remove(0));
		}
		if(first) {
			hand.add("FIRST");
		}
		else {
			hand.add("SECOND");
		}
		
		//Figure out which cards to keep
		for(int j = 0; j < hand.size() - 1; j++) {
			keepCard[j] = shouldKeep(hand, hand.get(j), conditions);
		}
		//Mulligan selected cards and move mulliganed cards into discard since they are no longer relevant
		for(int k = 2; k >= 0; k--) {
			if(!keepCard[k]) {
				discard.add(hand.remove(k));
			}
		}
		//Draw cards from shuffled deck until hand has 3 cards
		while(hand.size() < 4) {
			hand.add(0, shuffled.remove(0));
		}
		hand.add(0, shuffled.remove(0));
		if(!first) {
			hand.add(0, shuffled.remove(0));
		}
		
		//Score the hand! 
		//+1 point if you would keep the card, +0 otherwise
		double EBV = 0.0;
		for(int h = 0; h < hand.size() - 1; h++) {
			if(shouldKeep(hand, hand.get(h), conditions)) {
				EBV++;
			}
		}
		return EBV;
	}
	
	public static boolean shouldKeep(ArrayList<String> hand, String card, ArrayList<Condition> conditions) {
		for(int i = 0; i < conditions.size(); i++) {
			Condition c = conditions.get(i);
			if(contains(c.getReqs(), "KEEP") && contains(c.getKeep(), card)) {
				return true;
			}
			boolean met = true;
			if(contains(c.getKeep(), card)) {
				for(int t = 0; t < c.getReqs().length; t++) {
					if(!hand.contains(c.getReqs()[t])) {
						met = false;
					}
				}
				if(met) {
					return met;
				}
			}
		}
		return false;
	}
	
	public static ArrayList<String> shuffle(ArrayList<String> deck) {
		ArrayList<String> shuffled = new ArrayList<String>();
		ArrayList<String> deckCopy = new ArrayList<String>(deck);
		while(!deckCopy.isEmpty()) {
			int index = (int)(Math.random() * deckCopy.size());
			shuffled.add(deckCopy.remove(index));
		}
		return shuffled;
	}
	
	public static boolean contains(String[] list, String search) {
		for(int i = 0; i < list.length; i++) {
			if(list[i].equals(search)) {
				return true;
			}
		}
		return false;
	}
}

class Condition {
	String[] reqs;
	String[] keep;
	public Condition(String[] r, String[] k) {
		reqs = r;
		keep = k;
	}
	
	public String[] getReqs() {
		return reqs;
	}
	
	public String[] getKeep() {
		return keep;
	}
}

