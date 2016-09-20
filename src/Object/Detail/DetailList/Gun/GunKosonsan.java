package Object.Detail.DetailList.Gun;

import Application.Application;
import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Object.Card.CardManager;
import Object.Detail.DetailBase;

public class GunKosonsan extends DetailBase{

	// コンストラクタ
	public GunKosonsan( boolean isMy ) {

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.DEATHRATTLE;
	}

	// 死亡
		public void finish(){

		// 初級歩兵を召喚
		createSoldier( DefineCardID.NOVICE_INFANTRY, mIsMy);

		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// タイプ
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD ;

		// 戦場がいっぱいなら終了
		if( cm.searchTypeNum( type ) >= Define.MAX_FIELD_CARD ) return;

		// 初級歩兵を召喚
		createSoldier( DefineCardID.NOVICE_INFANTRY, mIsMy);
	}
}
