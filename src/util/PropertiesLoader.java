package util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *Realiza leitura do arquivo de propriedades que configura a simulacao antes do
 * startup
 * 
 * @author taranti
 * 
 */
public class PropertiesLoader {

	private Properties props;
	private String masp = "shipsimulation.properties";
	

	protected PropertiesLoader() {

		// create an instance of properties class

		props = new Properties();

		// try retrieve data from file
		try {

			props.load(new FileInputStream(masp));
			//System.out.println(props);

		}

		// catch exception in case properties file does not exist

		catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected String getValor(String chave) {
		return (String) props.getProperty(chave);
	}
}