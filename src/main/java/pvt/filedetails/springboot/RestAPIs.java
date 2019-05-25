/**
 * 
 */
package pvt.filedetails.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sahil Jain
 *
 */
@RestController
public class RestAPIs {
	@PostMapping(value = "/folderPath")
	public ResponseEntity<Object> createProduct(@RequestBody String folderPath) {
		return new ResponseEntity<>("New directory path processed: " + folderPath, HttpStatus.OK);
	}

	@PostMapping(value = "/products")
	public ResponseEntity<Object> getProduct() {
		return new ResponseEntity<>("lol", HttpStatus.OK);
	}
}
