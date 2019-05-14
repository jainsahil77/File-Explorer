package pvt.filedetails.pojo;

/**
 * This class contains Folder properties and extends GenericProperties
 * 
 * @author Sahil Jain
 *
 */
public class FolderProperties extends GenericFileData {
	private int fileCount;

	/**
	 * Constructor
	 * 
	 * @param folderPath
	 * @param folderName
	 * @param folderSize
	 * @param fileCount
	 */
	public FolderProperties(String folderPath, String folderName, long folderSize, int fileCount) {
		super(folderName, folderPath, folderSize, "Folder");
		this.fileCount = fileCount;
	}

	/**
	 * @return the fileCount
	 */
	public int getFileCount() {
		return fileCount;
	}

	/**
	 * @param fileCount the fileCount to set
	 */
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	/**
	 * This method provides all folder properties objects array
	 * 
	 * @return String[]
	 */
	public String[] getFolderPropertiesVariableArray() {
		String[] array = { super.getName(), super.getPath(), this.getType(), "", String.valueOf(fileCount) };
		return array;
	}
}
