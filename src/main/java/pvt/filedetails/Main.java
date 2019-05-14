package pvt.filedetails;

import java.io.File;
import java.util.Scanner;

import pvt.filedetails.directoryprocessor.Processor;
import pvt.filedetails.swing.InputDirectoryDialogue;
import pvt.filedetails.utility.GenericConfigurations;

/**
 * @author Sahil Jain
 *
 *         Main class for starting the application based on the selected
 *         configuration (Console, GUI desktop or Web) For GUI based desktop
 *         application it launches a dialogue box for input directory
 */
public class Main {
	/**
	 * Main method for starting application and initialization of configurations
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try (Scanner scannerObj = new Scanner(System.in)) {
			boolean initializeConfigurations = GenericConfigurations.initializeConfigurations();

			if (initializeConfigurations) {
				switch (GenericConfigurations.getApplicationType()) {
				case CONSOLE:
					consoleBasedProcess(scannerObj, initializeConfigurations);
					break;
				case GUI:
					InputDirectoryDialogue.launchInputDirectoryDialogue();
					break;
				case REST_API:
					System.out.println("Sorry. Rest api development is still underway...");
					break;
				default:
					System.out.println(
							"Invalid application launch type selected. Allowed application type are 1,2 and 3.");
					break;
				}
			} else {
				System.out.println("Error encountered while initializing configurations");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Console based application start
	 * 
	 * @param scannerObj
	 * @param initializeConfigurations
	 */
	private static void consoleBasedProcess(Scanner scannerObj, boolean initializeConfigurations) {
		System.out.println("Properties initialized: " + initializeConfigurations);
		System.out.println("Enter Directory Path:");
		String directoryPath = scannerObj.nextLine().trim();
		File directoryFileObj = new File(directoryPath);

		Processor processor = new Processor(directoryFileObj);
		String processParentDirectory = processor.processParentDirectory();
		System.out.println(processParentDirectory);
	}
}