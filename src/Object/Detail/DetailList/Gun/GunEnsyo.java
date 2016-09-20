package Object.Detail.DetailList.Gun;

import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Object.Detail.DetailBase;

public class GunEnsyo extends DetailBase{

	// コンストラクタ
	public GunEnsyo( boolean isMy ) {

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
	}
}
