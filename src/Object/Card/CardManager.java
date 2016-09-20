package Object.Card;

import java.util.ArrayList;
import java.util.List;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Application.MesgRecvThread;
import Object.Collision;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class CardManager {

	private List<CharacterBase> mCardList;
	private CharacterBase mExplanation;
	private GSvector2[] mHandPos;
	private int mCardID;
	private boolean mIsMy;

	// コンストラクタ
	public CardManager( boolean isMy ){

		// リストを生成
		mCardList = new ArrayList<CharacterBase>();

		// 敵か味方か
		mIsMy = isMy;

		// デッキ生成
		CharacterBase deck = new DeckCard( isMy );

		mCardList.add( deck );
	}

	// 初期化
	public void initialize(){

		// 手札の位置設定
		final double FIRST_POS_X = 200;
		double[] MY_POS_Y = {
				Define.FIELD_MYHAND+20, Define.FIELD_MYHAND+15, Define.FIELD_MYHAND+10, Define.FIELD_MYHAND+5, Define.FIELD_MYHAND,
				Define.FIELD_MYHAND, Define.FIELD_MYHAND+5, Define.FIELD_MYHAND+10, Define.FIELD_MYHAND+15, Define.FIELD_MYHAND+20
		};
		double[] ENEMY_POS_Y = {
				Define.FIELD_ENEMYHAND-20, Define.FIELD_ENEMYHAND-15, Define.FIELD_ENEMYHAND-10, Define.FIELD_ENEMYHAND-5, Define.FIELD_ENEMYHAND,
				Define.FIELD_ENEMYHAND, Define.FIELD_ENEMYHAND-5, Define.FIELD_ENEMYHAND-10, Define.FIELD_ENEMYHAND-15, Define.FIELD_ENEMYHAND-20
		};

		mHandPos = new GSvector2[Define.MAX_HAND_CARD];

		for( int i=0; i<Define.MAX_HAND_CARD; i++ ){

			mHandPos[i] = new GSvector2( FIRST_POS_X + i * Define.CARD_SIZE.x * 0.9, mIsMy ? MY_POS_Y[i] + Define.CARD_SIZE.y : ENEMY_POS_Y[i] - Define.CARD_SIZE.y );
		}

		// リスト初期化
		for( int i=0; i<mCardList.size(); i++ ){

			mCardList.get(i).initialize();
		}

		// 変数初期化
		mCardID = 1;
	}

	// ターン開始時の処理
	public void startTurn(){

		for( int i=0; i<mCardList.size(); i++ ){

			// デッキ
			if( mIsMy && mCardList.get(i).getType() == Define.CARD_TYPE.DECK.ordinal() ){

				((DeckCard)mCardList.get(i)).drawCard();
			}

			// 兵士
			if( mCardList.get(i).getType() == Define.CARD_TYPE.MYFIELD.ordinal() ){

				((SoldierCard)mCardList.get(i)).reconstituteAttack();
			}

			if( mCardList.get(i).getType() == Define.CARD_TYPE.MYFIELD.ordinal() || mCardList.get(i).getType() == Define.CARD_TYPE.ENEMYFIELD.ordinal() ){

				DetailBase detail = ((Card)mCardList.get(i)).getDetail();

				((DetailBase)detail).initRevisionAttack();
			}
		}
	}

	// ターン終了時の処理
	public void endTurn(){

		for( int i=0; i<mCardList.size(); i++ ){

			// 兵士
			if( mCardList.get(i).getType() == Define.CARD_TYPE.MYFIELD.ordinal() || mCardList.get(i).getType() == Define.CARD_TYPE.ENEMYFIELD.ordinal() ){

				DetailBase detail = ((Card)mCardList.get(i)).getDetail();

				((DetailBase)detail).initRevisionAttack();
			}
		}
	}

	// 更新
	public void update(){

		// リスト更新
		updateList( mCardList );

		// ソート
		sortHand();
		sortField();

		// 説明更新
		if( mExplanation == null ) return;

		if( !((CardExplanation)mExplanation).getIsInitialize() ) return;

		mExplanation.update();

		if( !mExplanation.getIsDead() ) return;

		mExplanation.finish();
		mExplanation = null;
	}

	// リスト更新
	private void updateList( List<CharacterBase> list ){

		for( int i=0; i<list.size(); i++ ){

			list.get(i).update();

			if( !list.get(i).getIsDead() ) continue;

			list.get(i).finish();

			list.remove(i);

			continue;
		}
	}

	// 手札ソート
	private void sortHand(){

		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYHAND : Define.CARD_TYPE.ENEMYHAND;
		int handNum = searchTypeNum( type );
		int index = (int)(Math.ceil( ( Define.MAX_HAND_CARD - handNum ) / 2) );

		for( int i=0; i<mCardList.size(); i++ ){

			if( mCardList.get(i) == null ) continue;

			// 手札のみ
			if( mCardList.get(i).getType() != type.ordinal() ) continue;

			try{

				((Card)mCardList.get(i)).sortPos( mHandPos[ index ] );

				index++;

				if( index >= Define.MAX_HAND_CARD ) return;
			}catch( Exception e ){
				e.printStackTrace();
				MesgRecvThread.outServer( Application.getID() + Define.MSG + "HandError" );
			}
		}
	}

	// 戦場をソート
	public void sortField(){

		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;
		int fieldNum = searchTypeNum( type );
		int index = (int)(Math.ceil( ( Define.MAX_FIELD_CARD - fieldNum ) / 2) );
		double posY = mIsMy ? Define.FIELD_MYCARD_POSY : Define.FIELD_ENEMYCARD_POSY;

		for( int i=0; i<mCardList.size(); i++ ){

			if( mCardList.get(i) == null ) continue;

			// 戦場のみ
			if( mCardList.get(i).getType() != type.ordinal() ) continue;

			mCardList.get(i).setFieldNumber( index );

			try{
				// 攻撃中はソートしない
				if( ((SoldierCard)mCardList.get(i)).getAttackTimer() <= 0 ){

					((Card)mCardList.get(i)).sortPos( new GSvector2( Define.FIELD_CARD_POSX[ index ], posY ) );
				}

				index++;
			}catch(Exception e ){
				e.printStackTrace();
				MesgRecvThread.outServer( Application.getID() + Define.MSG + "FieldError" );
			}

			if( index >= Define.MAX_FIELD_CARD ) return;
		}
	}

	// デッキを返す
	public CharacterBase getDeck(){

		for( int i=0; i<mCardList.size(); i++ ){

			if( mCardList.get(i).getType() != Define.CARD_TYPE.DECK.ordinal() ) continue;

			return mCardList.get(i);
		}

		return null;
	}

	// リスト内タイプ数検索
	public int searchTypeNum( Define.CARD_TYPE type ){

		int num = 0;

		for( int i=0; i<mCardList.size(); i++ ){

			if( mCardList.get(i).getType() == type.ordinal() ) num++;
		}

		return num;
	}

	// リスト内タイプ&位置検索
	public int searchPosType( Define.CARD_TYPE type , GSvector2 mousePos ){

		for( int i=0; i<mCardList.size(); i++ ){

			CharacterBase c = mCardList.get(i);

			// 指定したタイプのみ
			if( c.getType() != type.ordinal() ) continue;

			// 指定位置がカード内にあるか
			if( Collision.isCollisionSquareDot( c.getPos(), c.getSize(), mousePos ) ){

				// IDを返す
				return c.getID();
			}
		}

		return -1;
	}

	// リスト内アビリティ&タイプ検索
	public int searchAbilityNum( Define.CARD_ABILITY abi, Define.CARD_TYPE type ){

		int num = 0;

		for( int i=0; i<mCardList.size(); i++ ){

			CharacterBase c = mCardList.get(i);
			DetailBase d = ((Card)c).getDetail();

			// 指定したID&タイプなら加算
			if( !d.isAbility( abi ) || c.getType() != type.ordinal() ) continue;

			num++;
		}

		return num;
	}

	// 指定したIDのカードを返す
	public CharacterBase searchID( int id ){

		for( int i=0; i<mCardList.size(); i++ ){

			if( mCardList.get(i).getID() == id ) return mCardList.get(i);
		}

		return null;
	}

	// 指定したID&タイプのカードを返す
	public CharacterBase searchIDType( int cardID, Define.CARD_TYPE type ){

		for( int i=0; i<mCardList.size(); i++ ){

			DetailBase detail = ((Card)mCardList.get(i)).getDetail();

			if( detail.getCardID() == cardID && mCardList.get(i).getType() == type.ordinal() ) return mCardList.get(i);
		}

		return null;
	}

	// マウスの挙動
	public void mouseMove( Define.MOUSE_STATUS_TYPE type ){

		for( int i=0; i<mCardList.size(); i++ ){

			CharacterBase c = mCardList.get(i);

			if( mouseMove( type, c ) ) return;
		}
	}

	// マウスの挙動(カード側)
	private boolean mouseMove( Define.MOUSE_STATUS_TYPE type, CharacterBase c ){

		// 離す
		if( type == Define.MOUSE_STATUS_TYPE.RELEASE ){

			c.release();
			return false;
		}

		// ドラッグ
		if( type == Define.MOUSE_STATUS_TYPE.DRAG ){

			c.drag();
			return false;
		}

		boolean isCollision = Collision.isCollisionSquareDot( c.getPos(), c.getSize(), Application.getObj().getMousePos() );

		// クリック
		if( type == Define.MOUSE_STATUS_TYPE.CLICK ){

			mExplanation = null;

			if( !isCollision ) return false;

			c.click();
			return true;
		}

		// 選択
		if( type == Define.MOUSE_STATUS_TYPE.SELECT ){

			if( !isCollision ) return false;

			c.select();
			return true;
		}

		return false;
	}

	// カード説明を生成
	public void createExplanation( int cardID, GSvector2 pos, double factor ){

		if( mExplanation != null ){

			mExplanation.finish();
			mExplanation = null;
		}

		// 説明を生成
		mExplanation = new CardExplanation();
		((CardExplanation)mExplanation).initialize( cardID, pos, factor );
	}

	// カードリストに追加
	public void addCardList( CharacterBase c ){

		mCardList.add( c );

		mCardID++;
	}

	// ゲッター
	public List<CharacterBase> getCardList(){ return mCardList; }
	public CharacterBase getExplanation(){ return mExplanation; }
	public int getCardID(){ return mCardID; }
}
