/**
 * 
 */
package pvt.filedetails.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * @author Sahil Jain
 *
 */
public class InputDirectoryDialogue {

	private JFrame inputDialogueFrame;
	private JTextField inputDirectoryPath;

	/**
	 * Launch the application.
	 */
	public static void launchInputDirectoryDialogue() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputDirectoryDialogue window = new InputDirectoryDialogue();
					window.inputDialogueFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InputDirectoryDialogue() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		inputDialogueFrame = new JFrame();
		inputDialogueFrame.setBounds(100, 100, 694, 287);
		inputDialogueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputDialogueFrame.getContentPane().setLayout(null);

		JLabel lblEnterDirectoryPath = new JLabel("Enter Directory Path: ");
		lblEnterDirectoryPath.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterDirectoryPath.setBounds(29, 71, 154, 25);
		inputDialogueFrame.getContentPane().add(lblEnterDirectoryPath);

		JLabel lblFileExplorer = new JLabel("File Explorer");
		lblFileExplorer.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileExplorer.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFileExplorer.setBounds(182, 10, 258, 31);
		inputDialogueFrame.getContentPane().add(lblFileExplorer);

		inputDirectoryPath = new JTextField();
		inputDirectoryPath.setToolTipText("C:\\Sample\\Directory");
		inputDirectoryPath.setBounds(182, 71, 474, 25);
		inputDialogueFrame.getContentPane().add(inputDirectoryPath);
		inputDirectoryPath.setColumns(10);

		JLabel lblValidPathMessage = new JLabel("");
		lblValidPathMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblValidPathMessage.setBounds(182, 113, 310, 31);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSubmit.setBounds(361, 193, 143, 47);
		inputDialogueFrame.getContentPane().add(btnSubmit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCancel.setBounds(513, 193, 143, 47);
		inputDialogueFrame.getContentPane().add(btnCancel);

		JButton btnValidatePath = new JButton("Validate Path");
		btnValidatePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblValidPathMessage.setForeground(Color.GREEN);
				lblValidPathMessage
						.setText(FileUtility.validateDirectory(inputDirectoryPath.getText().trim()).getErrorMessage());
				lblValidPathMessage.updateUI();
			}
		});
		btnValidatePath.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnValidatePath.setBounds(513, 113, 143, 31);
		inputDialogueFrame.getContentPane().add(btnValidatePath);

		inputDialogueFrame.getContentPane().add(lblValidPathMessage);
	}
}
