/**
 * 
 */
package pvt.filedetails.utility;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import pvt.filedetails.utility.Enums.ValidateDirectoryError;

/**
 * @author Sahil Jain
 *
 */
public class FileUtility {
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

	public static String getFileExtension(String fileName) {
		String extension = "";
		int lastIndexOf = fileName.lastIndexOf('.');
		if (lastIndexOf == -1)
			return extension;
		else
			return fileName.substring(lastIndexOf, fileName.length());
	}
}
