/**
 * 
 */
package pvt.filedetails.swing;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import pvt.filedetails.dal.GUIData;
import pvt.filedetails.directoryprocessor.Processor;
import pvt.filedetails.utility.Enums.DialogueBoxType;
import pvt.filedetails.utility.Enums.FileFolder;
import pvt.filedetails.utility.Enums.MenuItems;
import pvt.filedetails.utility.Enums.ProcessingStatus;
import pvt.filedetails.utility.FileUtility;
import pvt.filedetails.utility.SwingUtility;

/**
 * @author Sahil Jain
 *
 */
public class ExplorerWindow {

	private JFrame frameDirectoryExplorer;
	private JTable jTable;
	private JLabel lblOpenedDirectoryValue;
	private GUIData guiData;
	private String currentOpenDirectoryPath;
	private static final String[] COLUMN = { "Name", "Path", "Type", "Size", "File Count" };

	/**
	 * Launch the application.
	 */
	public static void launchExplorerWindow(Processor processor) {
		startDirectoryProcessing(processor);
		EventQueue.invokeLater(() -> {
			try {
				ExplorerWindow window = new ExplorerWindow(processor);
				window.frameDirectoryExplorer.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static void startDirectoryProcessing(Processor processor) {
		Runnable runnable = processor::processParentDirectory;
		new Thread(runnable).start();
	}

	/**
	 * Create the application.
	 */
	public ExplorerWindow(Processor processor) {
		guiData = new GUIData(processor.getSharedResources());
		this.currentOpenDirectoryPath = processor.getParentDirectory().getAbsolutePath();
		initialize(processor);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Processor processor) {
		this.frameDirectoryExplorer = new JFrame();
		this.frameDirectoryExplorer.setTitle("Directory Explorer");
		this.frameDirectoryExplorer.setBounds(100, 100, 1215, 754);
		this.frameDirectoryExplorer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frameDirectoryExplorer.setResizable(false);
		this.frameDirectoryExplorer.getContentPane().setLayout(null);

		this.frameDirectoryExplorer.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				clearDataAndShutDownThreadPool(processor, true);
			}
		});

		JPanel panelInformation = new JPanel();
		panelInformation.setBackground(SystemColor.control);
		panelInformation.setBounds(10, 10, 1191, 94);
		this.frameDirectoryExplorer.getContentPane().add(panelInformation);
		panelInformation.setLayout(null);

		JLabel lblDir = new JLabel("Directory: ");
		lblDir.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		lblDir.setBounds(10, 10, 87, 24);
		panelInformation.add(lblDir);

		JLabel lblStatus = new JLabel("Status: ");
		lblStatus.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		lblStatus.setBounds(10, 57, 87, 24);
		panelInformation.add(lblStatus);

		JLabel lblDirectory = new JLabel(processor.getParentDirectory().getAbsolutePath());
		lblDirectory.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		lblDirectory.setBounds(107, 10, 1074, 24);
		panelInformation.add(lblDirectory);

		JLabel lblProcessingStatus = new JLabel(
				processor.getSharedResources().getProcessingStatus().getProcessingStatus());
		lblProcessingStatus.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		lblProcessingStatus.setBounds(107, 57, 227, 24);
		panelInformation.add(lblProcessingStatus);

		JLabel lblOpenedDirectory = new JLabel("Opened Directory:");
		lblOpenedDirectory.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		lblOpenedDirectory.setBounds(344, 57, 155, 24);
		panelInformation.add(lblOpenedDirectory);

		this.lblOpenedDirectoryValue = new JLabel(this.currentOpenDirectoryPath);
		this.lblOpenedDirectoryValue.setToolTipText(this.currentOpenDirectoryPath);
		this.lblOpenedDirectoryValue.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
		this.lblOpenedDirectoryValue.setBounds(509, 57, 672, 24);
		panelInformation.add(this.lblOpenedDirectoryValue);

		Runnable updateStatusRunnable = () -> {
			lblProcessingStatus.setText(processor.getSharedResources().getProcessingStatus().getProcessingStatus());
			lblProcessingStatus.updateUI();
			while (!processor.getSharedResources().getProcessingStatus().equals(ProcessingStatus.COMPLETED)) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			lblProcessingStatus.setText(processor.getSharedResources().getProcessingStatus().getProcessingStatus());
			lblProcessingStatus.updateUI();
			Set<String[]> folderContent = guiData.getFolderContent(processor.getParentDirectory().getAbsolutePath());
			updateTableData(folderContent);
		};
		new Thread(updateStatusRunnable).start();

		this.jTable = this.getJTable();
		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setBounds(10, 156, 1181, 528);
		this.frameDirectoryExplorer.getContentPane().add(scrollPane);

		JPanel panelOperations = new JPanel();
		panelOperations.setBounds(10, 114, 1181, 32);
		this.frameDirectoryExplorer.getContentPane().add(panelOperations);
		panelOperations.setLayout(null);

		JButton btnCreateFolder = new JButton("Create Folder");
		btnCreateFolder.addActionListener(
				actionEvent -> createFileFolderOperation(processor, updateStatusRunnable, FileFolder.FOLDER));
		btnCreateFolder.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnCreateFolder.setBounds(0, 0, 150, 32);
		panelOperations.add(btnCreateFolder);

		JButton btnCreateFile = new JButton("Create File");
		btnCreateFile.addActionListener(
				actionEvent -> createFileFolderOperation(processor, updateStatusRunnable, FileFolder.FILE));
		btnCreateFile.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnCreateFile.setBounds(160, 0, 131, 32);
		panelOperations.add(btnCreateFile);

		JButton btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.addActionListener(actionEvent -> deleteOperation(processor, updateStatusRunnable));
		btnDeleteSelected.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnDeleteSelected.setBounds(301, 0, 174, 32);
		panelOperations.add(btnDeleteSelected);

		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(actionEvent -> renameOperation(processor, updateStatusRunnable));
		btnRename.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnRename.setBounds(485, 0, 122, 32);
		panelOperations.add(btnRename);

		JMenuBar menuBar = prepareMenuBar(processor);
		panelOperations.add(menuBar);

		JButton btnReprocess = new JButton("Reprocess");
		btnReprocess.addActionListener(actionEvent -> {
			updateCurrentDirectoryLabel(processor.getParentDirectory().getAbsolutePath());
			reprocessDirectory(processor, updateStatusRunnable);
		});
		btnReprocess.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnReprocess.setBounds(617, 0, 122, 32);
		panelOperations.add(btnReprocess);

		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(actionEvent -> {
			Set<String[]> folderContent = guiData.getFolderContent(processor.getParentDirectory().getAbsolutePath());
			updateTableData(folderContent);
			updateCurrentDirectoryLabel(processor.getParentDirectory().getAbsolutePath());
		});
		btnHome.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnHome.setBounds(749, 0, 122, 32);
		panelOperations.add(btnHome);

		JLabel lblDesignedAndDeveloped = new JLabel("Designed and Developed by Sahil Jain");
		lblDesignedAndDeveloped.setToolTipText("Designed and Developed by Sahil Jain");
		lblDesignedAndDeveloped.setFont(new Font("Segoe Print", Font.BOLD, 13));
		lblDesignedAndDeveloped.setBounds(10, 683, 1181, 24);
		frameDirectoryExplorer.getContentPane().add(lblDesignedAndDeveloped);
	}

	/**
	 * This method prepares menu bar and defines their operations
	 * 
	 * @param processor
	 * @return instance of JMenuBar
	 */
	private JMenuBar prepareMenuBar(Processor processor) {
		ActionListener menutItemActionListener = actionEvent -> {
			System.out.println("Menu clicked");
			String actionCommand = actionEvent.getActionCommand();
			MenuItems menuItems = MenuItems.getMenuItemEnum(actionCommand);
			switch (menuItems) {
			case PROCESS_ANOTHER_DIRECTORY:
				SwingUtility.displayPopUpMessage("Tab Development in progress", DialogueBoxType.ERROR);
				break;
			case PROCESS_OTHER_DIRECTORY:
				processAnotherDirectory(processor);
				break;
			case VIEW_ALL_FILES:
				updateCurrentDirectoryLabel(processor.getParentDirectory().getAbsolutePath());
				this.updateTableData(this.guiData.getAllFileDetails());
				break;
			case VIEW_ALL_FOLDERS:
				updateCurrentDirectoryLabel(processor.getParentDirectory().getAbsolutePath());
				this.updateTableData(this.guiData.getAllFoldersDetails());
				break;
			case VIEW_CONTENT:
				updateCurrentDirectoryLabel(processor.getParentDirectory().getAbsolutePath());
				this.updateTableData(this.guiData.getFolderContent(processor.getParentDirectory().getAbsolutePath()));
				break;
			default:
				System.out.println("Invalid menu type. Report this issue!!");
				break;
			}
		};

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		menuBar.setBackground(Color.LIGHT_GRAY);
		menuBar.setBounds(1114, 0, 65, 32);
		menuBar.setToolTipText("Select View");

		JMenu mnMenu = new JMenu("Menu");
		mnMenu.setBackground(Color.LIGHT_GRAY);
		mnMenu.setHorizontalAlignment(SwingConstants.CENTER);
		mnMenu.setBounds(1114, 0, 65, 32);
		mnMenu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

		JMenuItem mntmViewContent = new JMenuItem(MenuItems.VIEW_CONTENT.getItemValue());
		mntmViewContent.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		mntmViewContent.addActionListener(menutItemActionListener);
		mnMenu.add(mntmViewContent);

		JMenuItem mntmViewAllFiles = new JMenuItem(MenuItems.VIEW_ALL_FILES.getItemValue());
		mntmViewAllFiles.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		mntmViewAllFiles.addActionListener(menutItemActionListener);
		mnMenu.add(mntmViewAllFiles);

		JMenuItem mntmViewAllFolders = new JMenuItem(MenuItems.VIEW_ALL_FOLDERS.getItemValue());
		mntmViewAllFolders.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		mntmViewAllFolders.addActionListener(menutItemActionListener);
		mnMenu.add(mntmViewAllFolders);

		JMenuItem mntmProcessOther = new JMenuItem(MenuItems.PROCESS_OTHER_DIRECTORY.getItemValue());
		mntmProcessOther.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		mntmProcessOther.addActionListener(menutItemActionListener);
		mnMenu.add(mntmProcessOther);

		JMenuItem mntmProcessAnother = new JMenuItem(MenuItems.PROCESS_ANOTHER_DIRECTORY.getItemValue());
		mntmProcessAnother.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		mntmProcessAnother.addActionListener(menutItemActionListener);
		mnMenu.add(mntmProcessAnother);

		menuBar.add(mnMenu);
		return menuBar;
	}

	/**
	 * This method reprocesses the directory and launches a thread for updating
	 * table
	 * 
	 * @param processor
	 * @param updateStatusRunnable
	 */
	private void reprocessDirectory(Processor processor, Runnable updateStatusRunnable) {
		DefaultTableModel model = (DefaultTableModel) getJTable().getModel();
		if (model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		clearDataAndShutDownThreadPool(processor, false);
		this.jTable.setModel(model);
		model.fireTableDataChanged();
		processor.getSharedResources().setProcessingStatus(ProcessingStatus.PENDING);
		startDirectoryProcessing(processor);
		new Thread(updateStatusRunnable).start();
	}

	/**
	 * This method performs process another directory by clearing all data
	 * structures and terminating all threads and calls input dir dialogue box
	 * 
	 * @param processor
	 */
	private void processAnotherDirectory(Processor processor) {
		clearDataAndShutDownThreadPool(processor, true);
		frameDirectoryExplorer.dispose();
		InputDirectoryDialogue.launchInputDirectoryDialogue();
	}

	/**
	 * This method updates table data based on the given list of new data
	 * 
	 * @param listNewData
	 */
	private void updateTableData(Set<String[]> listNewData) {
		System.out.println(listNewData.size());
		DefaultTableModel model = (DefaultTableModel) getJTable().getModel();
		Iterator<String[]> iterator = listNewData.iterator();
		if (model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		while (iterator.hasNext()) {
			model.addRow(iterator.next());
		}
		this.jTable.setModel(model);
		model.fireTableDataChanged();
	}

	/**
	 * This method return an instance of JTable with all defined properties and
	 * columns.
	 * 
	 * @return JTable
	 */
	private JTable getJTable() {
		if (this.jTable == null) {
			this.jTable = new JTable() {
				private static final long serialVersionUID = 7739801596675122989L;

				@Override
				public boolean isCellEditable(int nRow, int nCol) {
					return false;
				}
			};
			DefaultTableModel contactTableModel = (DefaultTableModel) this.jTable.getModel();
			contactTableModel.setColumnIdentifiers(COLUMN);
			this.jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
			defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			this.jTable.getColumn("Type").setCellRenderer(defaultTableCellRenderer);
			this.jTable.getColumn("Size").setCellRenderer(defaultTableCellRenderer);
			this.jTable.getColumn("File Count").setCellRenderer(defaultTableCellRenderer);

			this.jTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent mouseEvent) {
					JTable table = (JTable) mouseEvent.getSource();
					Point point = mouseEvent.getPoint();
					int row = table.rowAtPoint(point);
					if (!mouseEvent.isConsumed() && mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1
							&& table.getSelectedColumn() == 1) {
						String path = table.getModel().getValueAt(row, 1).toString();
						System.out.println("Double click: " + path);
						mouseEvent.consume();
						File file = new File(path);

						if (file.isFile()) {
							Desktop desktop = Desktop.getDesktop();
							// Open a file using the default program for the file type.
							try {
								desktop.open(new File(path));
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							updateCurrentDirectoryLabel(path);
							updateTableData(guiData.getFolderContent(currentOpenDirectoryPath));
						}
					}
				}
			});
		}
		return this.jTable;
	}

	/**
	 * This method updates current dir label text and tool tip
	 * 
	 * @param path
	 */
	private void updateCurrentDirectoryLabel(String path) {
		currentOpenDirectoryPath = path;
		lblOpenedDirectoryValue.setText(currentOpenDirectoryPath);
		lblOpenedDirectoryValue.setToolTipText(currentOpenDirectoryPath);
		lblOpenedDirectoryValue.updateUI();
	}

	/**
	 * This method performs rename operation of selected file/folder. Promts user
	 * for reprocess if required.
	 * 
	 * @param processor
	 * @param updateStatusRunnable
	 */
	private void renameOperation(Processor processor, Runnable updateStatusRunnable) {
		List<String> selectedFilePaths = SwingUtility.getSelectedFilePaths(jTable);
		if (selectedFilePaths.isEmpty()) {
			SwingUtility.displayPopUpMessage("No row is selected", DialogueBoxType.ERROR);
		} else if (selectedFilePaths.size() > 1) {
			SwingUtility.displayPopUpMessage("Select only single row", DialogueBoxType.ERROR);
		} else {
			String path = selectedFilePaths.get(0);
			File file = new File(path);
			String newName = SwingUtility.displayPopUpQuestionDialogue("Enter new name", "Rename");
			String newPath = new File(file.getParent(), newName).getAbsolutePath();
			if (FileUtility.isValidPathString(newPath)) {
				boolean renameTo = file.renameTo(new File(newPath));
				if (renameTo) {
					int reprocessDir = SwingUtility.displayPopUpMessage(
							"Rename successfull. Do you want to reprocess directory?", DialogueBoxType.CONFIRMATION);
					if (reprocessDir == 0) {
						reprocessDirectory(processor, updateStatusRunnable);
					}
				} else {
					SwingUtility.displayPopUpMessage("Rename Failed.", DialogueBoxType.ERROR);
				}
			} else {
				SwingUtility.displayPopUpMessage("Invalid file/folder name.", DialogueBoxType.ERROR);
			}
		}
	}

	/**
	 * This method performs delete operation. After the file/folder is deleted, it
	 * prompts user if reprocess is required.
	 * 
	 * @param processor
	 * @param updateStatusRunnable
	 */
	private void deleteOperation(Processor processor, Runnable updateStatusRunnable) {
		List<String> selectedFilePaths = SwingUtility.getSelectedFilePaths(jTable);
		if (!selectedFilePaths.isEmpty()) {
			int userConfirmation = SwingUtility.displayPopUpMessage(
					"You are about to delete " + selectedFilePaths.size() + " files/folders. Are you sure?",
					DialogueBoxType.CONFIRMATION);
			if (userConfirmation == 0) {
				for (String pathname : selectedFilePaths) {
					File file = new File(pathname);
					FileUtility.deleteGivenFileFolder(file);
				}
				int reprocessSelected = SwingUtility.displayPopUpMessage(
						"Files/Folders successfully deleted. Do you want to reprocess directory?",
						DialogueBoxType.CONFIRMATION);
				if (reprocessSelected == 0) {
					reprocessDirectory(processor, updateStatusRunnable);
				}
			} else {
				System.out.println("User Declined deletion");
			}
		} else {
			SwingUtility.displayPopUpMessage("No files/folders selected", DialogueBoxType.ERROR);
		}
	}

	/**
	 * This method clears all the data and shuts down thread pool if
	 * termkinateThreadPool is true
	 * 
	 * @param processor
	 * @param terminateThreadPool
	 */
	private void clearDataAndShutDownThreadPool(Processor processor, boolean terminateThreadPool) {
		processor.clearDataAndShutDownProcessor(terminateThreadPool);
		guiData.clearData();
		System.out.println("Data cleared");
	}

	/**
	 * This method creates file or folder as per
	 * 
	 * @param processor
	 * @param updateStatusRunnable
	 */
	private void createFileFolderOperation(Processor processor, Runnable updateStatusRunnable, FileFolder fileFolder) {
		String newName = SwingUtility.displayPopUpQuestionDialogue("Name", "Create NEW");
		String newPath = new File(currentOpenDirectoryPath, newName).getAbsolutePath();
		File file = new File(newPath);
		boolean isCreated = false;
		switch (fileFolder) {
		case FILE:
			try {
				isCreated = file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case FOLDER:
			isCreated = file.mkdir();
			break;
		default:
			break;
		}

		if (isCreated) {
			int reprocessDir = SwingUtility.displayPopUpMessage(
					fileFolder + " created. Do you want to reprocess directory?", DialogueBoxType.CONFIRMATION);
			if (reprocessDir == 0) {
				reprocessDirectory(processor, updateStatusRunnable);
			}
		} else {
			SwingUtility.displayPopUpMessage("Error occured while creating folder", DialogueBoxType.ERROR);
		}
	}
}
