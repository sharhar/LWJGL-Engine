package code;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

public class Window extends JFrame {
	private static final long serialVersionUID = -57124330276627930L;
	
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public Window() {
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewProject = new JMenuItem("New Project");
		mnFile.add(mntmNewProject);
		
		JMenuItem mntmOpenProject = new JMenuItem("Open Project");
		mntmOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if(chooser.showOpenDialog(Window.this) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					
					try {
						Main.imageBuffer = ImageIO.read(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					Main.readImage = true;
				}
			}
		});
		mnFile.add(mntmOpenProject);
		
		JMenuItem mntmSaveProject = new JMenuItem("Save Project");
		mntmSaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if(chooser.showOpenDialog(Window.this) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					
					String info = ("EntityName=" + file.getName()) 
							+ ("\nPolyNum=" +Main.shapes.size());
					
					FileUtils.writeSrting(file.getPath() + ".txt", info);
					
					file.mkdirs();
					
					try {
						ImageIO.write(Main.imageBuffer, "png", new File(file.getPath() + "/"  + file.getName() + ".png"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					for(int i = 0;i < Main.shapes.size();i++) {
						String data = "";
						for(int j = 0; j < Main.shapes.get(i).vecs.size();j++) {
							data += ((Main.shapes.get(i).vecs.get(j).x-100.0f)/400.0f) + "-" + ((Main.shapes.get(i).vecs.get(j).y-100)/400.0f) + "\n";
						}
						FileUtils.writeSrting(file.getPath() + "/"  + "PolyData" + i + ".txt", data);
					}
				}
			}
		});
		mnFile.add(mntmSaveProject);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStartShape = new JButton("Start Shape");
		btnStartShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.startShape();
			}
		});
		btnStartShape.setBounds(10, 11, 89, 23);
		contentPane.add(btnStartShape);
		
		JButton btnEndShape = new JButton("End Shape");
		btnEndShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.endShape();
			}
		});
		btnEndShape.setBounds(10, 45, 89, 23);
		contentPane.add(btnEndShape);
		
		JButton btnEditShape = new JButton("Edit Shape");
		btnEditShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnEditShape.setBounds(10, 79, 89, 23);
		contentPane.add(btnEditShape);
		
		JCheckBox chckbxWireframe = new JCheckBox("Wireframe");
		chckbxWireframe.setBounds(10, 109, 97, 23);
		contentPane.add(chckbxWireframe);
		setVisible(true);
	}
}
