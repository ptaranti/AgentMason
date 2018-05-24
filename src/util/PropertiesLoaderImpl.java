package util;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;




 /* Oferece metodos estaticos para leitura das propriedades do arquivo de
 * propriedades acessado pela classe PropertiesLoader
 * 
 * @author taranti
 * 
 */
public class PropertiesLoaderImpl {
	
	
	

	private static PropertiesLoader loader = new PropertiesLoader();
	
	
	public static final List<String> UNITS = findUnits();
	
	public static final double NORTH_LIMIT = findNothLimit();	
	public static final double SOUTH_LIMIT = findSouthLimit();
	public static final double EAST_LIMIT = findEastLimit();
	public static final double WEST_LIMIT = findWestLimit();


	

	private static String getValor(String chave) {
		return (String) loader.getValor(chave);
	}

	
	
	

	private static double findWestLimit() {
		return Double.parseDouble(getValor("WEST_LIMIT"));
	}





	private static double findEastLimit() {
		return Double.parseDouble(getValor("EAST_LIMIT"));
	}





	private static double findSouthLimit() {
		return Double.parseDouble(getValor("SOUTH_LIMIT"));
	}





	private static double findNothLimit() {
		return Double.parseDouble(getValor("NORTH_LIMIT"));
	}





	public static void main(String[] args) {
	
		
		
		System.out.println(PropertiesLoaderImpl.getValor("UNITS"));
		System.out.println(findUnits());
		System.out.println("\n\nAll units :");
		for(String str : findUnits()){
			
			System.out.println("teste-> " +str.length());
		}
	}

	private static List<String> findUnits() {
	
		String units = PropertiesLoaderImpl.getValor("UNITS");
		
		List<String> listOfUnits =  new Vector<String>(Arrays.asList(units.split(",")));		
	
		return listOfUnits;
	}
}

// / para usar:
// PropertiesLoaderImpl.getValor("SUA VARIAVEL")
// find....

