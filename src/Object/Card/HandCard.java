package Object.Card;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Application.MesgRecvThread;
import Object.Character.CharacterBase;
import Object.Character.Tactician;
import Object.Detail.DetailReader;

public class HandCard extends Card{

	private boolean mIsHand;
	private final double FIRST_MOVE_Y = 150;

	// コンストラクタ
	public HandCard( boolean isMy ){

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos ){

		mIsHand = false;

		super.initialize();

		mDetail = DetailReader.getDetail( cardID, mIsMy );

		super.initializeDetail( cardID );

		mPos = pos;
		mLastPos.x = pos.x;
		mLastPos.y = pos.y + FIRST_MOVE_Y * ( mIsMy ? 1 : -1 );
	}

	// 更新
	public void update(){

		super.update();

		// カードリサイズ変更
		changeReSize();

		// 手札状態でない時の処理
		if( mIsHand ) return;

		updateNotHand();
	}

	// カードリサイズ変更
	private void changeReSize(){

		// 味方のみ
		if( !mIsMy ) return;

		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( true );

		mReSize.x = Define.CARD_RESIZE.x * ( ((Tactician)tactician).getMana() < mDetail.getCost() ? 1 : 2 );
	}

	// 手札状態でない時の処理
	private void updateNotHand(){

		// 終点でなければ終了
		if( mPos.x != mLastPos.x || mPos.y != mLastPos.y ) return;

		int handNum = Application.getObj().getCardManager( mIsMy ).searchTypeNum( mIsMy ? Define.CARD_TYPE.MYHAND : Define.CARD_TYPE.ENEMYHAND );

		// 手札がすでに最大なら消去
		if( handNum >= Define.MAX_HAND_CARD ){

			mIsDead = true;
			return;
		}

		mIsHand = true;

		mType = mIsMy ? Define.CARD_TYPE.MYHAND.ordinal() : Define.CARD_TYPE.ENEMYHAND.ordinal();
	}

	// クリック
	public void click(){

		if( !mIsMy ) return;

		super.click();
	}

	// 選択した時
	public void select(){

		if( !mIsHand ) return;

		super.select();
	}

	// マウスを離した時
	public void release(){

		if( !mIsHand ) return;

		if( !mIsSelect ) return;

		super.release();

		// マウス位置を取得
		GSvector2 mousePos = Application.getObj().getMousePos();

		// 軍師取得
		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( mIsMy );

		// 条件確認
		if( !mDetail.useCondition( mousePos, tactician, mType == Define.CARD_TYPE.MYHAND.ordinal() ) ) return;

		// マナを消費
		((Tactician)tactician).useMana( mDetail.getCost() );

		// この手札は死亡させる
		mIsDead = true;

		// 兵士召喚
		if( mDetail.getIsSoldier() ){

			putCreature();
		}

		// 呪文
		if( !mDetail.getIsSoldier() ){

			playSpell();
		}
	}

	// フィールドにカードを置く
	private void putCreature(){

		// フィールドカードを生成
		CharacterBase card = new SoldierCard( mIsMy );

		((SoldierCard)card).initialize(
				mDetail,
				new GSvector2( mPos.x, mPos.y )
				);

		// リストに追加
		Application.getObj().getCardManager( mIsMy ).addCardList( card );

		sendPlayCard( Define.MSG_PUT_HANDCARD );
	}

	// 呪文を使用
	private void playSpell(){

		mDetail.play();

		sendPlayCard( Define.MSG_PLAY_SPELL );
	}

	// カードの使用を送信
	private void sendPlayCard( String msgType ){

		CharacterBase selectCharacter = mDetail.getSelectCharacter();

		// 選択先のID
		String fieldNumber = "null";

		// 選択先の敵か味方か
		String isMy = "null";

		if( selectCharacter != null ){

			fieldNumber = String.valueOf( selectCharacter.getFieldNumber() );
			isMy = selectCharacter.getIsMy() ? "false" : "true";
		}

		String msg = Application.getID() + Define.MSG + msgType + Define.MSG + mDetail.getCardID() + Define.MSG + fieldNumber + Define.MSG + isMy;
		MesgRecvThread.outServer( msg );
	}

	// ドラッグ
	public void drag(){

		if( !mIsHand ) return;

		if( !mIsSelect ) return;

		super.drag();
	}
}
