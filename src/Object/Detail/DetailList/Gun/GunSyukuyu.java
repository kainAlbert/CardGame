package Object.Detail.DetailList.Gun;

import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Object.Detail.DetailBase;

public class GunSyukuyu extends DetailBase{

	// コンストラクタ
	public GunSyukuyu( boolean isMy ) {

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.DEATHRATTLE;
		mAbility2 = Define.CARD_ABILITY.TAUNT;
	}

	// 死亡
	public void finish(){

		// 初級槍兵を召喚
		createSoldier( DefineCardID.NOVICE_SPEAR, mIsMy);
	}
}
