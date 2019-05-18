/**
 * 
 */
package pvt.filedetails.utility;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import pvt.filedetails.utility.Enums.DialogueBoxType;

/**
 * @author Sahil Jain
 *
 */
public class SwingUtility {
	private SwingUtility() {
		// Adding a private constructor to hide the implicit public one.
	}

	/**
	 * This method displays a pop up dialogue box based the DialogueBoxType
	 * 
	 * @param message
	 * @param dialogueBoxType
	 * @return integer value returned by dialogue box
	 */
	public static int displayPopUpMessage(String message, DialogueBoxType dialogueBoxType) {
		final JPanel panel = new JPanel();
		int returnValue = -1;
		switch (dialogueBoxType) {
		case CONFIRMATION:
			returnValue = JOptionPane.showConfirmDialog(panel, message, "Confirmation", JOptionPane.YES_NO_OPTION);
			break;
		case WARNING:
			JOptionPane.showMessageDialog(panel, message, "Warning", JOptionPane.WARNING_MESSAGE);
			break;
		case ERROR:
			JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
			break;
		case SUCCESS:
			JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
			break;
		default:
			System.out.println("Invalid dialogue option provided");
			break;
		}
		return returnValue;
	}

	public static String displayPopUpQuestionDialogue(String message, String title) {
		final JPanel panel = new JPanel();
		return JOptionPane.showInputDialog(panel, message, title, JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * This method returns selected paths from the jTable
	 * 
	 * @param jTable
	 * @return List<Paths>
	 */
	public static List<String> getSelectedFilePaths(JTable jTable) {
		List<String> selectRowsList = new LinkedList<>();
		if (jTable.isValid() && jTable.isVisible()) {
			int column = 1;
			int[] selectedRows = jTable.getSelectedRows();
			for (int row : selectedRows) {
				selectRowsList.add(jTable.getModel().getValueAt(row, column).toString());
			}
		}
		return selectRowsList;
	}
}
