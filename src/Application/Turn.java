package Application;

import Object.Collision;
import Object.Character.CharacterBase;
import Object.Character.Tactician;

public class Turn{

	private CharacterBase mButton;
	private boolean mIsMyTurn;
	private int mTimer;
	private boolean mIsFirstTurnChange;

	// コンストラクタ
	public Turn(){

		mButton = new CharacterBase();
	}

	// 初期化
	public void initialize(){

		// 最初のターン決め
		if( Application.getID() == 1 ){

			mIsMyTurn = Math.random() > 0.5;
		}

		// ボタン初期化
		mButton.initialize(
				"turnEnd",
				new GSvector2( Define.TURN_BUTTON_IMAGE_POS.x, Define.TURN_BUTTON_IMAGE_POS.y ),
				new GSvector2( Define.TURN_BUTTON_IMAGE_SIZE.x, Define.TURN_BUTTON_IMAGE_SIZE.y ),
				new GSvector2( Define.TURN_BUTTON_IMAGE_RESIZE.x, Define.TURN_BUTTON_IMAGE_RESIZE.y ),
				0, 0);

		changeButton();

		mIsFirstTurnChange = false;
	}

	// 開始ターンを設定
	public void setStartTurn( boolean isMyTurn ){

		mIsMyTurn = isMyTurn;

		changeButton();
	}

	// 更新
	public void update(){

		mTimer--;

		if( mTimer <= 0 ) return;

		if( mTimer < Define.TURN_DISTANCE_TIME / 2 || mTimer >= Define.TURN_DISTANCE_TIME - Define.TURN_DISTANCE_TIME / 4 ){

			Application.getStringLabel().movePosX( Define.TURN_STRING_MOVE );
		}
	}

	// クリック
	public void click(){

		if( !mIsMyTurn ) return;

		GSvector2 mousePos = Application.getObj().getMousePos();

		if( !Collision.isCollisionSquareDot( mButton.getPos(), mButton.getSize(), mousePos ) ) return;

		// ターン変更を送信
		String msg = Application.getID() + Define.MSG + Define.MSG_CHANGE_TURN;
		MesgRecvThread.outServer( msg );

		// ターン変更
		turnChange();
	}

	// ターン変更
	public void turnChange(){

		mIsMyTurn = !mIsMyTurn;

		// ターン変更文字設定
		changeTurnStr();

		// 終了時の処理
		if( !mIsMyTurn ){

			// ターン終了処理
			Application.getObj().getCardManager( true ).endTurn();
			Application.getObj().getCardManager( false ).endTurn();

			// ボタンのリサイズ変更
			changeButton();
		}

		// 開始時の処理
		if( mIsMyTurn ){

			// ターン開始処理
			Application.getObj().getCardManager( true ).startTurn();
			Application.getObj().getCardManager( false ).startTurn();

			// ヒーローパワーを使用可能にする
			CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( true );

			((Tactician)tactician).usePower();

			// ボタンのリサイズ変更
			changeButton();
		}

		if( mIsFirstTurnChange ){

			// マナ回復
			((Tactician)Application.getObj().getCharacterManager().getTactician( mIsMyTurn )).startTurn();
			return;
		}

		mIsFirstTurnChange = true;
	}

	// ボタンリサイズ変更
	private void changeButton(){

		mButton.changeReSizeX( Define.TURN_BUTTON_IMAGE_RESIZE.x * ( mIsMyTurn ? 1 : 2 ) );
	}

	// ターン変更文字設定
	public void changeTurnStr(){

		mTimer = Define.TURN_DISTANCE_TIME;

		// ターン変更文字設定
		Application.getStringLabel().setType( mIsMyTurn ? Define.STRING_TYPE.MYTURN : Define.STRING_TYPE.ENDTURN );
		Application.getStringLabel().setPos( new GSvector2( Define.WINDOW_SIZE.x, Define.TURN_STRING_POS.y ) );
	}

	// ゲッター
	public CharacterBase getButton(){ return mButton; }
	public boolean getIsMyTurn(){ return mIsMyTurn; }
	public int getTimer(){ return mTimer; }

}
