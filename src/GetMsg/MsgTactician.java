package GetMsg;

import Application.Application;
import Application.Define;
import Application.MesgRecvThread;

public class MsgTactician {

	public static boolean mIsGetTactician = false;

	// 軍師設定
	public static void setTactician( String[] t ){

		if( Application.getObj().getIsStart() ) return;

		if( !Application.getSelectTactician().getIsSelect() ) return;

		if( !mIsGetTactician ){

			Define.TACTICIAN_ID[] id = {
					Define.TACTICIAN_ID.SONKEN, Define.TACTICIAN_ID.SYOKATURYO, Define.TACTICIAN_ID.SIBAI, Define.TACTICIAN_ID.TOTAKU
			};

			Application.getObj().getCharacterManager().setEnemyTactician( id[ Integer.parseInt(t[2])] );

			// カード管理者初期化
			Application.getObj().getCardManager( false ).initialize();

			mIsGetTactician = true;
		}

		// 準備完了を知らせる
		String msg = Application.getID() + Define.MSG + Define.MSG_SET_START;
		MesgRecvThread.outServer( msg );
	}

	// ヒーローパワーを使用
	public static void userPower( String[] t ){

		Application.getObj().getEnemyManager().usePower( t[2], t[3] );
	}

}
