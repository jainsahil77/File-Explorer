/**
 * 
 */
package pvt.filedetails.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;

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
	JScrollPane scrollPane;
	private GUIData guiData;
	private static final String COLUMN[] = { "Name", "Path", "Type", "Size", "File Count" };

	/**
	 * Launch the application.
	 */
	public static void launchExplorerWindow(Processor processor) {
		startDirectoryProcessing(processor);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExplorerWindow window = new ExplorerWindow(processor);
					window.frameDirectoryExplorer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void startDirectoryProcessing(Processor processor) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				processor.processParentDirectory();
			}
		};
		new Thread(runnable).start();
	}

	/**
	 * Create the application.
	 */
	public ExplorerWindow(Processor processor) {
		guiData = new GUIData(processor.getSharedResources());
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
		this.frameDirectoryExplorer.getContentPane().setLayout(null);

		this.frameDirectoryExplorer.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				clearData(processor);
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

		JLabel lblOpenedDirectoryValue = new JLabel("");
		lblOpenedDirectoryValue.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
		lblOpenedDirectoryValue.setBounds(509, 57, 672, 24);
		panelInformation.add(lblOpenedDirectoryValue);

		Runnable updateStatusRunnable = new Runnable() {
			@Override
			public void run() {
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
				List<String[]> folderContent = guiData
						.getFolderContent(processor.getParentDirectory().getAbsolutePath());
				updateTableData(folderContent);
			}
		};
		new Thread(updateStatusRunnable).start();

		List<String[]> folderContent = this.guiData.getFolderContent(processor.getParentDirectory().getAbsolutePath());
		this.updateTableData(folderContent);
		this.jTable = this.getJTable();
		this.updateTableData(folderContent);
		this.scrollPane = new JScrollPane(jTable);
		this.scrollPane.setBounds(10, 156, 1181, 551);
		this.frameDirectoryExplorer.getContentPane().add(scrollPane);

		JPanel panelOperations = new JPanel();
		panelOperations.setBounds(10, 114, 1181, 32);
		this.frameDirectoryExplorer.getContentPane().add(panelOperations);
		panelOperations.setLayout(null);

		JButton btnCreateFolder = new JButton("Create Folder");
		btnCreateFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtility.displayPopUpMessage("Development in progress", DialogueBoxType.ERROR);
			}
		});
		btnCreateFolder.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnCreateFolder.setBounds(0, 0, 150, 32);
		panelOperations.add(btnCreateFolder);

		JButton btnCreateFile = new JButton("Create File");
		btnCreateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtility.displayPopUpMessage("Development in progress", DialogueBoxType.ERROR);
			}
		});
		btnCreateFile.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnCreateFile.setBounds(160, 0, 131, 32);
		panelOperations.add(btnCreateFile);

		JButton btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
							reprocessParentDirectory(processor);
						}
					} else {
						System.out.println("User Declined deletion");
					}
				} else {
					SwingUtility.displayPopUpMessage("No files/folders selected", DialogueBoxType.ERROR);
				}
			}
		});
		btnDeleteSelected.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnDeleteSelected.setBounds(301, 0, 174, 32);
		panelOperations.add(btnDeleteSelected);

		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtility.displayPopUpMessage("Development in progress", DialogueBoxType.ERROR);
			}
		});
		btnRename.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btnRename.setBounds(485, 0, 122, 32);
		panelOperations.add(btnRename);

		JMenuBar menuBar = prepareMenuBar(processor);
		panelOperations.add(menuBar);

		JButton btnReprocess = new JButton("Reprocess");
		btnReprocess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) getJTable().getModel();
				if (model.getRowCount() > 0) {
					model.setRowCount(0);
				}
				jTable.setModel(model);
				model.fireTableDataChanged();
				startDirectoryProcessing(processor);
				new Thread(updateStatusRunnable).start();
			}
		});
		btnReprocess.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnReprocess.setBounds(617, 0, 122, 32);
		panelOperations.add(btnReprocess);
	}

	private JMenuBar prepareMenuBar(Processor processor) {
		ActionListener menutItemActionListener = actionEvent -> {
			System.out.println("Menu clicked");
			String actionCommand = actionEvent.getActionCommand();
			MenuItems menuItems = MenuItems.getMenuItemEnum(actionCommand);
			switch (menuItems) {
			case PROCESS_ANOTHER_DIRECTORY:
				// TODO
				SwingUtility.displayPopUpMessage("Tab Development in progress", DialogueBoxType.ERROR);
				break;
			case PROCESS_OTHER_DIRECTORY:
				reprocessParentDirectory(processor);
				break;
			case VIEW_ALL_FILES:
				this.updateTableData(this.guiData.getAllFileDetails());
				break;
			case VIEW_ALL_FOLDERS:
				this.updateTableData(this.guiData.getAllFoldersDetails());
				break;
			case VIEW_CONTENT:
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

	private void reprocessParentDirectory(Processor processor) {
		clearData(processor);
		frameDirectoryExplorer.dispose();
		InputDirectoryDialogue.launchInputDirectoryDialogue();
	}

	private void updateTableData(List<String[]> listNewData) {
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

	private JTable getJTable() {
		if (this.jTable == null) {
			this.jTable = new JTable() {
				private static final long serialVersionUID = 7739801596675122989L;

				@Override
				public boolean isCellEditable(int nRow, int nCol) {
					return false;
				}
			};
		}
		DefaultTableModel contactTableModel = (DefaultTableModel) this.jTable.getModel();
		contactTableModel.setColumnIdentifiers(COLUMN);
		this.jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
		defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		this.jTable.getColumn("Type").setCellRenderer(defaultTableCellRenderer);
		this.jTable.getColumn("Size").setCellRenderer(defaultTableCellRenderer);
		this.jTable.getColumn("File Count").setCellRenderer(defaultTableCellRenderer);
		return this.jTable;
	}

	private void clearData(Processor processor) {
		processor.shutDownProcessor();
		guiData.clearData();
		System.out.println("Frame closed");
	}
}
