package GetMsg;

import Application.Application;

public class MsgTurn {

	// 開始ターン設定
	public static void setStartTurn( String[] t ){

		if( !MsgTactician.mIsGetTactician ) return;

		if( !Application.getSelectTactician().getIsSelect() ) return;

		boolean isMyTurn = t[2].equals("true");

		Application.getTurn().setStartTurn(isMyTurn);
	}

	// ターン変更
	public static void changeTurn(){

		Application.getTurn().turnChange();
	}
}
