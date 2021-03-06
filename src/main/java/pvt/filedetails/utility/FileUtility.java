/**
 * 
 */
package pvt.filedetails.utility;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import pvt.filedetails.utility.Enums.ValidateDirectoryError;

/**
 * This class contains utility methods for Files
 * 
 * @author Sahil Jain
 *
 */
public class FileUtility {
	private FileUtility() {
		// Adding a private constructor to hide the implicit public one.
	}

	/**
	 * This method validates whether given directory is valid or not
	 * 
	 * @param directory
	 * @return ValidateDirectoryError enum
	 */
	public static ValidateDirectoryError validateDirectory(String directory) {
		File file = new File(directory);
		if (StringUtils.isNotBlank(directory)) {
			if (!file.exists()) {
				return ValidateDirectoryError.DIRECTORY_NOT_FOUND;
			} else {
				if (file.isFile()) {
					return ValidateDirectoryError.IS_FILE;
				} else {
					return ValidateDirectoryError.VALID_DIRECTORY;
				}
			}
		} else {
			return ValidateDirectoryError.INVALID_STRING;
		}
	}

	/**
	 * This method checks if the given string path is valid or not
	 * 
	 * @param path
	 * @return true iff the path is valid
	 */
	public static boolean isValidPathString(String path) {
		try {
			Paths.get(path);
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
		return true;
	}

	/**
	 * This method gets file extension
	 * 
	 * @param fileName
	 * @return extension of the given file name
	 */
	public static String getFileExtension(String fileName) {
		String extension = "";
		int lastIndexOf = fileName.lastIndexOf('.');
		if (lastIndexOf == -1)
			return extension;
		else
			return fileName.substring(lastIndexOf, fileName.length());
	}

	/**
	 * This method deletes the given file/folder.
	 * 
	 * @param file
	 */
	public static void deleteGivenFileFolder(File file) {
		Desktop desktop = Desktop.getDesktop();
		desktop.moveToTrash(file);
	}
}
