package Object.Detail.DetailList.Gi;

import Application.Application;
import Application.Define;
import Application.DefineCardID;
import Object.Card.CardManager;
import Object.Detail.DetailBase;

public class GiCallCharge extends DetailBase{

	// コンストラクタ
	public GiCallCharge( boolean isMy ) {

		super( isMy );
	}

	// プレイ
	public void play(){

		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// タイプ
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		for( int i=0; i<mAttack; i++ ){

			if( cm.searchTypeNum( type ) >= Define.MAX_FIELD_CARD ) return;

			// 初級騎兵を召喚
			createSoldier( DefineCardID.NOVICE_CHARGE, mIsMy);
		}
	}

	// 条件
	public boolean useCondition(){

		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// タイプ
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD ;

		return cm.searchTypeNum( type ) < Define.MAX_FIELD_CARD;
	}
}
