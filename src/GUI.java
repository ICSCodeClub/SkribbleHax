
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class GUI {

	private JFrame frame;
	private JTextField txtBrowserName;
	private JTextField txtInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 366, 251);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblTitle = new JLabel("Harry's SkribbleHax");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Sylfaen", Font.PLAIN, 35));
		frame.getContentPane().add(lblTitle, BorderLayout.NORTH);
		
		JPanel rightPanel = new JPanel();
		frame.getContentPane().add(rightPanel, BorderLayout.WEST);
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[]{96, 0};
		gbl_rightPanel.rowHeights = new int[]{19, 0, 0, 0, 0, 0, 0, 0};
		gbl_rightPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_rightPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		rightPanel.setLayout(gbl_rightPanel);
		
		txtBrowserName = new JTextField();
		GridBagConstraints gbc_txtBrowserName = new GridBagConstraints();
		gbc_txtBrowserName.insets = new Insets(0, 0, 5, 0);
		gbc_txtBrowserName.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtBrowserName.gridx = 0;
		gbc_txtBrowserName.gridy = 0;
		rightPanel.add(txtBrowserName, gbc_txtBrowserName);
		txtBrowserName.setColumns(10);
		
		JLabel lblBrowserName = new JLabel("Name of Browser");
		GridBagConstraints gbc_lblBrowserName = new GridBagConstraints();
		gbc_lblBrowserName.insets = new Insets(0, 0, 5, 0);
		gbc_lblBrowserName.gridx = 0;
		gbc_lblBrowserName.gridy = 1;
		rightPanel.add(lblBrowserName, gbc_lblBrowserName);
		
		JLabel lblOutput = new JLabel("Possibilities:");
		GridBagConstraints gbc_lblOutput = new GridBagConstraints();
		gbc_lblOutput.insets = new Insets(0, 0, 5, 0);
		gbc_lblOutput.gridx = 0;
		gbc_lblOutput.gridy = 3;
		rightPanel.add(lblOutput, gbc_lblOutput);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		rightPanel.add(scrollPane, gbc_scrollPane);
		
		JTextPane txtOutput = new JTextPane();
		scrollPane.setViewportView(txtOutput);
		
		JPanel centerPanel = new JPanel();
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		JLabel lblInput = new JLabel("Known Word");
		
		txtInput = new JTextField();
		txtInput.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtInput.setColumns(10);
		
		JLabel lblHelp = new JLabel("For unknown letters, enter _");
		
		JButton btnRunAlgorith = new JButton("Find Answers");
		btnRunAlgorith.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				System.out.println("aaaa");
	            List<String> answers = Main.findAnswer(txtInput.getText());
	            System.out.println(answers);
	            txtOutput.setText("");
	            for(String ans : answers)
	            	txtOutput.setText(ans+"\n");
	         }
	      });
		
		GroupLayout gl_centerPanel = new GroupLayout(centerPanel);
		gl_centerPanel.setHorizontalGroup(
			gl_centerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_centerPanel.createSequentialGroup()
					.addContainerGap(41, Short.MAX_VALUE)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblInput, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(54)
							.addComponent(btnRunAlgorith))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(53)
							.addComponent(lblHelp)))
					.addGap(32))
		);
		gl_centerPanel.setVerticalGroup(
			gl_centerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_centerPanel.createSequentialGroup()
					.addGap(19)
					.addComponent(lblHelp)
					.addGap(18)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInput))
					.addGap(18)
					.addComponent(btnRunAlgorith)
					.addContainerGap(57, Short.MAX_VALUE))
		);
		centerPanel.setLayout(gl_centerPanel);
	}
}
