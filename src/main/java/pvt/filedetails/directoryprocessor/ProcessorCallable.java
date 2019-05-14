/**
 * 
 */
package pvt.filedetails.directoryprocessor;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import pvt.filedetails.pojo.FileProperties;
import pvt.filedetails.pojo.FolderProperties;
import pvt.filedetails.utility.FileUtility;
import pvt.filedetails.utility.Enums.ProcessingStatus;

/**
 * This class implements callable interface and processes the directory
 * provided. It launches a thread from the fixed size thread pool for processing
 * each new sub directory.
 * 
 * @author Sahil Jain
 *
 */
public class ProcessorCallable implements Callable<ProcessingStatus> {
	private SharedResources sharedResources;
	private File folder;

	/**
	 * Constructor
	 * 
	 * @param sharedResources
	 * @param folder
	 */
	public ProcessorCallable(SharedResources sharedResources, File folder) {
		this.sharedResources = sharedResources;
		this.folder = folder;
	}

	@Override
	public ProcessingStatus call() throws Exception {
		return this.processDirectory(this.folder);
	}

	/**
	 * This method processes the input directory. Each file/folder is iterated. If a
	 * file is found, it is processed and if folder is found a new thread is
	 * launched for processing it from the fixed size threadpool
	 * 
	 * @param folder
	 * @return ProcessingStatus of the given directory
	 */
	private ProcessingStatus processDirectory(File folder) {
		File[] files = folder.listFiles();
		List<String> fileNameList = new LinkedList<>();
		int fileCount = files.length;

		this.sharedResources.getFolderDetails().put(folder.getAbsolutePath(),
				new FolderProperties(folder.getPath(), folder.getName(), folder.length(), fileCount));

		File[] fileArray = Arrays.copyOfRange(files, 0, fileCount);
		for (File file : fileArray) {
			fileNameList.add(file.getAbsolutePath());
			if (file.isDirectory()) {
				ProcessorCallable processorRunnable = new ProcessorCallable(this.sharedResources, file);
				this.sharedResources.getFixedThreadPool().submit(processorRunnable);
			} else {
				this.processFile(file);
			}
		}
		this.sharedResources.getFolderContent().put(folder.getAbsolutePath(), fileNameList);
		return ProcessingStatus.COMPLETED;
	}

	/**
	 * This method processes the given file
	 * 
	 * @param file
	 */
	private void processFile(File file) {
		long fileSize = file.length();
		String fileName = file.getName();
		this.sharedResources.getFileDetails().put(file.getAbsolutePath(),
				new FileProperties(file.getPath(), fileName, fileSize, FileUtility.getFileExtension(fileName)));
	}
}
