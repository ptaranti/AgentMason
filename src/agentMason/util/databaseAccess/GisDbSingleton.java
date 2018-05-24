package agentMason.util.databaseAccess;


import java.sql.*;

import org.postgis.*;
import org.postgresql.*;


public class GisDbSingleton {

	

		private static GisDbSingleton singleton = null;
		private PGConnection pgConn;
		private java.sql.Connection conn;

		private GisDbSingleton() {

			try {
				/*
				 * Load the JDBC driver and establish a connection.
				 */
				Class.forName("org.postgresql.Driver");
				String url = "jdbc:postgresql://localhost:5432/gisbase";
				conn = DriverManager.getConnection(url, "postgres", "postgres");
				pgConn = (PGConnection) conn;

				/*
				 * Add the geometry types to the connection. Note that you
				 * must cast the connection to the pgsql-specific connection * implementation before calling the addDataType() method.
				 */
				DriverWrapper.addGISTypes80(pgConn);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public static GisDbSingleton getInstance() {
			if (singleton == null) {
				singleton = new GisDbSingleton();
			}
			return singleton;
		}

		/**
		 * @return the conn
		 */
		public java.sql.Connection getConn() {
			return conn;
		}


	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
