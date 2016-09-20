package Object;

import Application.GSvector2;

public class Collision {

	// 更新
	public void update(){

	}

	// 正方形同士の判定
	private boolean isCollisionSquare( GSvector2 pos1, GSvector2 scale1, GSvector2 pos2, GSvector2 scale2 ){

		boolean cx =
				( pos1.x <= pos2.x && pos1.x + scale1.x >= pos2.x ) ||
				( pos1.x <= pos2.x + scale2.x && pos1.x + scale1.x >= pos2.x + scale2.x );

		boolean cy =
				( pos1.y <= pos2.y && pos1.y + scale1.y >= pos2.y ) ||
				( pos1.y <= pos2.y + scale2.y && pos1.y + scale1.y >= pos2.y + scale2.y );

		boolean cxy1 =
				( pos1.x <= pos2.x && pos1.x + scale1.x >= pos2.x + scale2.x ) &&
				( pos2.y <= pos1.y && pos2.y + scale2.y >= pos1.y + scale1.y );

		boolean cxy2 =
				( pos2.x <= pos1.x && pos2.x + scale2.x >= pos1.x + scale1.x ) &&
				( pos1.y <= pos2.y && pos1.y + scale1.y >= pos2.y + scale2.y );

		return ( cx && cy ) || cxy1 || cxy2;
	}

	// 点と円の判定
	private boolean isCollisionPointCircle( GSvector2 pos1, GSvector2 pos2, double radius ){

		return Math.pow( pos1.x - pos2.x, 2 ) + Math.pow( pos1.y - pos2.y, 2 ) < Math.pow( radius, 2 );
	}

	// 正方形と円の判定
	private boolean isCollisionSquareCircle( GSvector2 pos1, GSvector2 scale1, GSvector2 pos2, double radius2 ){

		GSvector2 pos2_r = new GSvector2( pos2.x + radius2, pos2.y + radius2 );

		GSvector2[] pos = {
				new GSvector2( pos1.x, pos1.y ), new GSvector2( pos1.x + scale1.x, pos1.y ),
				new GSvector2( pos1.x, pos1.y + scale1.y ), new GSvector2( pos1.x + scale1.x, pos1.y + scale1.y )
		};

		for( int i=0; i<4; i++ ){

			if( isCollisionPointCircle( pos[i], pos2_r, radius2 ) ) return true;
		}

		if( pos1.x < pos2_r.x - radius2 &&
				pos1.x + scale1.x > pos2_r.x + radius2 &&
				pos1.y < pos2_r.y - radius2 &&
				pos1.y + scale1.y > pos2_r.y + radius2 ) return true;

		return false;
	}

	// 円と円の判定
	private boolean isCollisionCircle( GSvector2 pos1, double radius1, GSvector2 pos2, double radius2 ){

		return Math.pow( pos1.x - pos2.x, 2 ) + Math.pow( pos1.y - pos2.y, 2 ) < Math.pow( radius1 + radius2, 2 );
	}

	// 四角と点の判定
	public static boolean isCollisionSquareDot( GSvector2 pos1, GSvector2 size1, GSvector2 pos2 ){

		if( pos1 == null || size1 == null || pos2 == null ) return false;

		return pos1.x <= pos2.x && pos1.x + size1.x  >= pos2.x &&
				   pos1.y <= pos2.y && pos1.y + size1.y  >= pos2.y;
	}
}
