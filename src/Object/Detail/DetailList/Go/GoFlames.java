package Object.Detail.DetailList.Go;

import java.util.List;

import Application.Application;
import Application.Define;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GoFlames extends DetailBase{

	// コンストラクタ
	public GoFlames( boolean isMy ) {

		super( isMy );
	}

	// プレイ
	public void play(){

		// リストを取得
		List<CharacterBase> list = Application.getObj().getCardManager( !mIsMy ).getCardList();

		// タイプ
		int enemyType = mIsMy ? Define.CARD_TYPE.ENEMYFIELD.ordinal() : Define.CARD_TYPE.MYFIELD.ordinal();

		// タイプ
		Define.CARD_TYPE myType = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// 呪文ダメージ+1の数だけ威力を上げる
		int revision = Application.getObj().getCardManager( mIsMy ).searchAbilityNum( Define.CARD_ABILITY.SPELL, myType );

		for( int i=0; i<list.size(); i++ ){

			if( list.get(i).getType() != enemyType ) continue;

			list.get(i).damage( mAttack + revision );
		}
	}
}
