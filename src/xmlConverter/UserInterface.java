package xmlConverter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * User interface class.
 * @author Mikael Dahlin
 *
 */
public class UserInterface implements Runnable{
	
	private JFrame frame;
	
	/**
	 * Constructor
	 */
	public UserInterface() {
		
	}

	/**
	 *  Run method
	 *  Main method for controlling the program.
	 */
	@Override
	public void run() {
		
		frame = new JFrame("XML_Converter");
		
		frame.setPreferredSize(new Dimension(600, 400));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		addComponents(frame.getContentPane());
		
		frame.pack();
		
		frame.setVisible(true);
	}

	/**
	 * Method for adding components to the frame.
	 * @param container
	 */
	public void addComponents(Container container) {
		
		GridLayout layout = new GridLayout(2, 1);
		
		JButton csvToXmlButton = new JButton("CSV to XML");
		JButton readXmlButton = new JButton("Read XML");
		readXmlButton.setVisible(false);
		
		ButtonListener listener = new ButtonListener(readXmlButton);
		csvToXmlButton.addActionListener(listener);
		readXmlButton.addActionListener(listener);
		
		container.setLayout(layout);
		container.add(csvToXmlButton);
		container.add(readXmlButton);
		
	}
	
}
