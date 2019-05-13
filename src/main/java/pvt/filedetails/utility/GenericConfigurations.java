/**
 * 
 */
package pvt.filedetails.utility;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import pvt.filedetails.utility.Enums.ApplicationType;

/**
 * @author Sahil Jain
 *
 */
public class GenericConfigurations {
	private static int threadPoolSize;
	private static ApplicationType applicationType;

	/**
	 * @return the applicationType
	 */
	public static ApplicationType getApplicationType() {
		return applicationType;
	}

	/**
	 * @param applicationType the applicationType to set
	 */
	public static void setApplicationType(int applicationType) {
		switch (applicationType) {
		case 1:
			GenericConfigurations.applicationType = ApplicationType.CONSOLE;
			break;
		case 2:
			GenericConfigurations.applicationType = ApplicationType.GUI;
			break;
		case 3:
			GenericConfigurations.applicationType = ApplicationType.REST_API;
			break;
		default:
			GenericConfigurations.applicationType = ApplicationType.GUI;
		}
	}

	/**
	 * @return the threadPoolSize
	 */
	public static int getThreadPoolSize() {
		return threadPoolSize;
	}

	/**
	 * @param threadPoolSize the threadPoolSize to set
	 */
	public static void setThreadPoolSize(int threadPoolSize) {
		GenericConfigurations.threadPoolSize = threadPoolSize;
	}

	public static boolean initializeConfigurations() {
		boolean isConfigLoaded = false;
		try (AbstractApplicationContext abstractApplicationContext = new FileSystemXmlApplicationContext(
				"./src/main/resources/configurations/GenericConfigurations.xml")) {
			abstractApplicationContext.getBean("Configurations");
			isConfigLoaded = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isConfigLoaded;
	}
}
