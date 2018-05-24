package agentMason.globalRepresentation;

import java.sql.Statement;

import agentMason.util.databaseAccess.GisDbSingleton;

public class GISAccess {

	static public void updateAgentPosition(AgentPosition agentPosition) {

		String INSERT_AGENTPOSITIONGHOST = "insert into agentpositionghost ( \"agentId\", course, \"simulationTime\", speed, coordinates) SELECT \"agentId\", course, \"simulationTime\", speed, coordinates from agentposition where \"agentId\"= '"
				+ agentPosition.agentId + "'";

		String UPDATE_AGENTPOSITION = "update agentposition set \"agentId\"='"
				+ agentPosition.agentId + "', course=" + agentPosition.courseRad
				+ ", \"simulationTime\"=" + agentPosition.simulationTime
				+ ", speed=" + agentPosition.speed + ", coordinates= ST_GeomFromText('POINT("
				+ agentPosition.coordinates.x + " "
				+ agentPosition.coordinates.y + ")') where \"agentId\"= '"
				+ agentPosition.agentId + "'";

		try {
			java.sql.Connection conn = GisDbSingleton.getInstance().getConn();
			Statement s = conn.createStatement();

			s.executeUpdate(INSERT_AGENTPOSITIONGHOST);
			s.executeUpdate(UPDATE_AGENTPOSITION);
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public void insertAgentPosition(AgentPosition agentPosition) {

		String INSERT_AGENTPOSITION = "insert into agentposition( \"agentId\", course, \"simulationTime\", speed, coordinates) values ('"
				+ agentPosition.agentId
				+ "', "
				+ agentPosition.courseRad
				+ " , "
				+ agentPosition.simulationTime
				+ ", "
				+ agentPosition.speed
				+ ", ST_GeomFromText('POINT("
				+ agentPosition.coordinates.x
				+ " "
				+ agentPosition.coordinates.y + ")'))";

		try {
			java.sql.Connection conn = GisDbSingleton.getInstance().getConn();
			Statement s = conn.createStatement();

			s.executeUpdate(INSERT_AGENTPOSITION);
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public void deleteAgentPosition(AgentPosition agentPosition) {

		String DELETE_AGENTPOSITION = "delete from agentposition where \"agentId\"='"
				+ agentPosition.agentId + "'";

		try {
			java.sql.Connection conn = GisDbSingleton.getInstance().getConn();
			Statement s = conn.createStatement();

			s.executeUpdate(DELETE_AGENTPOSITION);
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public void dropDatabaseTables() {

		try {
			java.sql.Connection conn = GisDbSingleton.getInstance().getConn();
			Statement s = conn.createStatement();

			s.execute("DROP TABLE agentposition");
			s.execute("DROP TABLE agentpositionghost");

			s.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static public void createDatabaseTables() {

		try {
			java.sql.Connection conn = GisDbSingleton.getInstance().getConn();
			Statement s = conn.createStatement();


			s.execute("CREATE TABLE agentposition( ID  SERIAL PRIMARY KEY,  \"agentId\"" 
					+ "character varying NOT NULL, course real NOT NULL,"
					+ "\"simulationTime\" bigint NOT NULL, speed real NOT NULL, coordinates geometry NOT NULL)"
					+ "WITH ( OIDS=FALSE, autovacuum_enabled=true )");
			s.execute("ALTER TABLE agentposition OWNER TO postgres");

			s.execute("CREATE TABLE agentpositionghost ( ID  SERIAL PRIMARY KEY, "
					+ "\"agentId\" character varying NOT NULL, course real NOT NULL,"
					+ "\"simulationTime\" bigint NOT NULL,  speed real NOT NULL,"
					+ "coordinates geometry NOT NULL )"
					+ "WITH ( OIDS=FALSE, autovacuum_enabled=true )");

			s.execute("ALTER TABLE agentpositionghost OWNER TO postgres");

			s.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static double truncate(double value) {  
        return Math.round(value * 1000) / 1000d;  
    }  
	
	public static void main(String[] args) {
		
		GISAccess.dropDatabaseTables();
		
		GISAccess.createDatabaseTables();
		
	}

}
