/**
 * 
 */
package pvt.filedetails.directoryprocessor;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import pvt.filedetails.utility.Enums.ProcessingStatus;

/**
 * @author Sahil Jain
 *
 */
public class Processor {
	private File parentDirectory;
	private SharedResources sharedResources;

	/**
	 * @return the sharedResources
	 */
	public SharedResources getSharedResources() {
		return sharedResources;
	}

	/**
	 * @return the directory
	 */
	public File getParentDirectory() {
		return parentDirectory;
	}

	/**
	 * @param directory the directory to set
	 */
	public void setParentDirectory(File parentDirectory) {
		this.parentDirectory = parentDirectory;
	}

	public Processor(File directory) {
		this.parentDirectory = directory;
		this.sharedResources = new SharedResources();
	}

	public String processParentDirectory() {
		if (this.parentDirectory.exists() && !this.parentDirectory.isFile()) {
			ProcessorRunnable processorRunnable = new ProcessorRunnable(this.sharedResources, this.parentDirectory);
			ExecutorService fixedThreadPool = this.sharedResources.getFixedThreadPool();
			Future<?> future = fixedThreadPool.submit(processorRunnable);
			this.sharedResources.setProcessingStatus(ProcessingStatus.PROCESSING);
			System.out.println("Threads Launched for processing....");
			try {
				this.sharedResources.setProcessingStatus((ProcessingStatus) future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		} else {
			this.sharedResources.setProcessingStatus(ProcessingStatus.FAILED);
		}
		String processingStatus = this.sharedResources.getProcessingStatus().getProcessingStatus();
		System.out.println(processingStatus);
		return processingStatus;
	}
}
