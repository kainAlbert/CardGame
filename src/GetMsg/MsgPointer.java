package GetMsg;

import Application.Application;
import Application.Define;

public class MsgPointer {

	// ポインター初期位置
	public static void pointerFirst( String[] t ){

		// y軸を逆転させる
		double posY = Double.parseDouble( t[3] );

		posY = Define.WINDOW_SIZE.y - posY;

		posY *= 1.05;

		Application.getObj().getEnemyManager().pointerFirst( Double.parseDouble( t[2] ), posY );
	}

	// ポインターターゲット
	public static void pointerTarget( String[] t ){

		// y軸を逆転させる
		double posY = Double.parseDouble( t[3] );

		posY = Define.WINDOW_SIZE.y - posY;

		posY *= 1.05;

		Application.getObj().getEnemyManager().pointerTarget( Double.parseDouble( t[2] ), posY );
	}

	// ポインターリセット
	public static void pointerReset(){

		Application.getObj().getEnemyManager().pointerReset();
	}
}
