package GetMsg;

import Application.Application;

public class MsgHandCard {

	// 手札からカードを出す
	public static void putHandCard( String[] t ){

		Application.getObj().getEnemyManager().putHandCard( Integer.parseInt( t[2] ), t[3], t[4] );
	}

	// 呪文を使う
	public static void playSpell( String[] t ){

		Application.getObj().getEnemyManager().playSpell( Integer.parseInt( t[2] ), t[3], t[4] );
	}
}
