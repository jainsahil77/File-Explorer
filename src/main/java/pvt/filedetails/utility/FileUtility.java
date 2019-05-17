/**
 * 
 */
package pvt.filedetails.utility;

import java.awt.Desktop;
import java.io.File;

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
	 * @return true iff the given path is a directory
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
