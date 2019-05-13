/**
 * 
 */
package pvt.filedetails.pojo;

/**
 * @author Sahil Jain
 *
 */
public class FileProperties extends GenericFileData {
	public FileProperties(String filePath, String fileName, long fileSize, String fileType) {
		super(fileName, filePath, fileSize, fileType);
	}

	public String[] getFilePropertiesVariableArray() {
		String[] array = { super.getName(), super.getPath(), this.getType(), String.valueOf(super.getSize()), "" };
		return array;
	}
}
