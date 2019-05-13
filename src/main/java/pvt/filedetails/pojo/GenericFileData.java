/**
 * 
 */
package pvt.filedetails.pojo;

/**
 * @author Sahil Jain
 *
 */
public class GenericFileData {
	private String path;
	private String name;
	private long size;
	private String type;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	public GenericFileData(String name, String path, long size, String type) {
		this.name = name;
		this.path = path;
		this.size = size;
		this.type = type;
	}
}
