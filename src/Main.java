import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// for testing
		// remove this line when building
		args = new String[] { "-i", "src/example_input.pdb", "-o",
				"src/example_output.pdb", "-probe", "1.7", "-resolution",
				"0.25" };
		ArrayList<Atom> atoms = new ArrayList<Atom>();
		Universe universe;
		String inputFilePath = null;
		String outputFilePath = null;
		double probeSize = 0;
		double resolution = 0;
		if (args.length != 8)
			inputError();
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
		System.out.println(inputFilePath + " " + outputFilePath + " "
				+ probeSize + " " + resolution);
		universe = loadFile(inputFilePath, atoms, resolution, probeSize);
		universe.eraseProbeAtoms(atoms);
		universe.eraseNonCavities();
		printOutputFile(outputFilePath, atoms, universe);
	}

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

	public static void inputError() {
		System.out
				.println("Usage: mycode -i inputfile -o outputfile -probe 1.7 -resolution 0.25");
		System.exit(1);
	}

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
