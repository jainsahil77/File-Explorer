/**
 * 
 */
package pvt.filedetails.springboot;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pvt.filedetails.directoryprocessor.Processor;
import pvt.filedetails.utility.Enums.ValidateDirectoryError;
import pvt.filedetails.utility.FileUtility;

/**
 * @author Sahil Jain
 *
 */
@RestController
public class RestAPIs {
	@PostMapping(value = "/processDirectory")
	public ResponseEntity<Object> processDirectory(@RequestBody String folderPath) {
		ValidateDirectoryError validateDirectoryError = FileUtility.validateDirectory(folderPath);
		if (validateDirectoryError.equals(ValidateDirectoryError.VALID_DIRECTORY)) {
			Processor processor = new Processor(new File(folderPath));
			Runnable runnable = processor::processParentDirectory;
			new Thread(runnable).start();
			return new ResponseEntity<>("Processing directory: " + folderPath, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error: " + validateDirectoryError.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/products")
	public ResponseEntity<Object> getProduct() {
		return new ResponseEntity<>("lol", HttpStatus.OK);
	}
}
