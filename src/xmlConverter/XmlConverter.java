package xmlConverter;

import javax.swing.SwingUtilities;

/**
 * Class with main method.
 * @author Mikael Dahlin
 *
 */
public class XmlConverter {

	/**
	 * Main method.
	 * The starting point of the program.
	 * @param args
	 */
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		SwingUtilities.invokeLater(ui);
	}

}
