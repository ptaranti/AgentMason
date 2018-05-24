package util;

import sim.util.Double2D;

import com.vividsolutions.jts.geom.Coordinate;

public final class GeometryExtra {

	public static Coordinate getRamdonPosition() {
		
		double y = PropertiesLoaderImpl.SOUTH_LIMIT
				+ Math.random()
				* (PropertiesLoaderImpl.NORTH_LIMIT - PropertiesLoaderImpl.SOUTH_LIMIT);
		double x = PropertiesLoaderImpl.WEST_LIMIT
				+ Math.random()
				* (PropertiesLoaderImpl.EAST_LIMIT - PropertiesLoaderImpl.WEST_LIMIT);
		return new Coordinate(x, y);
		

	}
	
	public static double getCourseFromToRad(Coordinate origin, Coordinate destiny){
		double polarRadAngle = Math.atan2(destiny.y - origin.y, destiny.x - origin.x);
		
		//if (polarRadAngle == Math.PI || polarRadAngle == (-1)*Math.PI) return 3/4*Math.PI;
		if (polarRadAngle >= 0.0){
			if(polarRadAngle == Math.PI/2) return 0.0;
			if(polarRadAngle < Math.PI/2) return Math.PI/2 - polarRadAngle;
			else return 2* Math.PI - (polarRadAngle -Math.PI/2);
		}
		if (polarRadAngle < 0.0){
			if(polarRadAngle == (-1)*Math.PI/2) return Math.PI;
			if(polarRadAngle < Math.PI/2) return Math.PI/2 - polarRadAngle;
			else return 2* Math.PI - (polarRadAngle -Math.PI/2);
		}
		
		
		return polarRadAngle;
	}

	public static Double2D coordinateToDouble2D(Coordinate coordinate){
		return new Double2D(coordinate.x, coordinate.y);
		}

	public static Coordinate double2DToCoordinate(Double2D double2D){
		return new Coordinate(double2D.x, double2D.y);
		}
	
}


