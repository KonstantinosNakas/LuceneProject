//import java.awt.EventQueue;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;

public class ResultsPanel {

	JFrame frame;
	JEditorPane textPane;
	JTextArea textArea = new JTextArea();
	JScrollPane jsp;
	/**
	 * Launch the application.
	 */
	
	
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public ResultsPanel() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 749, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textArea.setBackground(new Color(253, 245, 230));
		jsp = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(jsp, BorderLayout.CENTER);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 30));
		textArea.setText(textArea.getText() + "		 	THE RESULTS OF THE QUERY		\n");
	}

	public void addText(String text) throws BadLocationException {
		textArea.setText(textArea.getText() + text);
	}
	
	public void clearText() {
		textArea.setText(null);
	}

}
