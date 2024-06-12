import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * reads in a name from the user and displays the popularity of that name from 1890 to 2010
 * @author s-stojima
 *
 */
public class BabyName {
	public static void main(String[] args) throws FileNotFoundException {
		//read the files
		File fNames = new File("names.txt");
		Scanner sNames = new Scanner(fNames);
		File fMeanings = new File("meanings.txt");
		Scanner sMeanings = new Scanner(fMeanings);
		Scanner console = new Scanner(System.in);
		
		intro();
		System.out.print("Name: ");
		String name = console.nextLine();
		String popularity = getMatch(name, sNames);
		String meaning = getMatch(name, sMeanings);

		if(popularity != null && meaning != null) {
			System.out.println(popularity);
			System.out.println(meaning);
			drawGraph(popularity, meaning);
		}else {
			System.out.println("Name was not in top 100 names");
		}
		
		
		//close all the files
		sNames.close();
		sMeanings.close();
		console.close();
	}
	
	
	public static void intro() {
		System.out.println("This program allows you to search through the");
		System.out.println("data from the Social Security administration");
		System.out.println("to see how popular a particular name has been");
		System.out.println("since 1890.");
		System.out.println("");
	}
	
	
	public static String getMatch(String name, Scanner sFile) {
		// see if name exists
		while(sFile.hasNextLine()) {
			String line = sFile.nextLine();
			Scanner sLine = new Scanner(line);
			String lineName = sLine.next().toUpperCase();
			sLine.close();
			if(name.toUpperCase().equals(lineName)) {
				return line;
			}
		}
		//nothing was found
		return null;
		
	}
	
	
	public static void drawGraph(String popularity, String meaning) {
		DrawingPanel panel = new DrawingPanel(780, 560);
		Graphics g = panel.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		
		g.fillRect(0,  0,  780,  30);
		g.fillRect(0,  530,  780,  30);
		g.setColor(Color.BLACK);
		g.drawRect(0,  0,  780,  30);
		g.drawRect(0,  530,  780,  30);
		g.drawString(meaning, 0, 16);
		
		int year = 1890;
		int colWidth = 60;
		//trash the name and gender
		Scanner lineScan = new Scanner(popularity);
		lineScan.next();
		lineScan.next();
		for(int i=0; i<13; i++) {
			g.setColor(Color.black);
			int x = i*colWidth;
			g.drawString(""+ year, x, 545);
			year+=10;
			
			//draw rank and bar graph
			int rank = lineScan.nextInt();
			int yTop = rank * 2/5;
			if(rank == 1000)rank = 0;
			
			g.setColor(new Color(230, 255, 230));
			g.fillRect(x, 530-yTop, 30, yTop);
			g.setColor(Color.black);
			g.drawString(""+rank, x, 530-yTop);
		}
		
	}
}
