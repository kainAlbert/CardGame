package Object.Detail.DetailList.Gun;

import java.util.List;

import Application.Application;
import Application.Define;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GunCatastrophe extends DetailBase{

	// コンストラクタ
	public GunCatastrophe( boolean isMy ) {

		super( isMy );
	}

	// プレイ
	public void play(){

		// タイプ
		Define.CARD_TYPE myType = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// 呪文ダメージ+1の数だけ威力を上げる
		int revision = Application.getObj().getCardManager( mIsMy ).searchAbilityNum( Define.CARD_ABILITY.SPELL, myType );

		damageAllSoldier( Define.CARD_TYPE.MYFIELD, true, revision);
		damageAllSoldier( Define.CARD_TYPE.ENEMYFIELD, false, revision);
	}

	// 全ての兵士にダメージ
	private void damageAllSoldier( Define.CARD_TYPE type, boolean isMy, int revision ){

		// リストを取得
		List<CharacterBase> list = Application.getObj().getCardManager( isMy ).getCardList();

		for( int i=0; i<list.size(); i++ ){

			if( list.get(i).getType() != type.ordinal() ) continue;

			list.get(i).damage( mAttack + revision );
		}
	}
}
