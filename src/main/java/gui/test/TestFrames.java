package gui.test;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TestFrames extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4706963880497286739L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrames frame = new TestFrames();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestFrames() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800,640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		
	}

}
