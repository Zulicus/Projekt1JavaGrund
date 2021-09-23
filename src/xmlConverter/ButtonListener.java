package xmlConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class that converts CSV/XML files when a button is pressed.
 * @author Mikael Dahlin
 *
 */
public class ButtonListener implements ActionListener {

	private JButton button;
	private JFileChooser fileChooser;
	
	/**
	 * Constructor
	 * @param button
	 */
	public ButtonListener(JButton button) {
		this.button = button;
		this.fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	/**
	 * ActionListener Method, runs when a button is pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		try {
			// Create a DocumentBuilder.
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			// Check which button was pressed.
			if(event.getSource().toString().contains("CSV to XML")) {
				
				fileChooser.setSelectedFile(new File("SheettobecomeXML.csv"));
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName().endsWith(".csv")) {
					// Declaration of variables.
					BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
					Document doc = dBuilder.newDocument();
					
					String content;
					String[] line;
					Element rootElement = doc.createElement("table");
					Element rowElement;
					Element columnElement;
					
					doc.appendChild(rootElement);
					
					// Loop through the file content and generate XML tags.
					while((content = br.readLine()) != null) {
						rowElement = doc.createElement("row");
						rootElement.appendChild(rowElement);
						line = content.split(",");
						
						for ( int i = 0; i < line.length; i++) {
							columnElement = doc.createElement("column" + (i + 1));
							rowElement.appendChild(columnElement);
							columnElement.appendChild(doc.createTextNode(line[i]));
						}
					}
					
					br.close();
					
					// Save the XML file.
					fileChooser.setSelectedFile(new File("sheet.xml"));
					if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName().endsWith(".xml")) {
						System.out.println("Your file was saved as: " + fileChooser.getSelectedFile().getAbsolutePath());
					} else {
						fileChooser.setSelectedFile(new File("sheet.xml"));
						System.out.println("That is not a XML file!, sheet.xml was auto generated in the program directory.");
					}
					
					// Create the XML file.
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(fileChooser.getSelectedFile());
					transformer.transform(source, result);
					
					// Set read XML button to visible.
					button.setVisible(true);
					
				} else {
					System.out.println("That is not a CSV file!");
				}
				
			} else if(event.getSource().toString().contains("Read XML")){
				// Declaration of variables.
				Document doc = dBuilder.parse(fileChooser.getSelectedFile());
				PrintWriter pw = new PrintWriter(new File(fileChooser.getSelectedFile().getAbsolutePath().replace(".xml", ".csv")));
				String text = "";
				
				// Loop through the XML tree.
				NodeList rows = doc.getElementsByTagName("row");
				for(int i = 0;i < rows.getLength(); i++) {
					Node row = rows.item(i);
				
					if(row.getNodeType()==Node.ELEMENT_NODE) {
						Element rowEl = (Element) row;
						
						NodeList columns = rowEl.getChildNodes();
						for (int j = 0;j < columns.getLength();j++) {
							Node column = columns.item(j);
							
							if(row.getNodeType()==Node.ELEMENT_NODE) {
								Element columnEl = (Element) column;
								
								text += columnEl.getTextContent();
								if ( j < columns.getLength() - 1) {
									text += ",";
								} 
							}
						} 
						text += "\n";
					}
				}
				// Write to file and console.
				pw.write(text);
				pw.close();
				System.out.println(text);
			}else {
				System.out.println("Button not recognized");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
