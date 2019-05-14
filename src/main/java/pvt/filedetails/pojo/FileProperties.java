/**
 * 
 */
package pvt.filedetails.pojo;

/**
 * This class contains file properties and extends GenericProperties
 * 
 * @author Sahil Jain
 *
 */
public class FileProperties extends GenericFileData {
	/**
	 * Constructor
	 * 
	 * @param filePath
	 * @param fileName
	 * @param fileSize
	 * @param fileType
	 */
	public FileProperties(String filePath, String fileName, long fileSize, String fileType) {
		super(fileName, filePath, fileSize, fileType);
	}

	/**
	 * This method provides all file properties objects array
	 * 
	 * @return String[]
	 */
	public String[] getFilePropertiesVariableArray() {
		String[] array = { super.getName(), super.getPath(), this.getType(), String.valueOf(super.getSize()), "" };
		return array;
	}
}
