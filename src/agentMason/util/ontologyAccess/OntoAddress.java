package agentMason.util.ontologyAccess;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

import org.apache.jena.vocabulary.OWL;



/**
 * 
 * Classe de constantes de enderecamento da ontologia. 
 * Operacional
 * 
 * SUBSTITUA O CAMPO ontologyNameOWL, ATRIBUINDO O NOME DA ONTOLOGIA USADA.
 * 
 * @author Pier
 * @since abr 2007
 */
public class OntoAddress {

	public static final String ontologyNameOWL = PropertiesLoaderImpl.ONTOLOGY_NAME_OWL;
	public static final String ontologyNS = ("http://www.owl-ontologies.com/"
			+ ontologyNameOWL + "#");
	public static final String ontologyURI = ("http://www.owl-ontologies.com/" + ontologyNameOWL);
	private static String ontoAddressStatic;

	/**
	 * @return a Sring witch the *.owl address
	 */
	

	
	public static String getOntoAddress() {
		File fileRegister =  new File(ontologyNameOWL);
		try {
			return fileRegister.toURI().toURL().toString();
		} catch (MalformedURLException e) {
			System.out.println("Ontology not present \n Exiting simulation");
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return null;
		
		// a barra remete a raiz do diretorio de execucao (acima de src)
//		if (ontoAddressStatic == null) ontoAddressStatic = OntoAddress.class.getResource("/" + ontologyNameOWL).toString();
//		return ontoAddressStatic;


	}

	/**
	 * 
	 * class test
	 * @param args
	 */
	public static void main(String[] args) {

	
		//System.out.println("ontologyNameOWL\n: " + ontologyNameOWL);
		//System.out.println("ontologyURI\n: " + ontologyURI);
		//System.out.println("ontologyNS\n: " + ontologyNS);
		System.out.println("ontologyFileAddress\n: " + getOntoAddress());
	}

}
