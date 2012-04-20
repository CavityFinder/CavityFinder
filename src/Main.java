import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// for testing
		// remove this line when building
		args = new String[] { "-i", "~/Desktop/input.pdb", "-o",
				"~/Desktop/output.pdb", "-probe", "1.7", "-resolution", "0.25" };
		ArrayList<Atom> atoms = new ArrayList<Atom>();
		byte[][][] atomArray;
		String inputFilePath = null;
		String outputFilePath = null;
		double probeSize = 0;
		double resolution = 0;
		double minX, maxX, minY, maxY, minZ, maxZ;
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
		try {
			Scanner input = new Scanner(new File(inputFilePath));
			// atoms.add(new Atom(...));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found. Exiting.");
		}
	}
	
	public static void loadFile(){}

	public static void inputError() {
		System.out
				.println("Usage: mycode -i inputfile -o outputfile -probe 1.7 -resolution 0.25");
		System.exit(1);
	}
	
	public static void eraseProbeAtoms(){
		
	}

	public static void eraseNonCavities(byte[][][] atoms, int cx, int cy, int cz) {
		if (atoms[cx][cy][cz] == 0)
			return;
		atoms[cx][cy][cz]=1;
		for (int i = cx - 1; i <= cx + 1; i++) {
			for (int j = cy - 1; j < cy + 1; j++) {
				for (int k = cz - 1; k < cz + 1; k++) {
					if (!(i == cx && j == cy && k == cz) && i >= 0 && j >= 0
							&& k >= 0 && i < atoms.length
							&& j < atoms[0].length && k < atoms[0][0].length) {
						eraseNonCavities(atoms,i,j,k);
					}
				}
			}
		}
	}
}
