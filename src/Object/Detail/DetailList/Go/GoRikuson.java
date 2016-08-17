package Object.Detail.DetailList.Go;

import Application.Application;
import Application.Define;
import Object.Card.CardManager;
import Object.Detail.DetailBase;

public class GoRikuson extends DetailBase{


	// コンストラクタ
	public GoRikuson( boolean isMy ) {

		super( isMy );
	}

	// プレイ
	public void play(){

		// タイプ取得
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// カード管理者取得
		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// 手札が最大なら終了
		int handNum = cm.searchTypeNum( type );

		if( handNum >= Define.MAX_HAND_CARD ) return;

		// カードを引く
		cm.drawCard( 1 );
	}
}