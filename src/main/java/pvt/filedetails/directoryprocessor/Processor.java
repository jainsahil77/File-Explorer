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
 * This class consists SharedResources object and starts processing with the
 * given directory
 * 
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

	/**
	 * This method processes the parent directory by submitting the thread using the
	 * fixed size threadpool from shared resources object It also updates the
	 * processing status
	 * 
	 * @return Status message
	 */
	public String processParentDirectory() {
		if (this.parentDirectory.exists() && !this.parentDirectory.isFile()) {
			this.sharedResources.setProcessingStatus(ProcessingStatus.PENDING);
			ProcessorCallable processorRunnable = new ProcessorCallable(this.sharedResources, this.parentDirectory);
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

	/**
	 * Call sharedResources clear data method for clearing maps and terminating
	 * threadpool
	 */
	public void shutDownProcessor() {
		this.sharedResources.clearSharedResource();
	}
}
