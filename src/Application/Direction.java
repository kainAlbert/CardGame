package Application;

public class Direction{

	// 2つの位置から移動量を返す
	public static GSvector2 getToVelocity( GSvector2 pos1, GSvector2 pos2 ){

		GSvector2 vel = new GSvector2();
		double revision = 0;

		vel.x = pos2.x - pos1.x;
		vel.y = pos2.y - pos1.y;

		revision = Math.sqrt( Math.pow( vel.x, 2 ) + Math.pow( vel.y, 2 ) );

		vel.x = vel.x / revision;
		vel.y = vel.y / revision;

		if( pos1.x == pos2.x ) vel.x = 0;
		if( pos1.y == pos2.y ) vel.y = 0;

		return vel;
	}

	// 移動量から角度を返す
	public static double getToVelocity( GSvector2 vel ){

		return Math.atan2( vel.y, vel.x ) * 180 / Math.PI;
	}

	// 2点間の距離を返す
	public static double getDistance( GSvector2 pos1, GSvector2 pos2 ){

		return Math.sqrt( Math.pow( pos1.x - pos2.x, 2 ) + Math.pow( pos1.y - pos2.y, 2 ) );
	}
}
