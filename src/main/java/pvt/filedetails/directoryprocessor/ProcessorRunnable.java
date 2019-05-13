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
 * @author Sahil Jain
 *
 */
public class ProcessorRunnable implements Callable<ProcessingStatus> {
	private SharedResources sharedResources;
	private File folder;

	public ProcessorRunnable(SharedResources sharedResources, File folder) {
		this.sharedResources = sharedResources;
		this.folder = folder;
	}

	@Override
	public ProcessingStatus call() throws Exception {
		return this.processDirectory(this.folder);
	}

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
				ProcessorRunnable processorRunnable = new ProcessorRunnable(this.sharedResources, file);
				this.sharedResources.getFixedThreadPool().submit(processorRunnable);
			} else {
				this.processFile(file);
			}
		}
		this.sharedResources.getFolderContent().put(folder.getAbsolutePath(), fileNameList);
		return ProcessingStatus.COMPLETED;
	}

	private void processFile(File file) {
		long fileSize = file.length();
		String fileName = file.getName();
		this.sharedResources.getFileDetails().put(file.getAbsolutePath(),
				new FileProperties(file.getPath(), fileName, fileSize, FileUtility.getFileExtension(fileName)));
	}
}
