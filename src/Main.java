public class Main {
	public static void main(String[] args) {
		//for testing
		args =new String[]{"-i","~/Desktop/input.pdb","-o","~/Desktop/output.pdb","-probe","1.7","-resolution","0.25"};
		String usageString = "Usage: mycode -i inputfile -o outputfile -probe 1.7 -resolution 0.25";
		String inputFilePath = null;
		String outputFilePath = null;
		double probeSize = 0;
		double resolution = 0;
		if(args.length!=8)
			System.out.println(usageString);
		for (int i = 0; i < 8; i += 2) {
			if (args[i].equals("-i")) {
				inputFilePath = args[i+1];
			} else if (args[i].equals("-o")) {
				outputFilePath = args[i+1];
			} else if (args[i].equals("-probe")) {
				probeSize = Double.parseDouble(args[i+1]);
			} else if (args[i].equals("-resolution")) {
				resolution = Double.parseDouble(args[i+1]);
			} else {
				System.out.println(usageString);
			}
		}
		System.out.println(inputFilePath+" "+outputFilePath+" "+probeSize+" "+resolution);
	}
}
