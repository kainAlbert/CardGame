package Object.Card;

import java.util.List;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Character.Tactician;
import Object.Detail.DetailBase;

public class EnemyManager {

	// 手札から兵士カードを出す
	public void putHandCard( int cardID, String fieldNumber, String isMy ){

		// カード管理者を取得
		CardManager cm = Application.getObj().getCardManager( false );

		// 手札からカードを取得
		CharacterBase card = cm.searchIDType( cardID, Define.CARD_TYPE.ENEMYHAND );

		// Detail
		DetailBase detail = ((Card)card).getDetail();

		if( !fieldNumber.equals("null") ){

			detail.setSelectCharacter( getSelectCharacter( Integer.parseInt(fieldNumber), isMy.equals("true") ) );
		}

		CharacterBase soldierCard = new SoldierCard( false );

		((SoldierCard)soldierCard).initialize(
				detail,
				new GSvector2( card.getPos().x, card.getPos().y )
				);

		// 軍師取得
		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( false );

		// マナを消費
		((Tactician)tactician).useMana( detail.getCost() );

		// リストに追加
		cm.addCardList( soldierCard );

		// 説明を出す
		showExplanation( detail.getCardID() );

		// 手札は死亡させる
		card.doDead();
	}

	// 呪文を使う
	public void playSpell( int cardID, String fieldNumber, String isMy ){

		// カード管理者を取得
		CardManager cm = Application.getObj().getCardManager( false );

		// 手札からカードを取得
		CharacterBase card = cm.searchIDType( cardID, Define.CARD_TYPE.ENEMYHAND );

		// Detail
		DetailBase detail = ((Card)card).getDetail();

		if( !fieldNumber.equals("null") ){

			detail.setSelectCharacter( getSelectCharacter( Integer.parseInt(fieldNumber), isMy.equals("true") ) );
		}

		detail.play();

		// 軍師取得
		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( false );

		// マナを消費
		((Tactician)tactician).useMana( detail.getCost() );

		// 説明を出す
		showExplanation( detail.getCardID() );

		// 手札は死亡させる
		card.doDead();
	}

	// 攻撃
	public void attackEnemy( int enemyFieldNumber, int attackFieldNumber ){

		CharacterBase enemy = getSelectCharacter( enemyFieldNumber, false );
		CharacterBase attackCharacter = getSelectCharacter( attackFieldNumber, true );

		((SoldierCard)enemy).attackEnemy( attackCharacter );
	}

	// 選択先を返す
	private CharacterBase getSelectCharacter( int fieldNumber, boolean isMy ){

		List<CharacterBase> list = Application.getObj().getCardManager( isMy ).getCardList();

		for( int i=0; i<list.size(); i++ ){

			if( list.get(i).getFieldNumber() == fieldNumber ) return list.get(i);
		}

		return Application.getObj().getCharacterManager().getTactician(isMy);
	}

	// 使用カードの詳細を出す
	private void showExplanation( int cardID ){

		CardManager cm = Application.getObj().getCardManager( true );

		cm.createExplanation( cardID, new GSvector2( Define.WINDOW_SIZE.x / 2, -Define.CARD_EXPLANATION_SIZE.y ), 0.5 );
	}
}
