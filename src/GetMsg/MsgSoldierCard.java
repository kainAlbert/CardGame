package GetMsg;

import Application.Application;

public class MsgSoldierCard {

	// 攻撃
	public static void attack( String[] t ){

		Application.getObj().getEnemyManager().attackEnemy( Integer.parseInt(t[2]), Integer.parseInt(t[3]) );;
	}
}
