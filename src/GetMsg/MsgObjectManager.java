package GetMsg;

import Application.Application;
import Application.Define;
import Application.MesgRecvThread;
import Object.ObjectManager;

public class MsgObjectManager {

	// スタート
	public static void start(){

		ObjectManager obj = Application.getObj();

		if( obj.getIsStart() ) return;

		if( !MsgTactician.mIsGetTactician ) return;

		obj.setStart();

		// 準備完了を知らせる
		String msg = Application.getID() + Define.MSG + Define.MSG_SET_START;
		MesgRecvThread.outServer( msg );
	}
}
