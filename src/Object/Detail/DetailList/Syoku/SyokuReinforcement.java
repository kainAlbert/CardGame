package Object.Detail.DetailList.Syoku;

import java.util.List;

import Application.Application;
import Application.Define;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class SyokuReinforcement extends DetailBase{

	// コンストラクタ
	public SyokuReinforcement( boolean isMy ) {

		super( isMy );
	}

	// プレイ
	public void play(){

		// カードリスト取得
		List<CharacterBase> list = Application.getObj().getCardManager( mIsMy ).getCardList();

		// タイプ
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		for( int i=0; i<list.size(); i++ ){

			if( list.get(i).getType() != type.ordinal() ) continue;

			list.get(i).care( 2 );
		}

		// 軍師取得
		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( mIsMy );

		tactician.care( 2 );
	}
}
