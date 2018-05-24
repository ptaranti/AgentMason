package util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;

public class GeometryExtraTest {

	@Test
	public void testGetRamdonPosition() {

		for (int i = 1; i < 31; i++) {
			Coordinate c = GeometryExtra.getRamdonPosition();
			if (c.y < PropertiesLoaderImpl.SOUTH_LIMIT
					|| c.y > PropertiesLoaderImpl.NORTH_LIMIT
					|| c.x < PropertiesLoaderImpl.WEST_LIMIT
					|| c.x > PropertiesLoaderImpl.EAST_LIMIT)
				fail("GetRamdonPosition() Fail");
			else
				System.out.println(c);

		}
	}

	@Test
	public void testGetCourseFromToRad() {

		Coordinate zero = new Coordinate(0.0, 0.0);
		Coordinate NP = new Coordinate(0.0, 90);
		Coordinate c45 = new Coordinate(2.0, 2.0);
		Coordinate estEqautorLine = new Coordinate(180.0, 0.0);
		Coordinate c135 = new Coordinate(2.0, -2.0);
		Coordinate SP = new Coordinate(0.0, -90);
		Coordinate c225 = new Coordinate(-2.0, -2.0);
		Coordinate westEqautorLine = new Coordinate(-180.0, 0.0);
		Coordinate c305 = new Coordinate(-2.0, 2.0);

		System.out.println("rumo de "
				+ zero
				+ " para "
				+ NP
				+ "em Radianos = "
				+ GeometryExtra.getCourseFromToRad(zero, NP)
				/ Math.PI
				+ "*PI ou "
				+ Math.toDegrees(GeometryExtra.getCourseFromToRad(zero,
						NP)) + "Graus");
		

		System.out.println("rumo de "
				+ zero
				+ " para "
				+ c45
				+ "em Radianos = "
				+ GeometryExtra.getCourseFromToRad(zero, c45)
				/ Math.PI
				+ "*PI ou "
				+ Math.toDegrees(GeometryExtra.getCourseFromToRad(zero,
						c45)) + "Graus");
		
		System.out.println("rumo de "
				+ zero
				+ " para "
				+ estEqautorLine
				+ "em Radianos = "
				+ GeometryExtra.getCourseFromToRad(zero, estEqautorLine)
				/ Math.PI
				+ "*PI ou "
				+ Math.toDegrees(GeometryExtra.getCourseFromToRad(zero,
						estEqautorLine)) + "Graus");
		
		System.out.println("rumo de "
				+ zero
				+ " para "
				+ c135
				+ "em Radianos = "
				+ GeometryExtra.getCourseFromToRad(zero, c135)
				/ Math.PI
				+ "*PI ou "
				+ Math.toDegrees(GeometryExtra.getCourseFromToRad(zero,
						c135)) + "Graus");
		
		System.out.println("rumo de "
				+ zero
				+ " para "
				+ SP
				+ "em Radianos = "
				+ GeometryExtra.getCourseFromToRad(zero, SP)
				/ Math.PI
				+ "*PI ou "
				+ Math.toDegrees(GeometryExtra.getCourseFromToRad(zero,
						SP)) + "Graus");
		
		System.out.println("rumo de "
				+ zero
				+ " para "
				+ c225
				+ "em Radianos = "
				+ GeometryExtra.getCourseFromToRad(zero, c225)
				/ Math.PI
				+ "*PI ou "
				+ Math.toDegrees(GeometryExtra.getCourseFromToRad(zero,
						c225)) + "Graus");
		System.out.println("rumo de "
				+ zero
				+ " para "
				+ westEqautorLine
				+ "em Radianos = "
				+ GeometryExtra.getCourseFromToRad(zero, westEqautorLine)
				/ Math.PI
				+ "*PI ou "
				+ Math.toDegrees(GeometryExtra.getCourseFromToRad(zero,
						westEqautorLine)) + "Graus");
		
		System.out.println("rumo de "
				+ zero
				+ " para "
				+ c305
				+ "em Radianos = "
				+ GeometryExtra.getCourseFromToRad(zero, c305)
				/ Math.PI
				+ "*PI ou "
				+ Math.toDegrees(GeometryExtra.getCourseFromToRad(zero,
						c305)) + "Graus");

		
	}

	@Test
	public void testCoordinateToDouble2D() {
		fail("Not yet implemented");
	}

	@Test
	public void testDouble2DToCoordinate() {
		fail("Not yet implemented");
	}

}
