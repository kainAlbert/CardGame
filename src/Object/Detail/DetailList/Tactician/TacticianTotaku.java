package Object.Detail.DetailList.Tactician;

import Application.Application;
import Application.Define;
import Application.DefineCardID;
import Object.Card.CardManager;
import Object.Detail.DetailBase;

public class TacticianTotaku extends DetailBase{

	// コンストラクタ
	public TacticianTotaku( boolean isMy ){

		super( isMy );
	}


	// プレイ
	public void play(){

		int num = mHP > 15 ? 1 : 2;

		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// タイプ
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD ;

		for( int i=0; i<num; i++ ){

			if( cm.searchTypeNum( type ) >= Define.MAX_FIELD_CARD ) return;

			// 初級歩兵を召喚
			createSoldier( DefineCardID.NOVICE_INFANTRY, mIsMy);
		}
	}

	// 回復
	public void care( int c ){

		mHP = c;
	}

	// 条件
	public boolean useCondition(){

		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// タイプ
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD ;

		return cm.searchTypeNum( type ) < Define.MAX_FIELD_CARD;
	}
}
