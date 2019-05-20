/**
 * 
 */
package pvt.filedetails.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pvt.filedetails.directoryprocessor.Processor;
import pvt.filedetails.utility.Enums.ValidateDirectoryError;
import pvt.filedetails.utility.FileUtility;

/**
 * This class provides a Dialogue box for getting valid input directory for
 * processing. It creates the processor objects and launches ExplorerWindow
 * 
 * @author Sahil Jain
 *
 */
public class InputDirectoryDialogue {

	private JFrame inputDialogueFrame;

	/**
	 * Launch the dialogue window.
	 */
	public static void launchInputDirectoryDialogue() {
		EventQueue.invokeLater(() -> {
			try {
				InputDirectoryDialogue window = new InputDirectoryDialogue();
				window.inputDialogueFrame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the dialogue window.
	 */
	public InputDirectoryDialogue() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.inputDialogueFrame = new JFrame();
		this.inputDialogueFrame.setBounds(100, 100, 694, 287);
		this.inputDialogueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputDialogueFrame.getContentPane().setLayout(null);
		this.inputDialogueFrame.setResizable(false);

		JLabel lblEnterDirectoryPath = new JLabel("Enter Directory Path: ");
		lblEnterDirectoryPath.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		lblEnterDirectoryPath.setBounds(29, 71, 154, 25);
		this.inputDialogueFrame.getContentPane().add(lblEnterDirectoryPath);

		JLabel lblFileExplorer = new JLabel("File Explorer");
		lblFileExplorer.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileExplorer.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		lblFileExplorer.setBounds(182, 10, 258, 31);
		this.inputDialogueFrame.getContentPane().add(lblFileExplorer);

		JTextField inputDirectoryPath = new JTextField();
		inputDirectoryPath.setToolTipText("C:\\Sample\\Directory");
		inputDirectoryPath.setBounds(182, 71, 474, 25);
		this.inputDialogueFrame.getContentPane().add(inputDirectoryPath);
		inputDirectoryPath.setColumns(10);

		JLabel lblValidPathMessage = new JLabel("");
		lblValidPathMessage.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		lblValidPathMessage.setBounds(182, 113, 310, 31);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(actionEvent -> {
			String directory = inputDirectoryPath.getText().trim();
			File directoryFile = new File(directory);
			ValidateDirectoryError validateDirectory = FileUtility.validateDirectory(directory);
			if (validateDirectory.equals(ValidateDirectoryError.VALID_DIRECTORY)) {
				Processor processor = new Processor(directoryFile);
				ExplorerWindow.launchExplorerWindow(processor);
				inputDialogueFrame.dispose();
			} else {
				lblValidPathMessage.setForeground(Color.RED);
				lblValidPathMessage.setText(FileUtility.validateDirectory(directory).getErrorMessage());
				lblValidPathMessage.updateUI();
			}
		});
		btnSubmit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
		btnSubmit.setBounds(361, 170, 143, 47);
		this.inputDialogueFrame.getContentPane().add(btnSubmit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(actionEvent -> System.exit(0));

		btnCancel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
		btnCancel.setBounds(513, 170, 143, 47);
		this.inputDialogueFrame.getContentPane().add(btnCancel);

		JButton btnValidatePath = new JButton("Validate Path");
		btnValidatePath.addActionListener(actionEvent -> {
			lblValidPathMessage.setForeground(Color.GREEN);
			lblValidPathMessage
					.setText(FileUtility.validateDirectory(inputDirectoryPath.getText().trim()).getErrorMessage());
			lblValidPathMessage.updateUI();
		});
		btnValidatePath.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnValidatePath.setBounds(513, 113, 143, 31);
		this.inputDialogueFrame.getContentPane().add(btnValidatePath);
		this.inputDialogueFrame.getContentPane().add(lblValidPathMessage);

		JLabel label = new JLabel("Designed and Developed by Sahil Jain");
		label.setToolTipText("Designed and Developed by Sahil Jain");
		label.setFont(new Font("Segoe Print", Font.BOLD, 13));
		label.setBounds(10, 227, 670, 23);
		this.inputDialogueFrame.getContentPane().add(label);
	}
}
