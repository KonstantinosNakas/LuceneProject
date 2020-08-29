import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;

import org.apache.lucene.queryparser.classic.ParseException;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchingWindow {

	private JFrame frmSearchWindow;
	private JTextField textFieldRestaurant;
	private JTextField textFieldReviews;
	private QueryHandler query;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchingWindow window = new SearchingWindow();
					window.frmSearchWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchingWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		query = new QueryHandler();
		frmSearchWindow = new JFrame();
		frmSearchWindow.getContentPane().setForeground(Color.DARK_GRAY);
		frmSearchWindow.setTitle("Search Window");
		frmSearchWindow.setIconImage(Toolkit.getDefaultToolkit().getImage(SearchingWindow.class.getResource("/search.png")));
		frmSearchWindow.setBounds(100, 100, 1639, 728);
		frmSearchWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSearchWindow.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel ImagePanel = new JPanel();
		ImagePanel.setBackground(new Color(176,196,222));
		frmSearchWindow.getContentPane().add(ImagePanel, BorderLayout.NORTH);
		
		JLabel ImageLabel = new JLabel("");
		ImagePanel.add(ImageLabel);
		ImageLabel.setIcon(new ImageIcon(SearchingWindow.class.getResource("/compsc.png")));
		
		JPanel MainPanel = new JPanel();
		frmSearchWindow.getContentPane().add(MainPanel, BorderLayout.CENTER);
		MainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFunctionallity1 = new JPanel();
		panelFunctionallity1.setBackground(new Color(176,196,222));
		MainPanel.add(panelFunctionallity1, BorderLayout.NORTH);
		panelFunctionallity1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel SearchRestaurantlbl = new JLabel("Search restaurant");
		SearchRestaurantlbl.setFont(new Font("Tahoma", Font.PLAIN, 40));
		SearchRestaurantlbl.setForeground(new Color(0, 0, 0));
		panelFunctionallity1.add(SearchRestaurantlbl);
		
		textFieldRestaurant = new JTextField();
		textFieldRestaurant.setFont(new Font("Tahoma", Font.PLAIN, 40));
		textFieldRestaurant.setHorizontalAlignment(SwingConstants.TRAILING);
		panelFunctionallity1.add(textFieldRestaurant);
		textFieldRestaurant.setColumns(10);
		
		JRadioButton rdbtnStars = new JRadioButton("Stars");
		rdbtnStars.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelFunctionallity1.add(rdbtnStars);
		
		JRadioButton rdbtnReviews = new JRadioButton("Reviews");
		rdbtnReviews.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelFunctionallity1.add(rdbtnReviews);
		
		ButtonGroup group1 = new ButtonGroup();
		group1.add(rdbtnStars);
		group1.add(rdbtnReviews);
		
		JButton btnFull1 = new JButton("Full");
		btnFull1.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnFull1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(textFieldRestaurant.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type a query!!");
				}else {
					String fullText = textFieldRestaurant.getText();
					String myQuery = fullText.split("#")[0];
					String type = fullText.split("#")[1];
					if (rdbtnStars.isSelected()){
						query.DetermineTheSortType("RestaurantsByStars");
					}else if (rdbtnReviews.isSelected()){
						query.DetermineTheSortType("RestaurantsByReviews");
					}else{
						query.DetermineTheSortType("RestaurantsByText");
					}
					try {	
						if (type.equals("location") || type.equals("reviews") || type.equals("mix")) {
							query.RestaurantsQueryFull(myQuery,type);
						}else {
							JOptionPane.showMessageDialog(null, "The searching options are location, reviews and mix!!");
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		panelFunctionallity1.add(btnFull1);
		
		JButton btnRepresent1 = new JButton("Representative");
		btnRepresent1.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnRepresent1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textFieldRestaurant.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type a query!!");
				}else {
					String fullText = textFieldRestaurant.getText();
					String myQuery = fullText.split("#")[0];
					String type = fullText.split("#")[1];
					if (rdbtnStars.isSelected()){
						query.DetermineTheReprType("RestaurantsByStars");
					}else if (rdbtnReviews.isSelected()){
						query.DetermineTheReprType("RestaurantsByReviews");
					}else{
						JOptionPane.showMessageDialog(null, "Please select a representation type!!");
						
					}
					try {
						if(rdbtnStars.isSelected() || rdbtnReviews.isSelected()) {
							if (type.equals("location") || type.equals("reviews") || type.equals("mix")) {
								query.RestaurantsQueryRepresentative(myQuery,type);
							}else {
								JOptionPane.showMessageDialog(null, "The searching options are location, reviews and mix!!");
							}
						}	
					} catch (BadLocationException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		panelFunctionallity1.add(btnRepresent1);
		
		JPanel panelFunctionallity2 = new JPanel();
		panelFunctionallity2.setBackground(new Color(176,196,222));
		MainPanel.add(panelFunctionallity2, BorderLayout.CENTER);
		
		JLabel SearchReviewslbl = new JLabel("Search reviews");
		SearchReviewslbl.setFont(new Font("Tahoma", Font.PLAIN, 40));
		SearchReviewslbl.setForeground(new Color(0, 0, 0));
		panelFunctionallity2.add(SearchReviewslbl);
		
		textFieldReviews = new JTextField();
		textFieldReviews.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelFunctionallity2.add(textFieldReviews);
		textFieldReviews.setColumns(10);
		JRadioButton rdbtnUsefull = new JRadioButton("Usefull");
		rdbtnUsefull.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelFunctionallity2.add(rdbtnUsefull);
		
		JRadioButton rdbtnDate = new JRadioButton("Date");
		rdbtnDate.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelFunctionallity2.add(rdbtnDate);
		
		ButtonGroup group2 = new ButtonGroup();
		group2.add(rdbtnUsefull);
		group2.add(rdbtnDate);
		
		JButton btnFull2 = new JButton("Full");
		btnFull2.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnFull2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textFieldReviews.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type a query!!");
				}else {
					String fullText = textFieldReviews.getText();
					String myQuery = fullText.split("#")[0];
					String type = fullText.split("#")[1];
					if (rdbtnUsefull.isSelected()){
						query.DetermineTheSortType("ReviewsByUsefull");
					}
					else if (rdbtnDate.isSelected()){
						query.DetermineTheSortType("ReviewsByDate");
					}else {
						query.DetermineTheSortType("ReviewsByText");
					}
					try {
						if (type.equals("name") || type.equals("review") || type.equals("mix")) {
							query.ReviewsQueryFull(myQuery,type);
						}else {
							JOptionPane.showMessageDialog(null, "The searching options are name, review and mix!!");
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		panelFunctionallity2.add(btnFull2);
		
		JButton btnRepresent2 = new JButton("Representative");
		btnRepresent2.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnRepresent2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textFieldReviews.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type a query!!");
				}else {
					String fullText = textFieldReviews.getText();
					String myQuery = fullText.split("#")[0];
					String type = fullText.split("#")[1];
					if (rdbtnUsefull.isSelected()){
						query.DetermineTheReprType("ReviewsByUsefull");
					}else if (rdbtnDate.isSelected()){
						query.DetermineTheReprType("ReviewsByDate");
					}else{
						JOptionPane.showMessageDialog(null, "Please select a representation type!!");
					}
					try {
						if(rdbtnUsefull.isSelected() || rdbtnDate.isSelected()) {
							if (type.equals("name") || type.equals("review") || type.equals("mix")) {
								query.ReviewsQueryRepresentative(myQuery,type);
							}else {
								JOptionPane.showMessageDialog(null, "The searching options are name, review and mix!!");
							}
						}	
					} catch (BadLocationException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		panelFunctionallity2.add(btnRepresent2);
	}

}
