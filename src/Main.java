

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


/* 
I have not received unauthorized aid on this assignment. I understand
the answers that I have submitted. The answers submitted have not
been directly copied from another source, but instead are written in
my own words. 
*/

/**
 * Final Project
 * @authors Bryan Malyn (bmalyn)
 * 			John Mooring (jmooring)
 * 			Derek Chaconas (chaconas)
 */

public class Main {
	public static void main(String[] args) {
		ArrayList<Atom> atoms = new ArrayList<Atom>();
		Universe universe;
		String inputFilePath = null;
		String outputFilePath = null;
		double probeSize = 0;
		double resolution = 0;
		if (args.length != 8)
			inputError();
		// Load the command line arguments
		for (int i = 0; i < 8; i += 2) {
			if (args[i].equals("-i")) {
				inputFilePath = args[i + 1];
			} else if (args[i].equals("-o")) {
				outputFilePath = args[i + 1];
			} else if (args[i].equals("-probe")) {
				probeSize = Double.parseDouble(args[i + 1]);
			} else if (args[i].equals("-resolution")) {
				resolution = Double.parseDouble(args[i + 1]);
			} else {
				inputError();
			}
		}
		// Load the universe
		universe = loadFile(inputFilePath, atoms, resolution, probeSize);
		// Erase probe atoms from universe
		universe.eraseProbeAtoms(atoms);
		// Erase non cavities from universe
		universe.eraseNonCavities();
		// Generates output file
		printOutputFile(outputFilePath, atoms, universe);
	}

	// Loads the universe from the input file, and sets the resolution as well
	// as the probe size.
	public static Universe loadFile(String inputFilePath,
			ArrayList<Atom> atoms, double res, double probeR) {
		double minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE, minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;
		try {
			Scanner input = new Scanner(new File(inputFilePath));
			while (input.hasNextLine()) {
				String info = input.nextLine();
				Scanner parser = new Scanner(info);
				parser.next();
				parser.next();
				parser.next();
				parser.next();
				parser.next();
				double x = parser.nextDouble();
				double y = parser.nextDouble();
				double z = parser.nextDouble();

				parser.nextDouble();
				double r = parser.nextDouble();
				minX = Math.min(minX, x - r);
				maxX = Math.max(maxX, x + r);
				minY = Math.min(minY, y - r);
				maxY = Math.max(maxY, y + r);
				minZ = Math.min(minZ, z - r);
				maxZ = Math.max(maxZ, z + r);
				atoms.add(new Atom(info, x, y, z, r));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found. Exiting.");
			System.exit(1);
		}
		return new Universe(res, probeR, minX, maxX, minY, maxY, minZ, maxZ);
	}

	// Shows the proper format for the command line arguments
	public static void inputError() {
		System.out
				.println("Usage: mycode -i inputfile -o outputfile -probe 1.7 -resolution 0.25");
		System.exit(1);
	}

	// Creates the output file.
	public static void printOutputFile(String outputFile,
			ArrayList<Atom> loadedAtoms, Universe universe) {
		try {
			PrintWriter out = new PrintWriter(new File(outputFile));
			for (Atom atom : loadedAtoms)
				out.println(atom);
			universe.print(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error printing output file.");
		}
	}
}
