package Object.Detail.DetailList.Syoku;

import Application.Define;
import Application.GSvector2;
import Object.Detail.DetailBase;

public class SyokuKanginpei extends DetailBase{

	// コンストラクタ
	public SyokuKanginpei( boolean isMy ) {

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.TAUNT;
	}
}
