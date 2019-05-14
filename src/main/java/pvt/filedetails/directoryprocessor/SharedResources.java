package pvt.filedetails.directoryprocessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pvt.filedetails.pojo.FileProperties;
import pvt.filedetails.pojo.FolderProperties;
import pvt.filedetails.utility.Enums.ProcessingStatus;
import pvt.filedetails.utility.GenericConfigurations;

/**
 * @author Sahil Jain
 *
 */
public class SharedResources {
	private ExecutorService fixedThreadPool;
	private ProcessingStatus processingStatus;
	private HashMap<String, FolderProperties> folderDetails;
	private HashMap<String, FileProperties> fileDetails;
	private HashMap<String, List<String>> folderContent;

	/**
	 * @return the processingStatus
	 */
	public ProcessingStatus getProcessingStatus() {
		return processingStatus;
	}

	/**
	 * @param processingStatus the processingStatus to set
	 */
	public void setProcessingStatus(ProcessingStatus processingStatus) {
		this.processingStatus = processingStatus;
	}

	/**
	 * @return the fixedThreadPool
	 */
	public ExecutorService getFixedThreadPool() {
		return fixedThreadPool;
	}

	/**
	 * @param fixedThreadPool the fixedThreadPool to set
	 */
	public void setFixedThreadPool(ExecutorService fixedThreadPool) {
		this.fixedThreadPool = fixedThreadPool;
	}

	/**
	 * @return the folderData
	 */
	public Map<String, FolderProperties> getFolderDetails() {
		return folderDetails;
	}

	/**
	 * @return the fileDetails
	 */
	public Map<String, FileProperties> getFileDetails() {
		return fileDetails;
	}

	/**
	 * @return the folderContent
	 */
	public Map<String, List<String>> getFolderContent() {
		return folderContent;
	}

	public SharedResources() {
		this.folderDetails = new HashMap<>();
		this.fileDetails = new HashMap<>();
		this.folderContent = new HashMap<>();
		this.processingStatus = ProcessingStatus.PENDING;
		this.fixedThreadPool = Executors.newFixedThreadPool(GenericConfigurations.getThreadPoolSize());
	}

	public void clearSharedResource() {
		this.folderDetails.clear();
		this.fileDetails.clear();
		this.folderContent.clear();
		this.fixedThreadPool.shutdownNow();
	}
}
