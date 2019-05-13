package pvt.filedetails;

import java.io.File;
import java.util.Scanner;

import pvt.filedetails.directoryprocessor.Processor;
import pvt.filedetails.swing.InputDirectoryDialogue;
import pvt.filedetails.utility.GenericConfigurations;

/**
 * @author Sahil Jain
 *
 */
public class Main {
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