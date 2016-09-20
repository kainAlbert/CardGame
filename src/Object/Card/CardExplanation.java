package Object.Card;

import Application.Application;
import Application.Define;
import Application.Direction;
import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Character.NumLabel;
import Object.Detail.DetailReader;
import Object.Detail.DetailStructure;

public class CardExplanation extends CharacterBase{

	private NumLabel mCostLabel;
	private NumLabel mAttackLabel;
	private NumLabel mHPLabel;
	private DetailStructure mStr;
	private int mTimer;
	private boolean mIsInitialize;

	// コンストラクタ
	public CardExplanation(){

		super();

		mTimer = Define.CARD_EXPLANATION_TIME;

		mStr = new DetailStructure();

		// 各ラベル生成
		mCostLabel = new NumLabel();
		mAttackLabel = new NumLabel();
		mHPLabel = new NumLabel();

		mIsInitialize = false;
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, double factor ){

		super.initialize( "explanation/" + cardID,
				pos,
				new GSvector2( Define.CARD_EXPLANATION_SIZE.x * factor, Define.CARD_EXPLANATION_SIZE.y * factor ),
				new GSvector2( Define.CARD_RESIZE.x, Define.CARD_RESIZE.y ), 0, 0
				);

		mStr = DetailReader.readDetail( cardID );

		// ラベルクラス初期化
		mCostLabel.initialize( Define.CARD_NUM_TYPE.COST.ordinal(), Define.CARD_NUM_IMAGE_SIZE * 2 * factor );
		mAttackLabel.initialize( Define.CARD_NUM_TYPE.ATTACK.ordinal(), Define.CARD_NUM_IMAGE_SIZE * 2 * factor );
		mHPLabel.initialize( Define.CARD_NUM_TYPE.HP.ordinal(), Define.CARD_NUM_IMAGE_SIZE * 2 * factor );

		if( factor != 1 ){

			CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( false );

			mLastPos = new GSvector2( tactician.getPos().x + tactician.getSize().x + 10, tactician.getPos().y );
		}

		mIsInitialize = true;
	}

	// 更新
	public void update(){

		if( !mIsInitialize ) return;

		super.update();

		moveBack();

		// 各数字の位置設定
		mCostLabel.updateNum( mStr.mCost, new GSvector2( mPos.x, mPos.y ) );

		// 兵士以外は表示しない
		if( mStr.mIsSoldier ){

			mAttackLabel.updateNum( mStr.mAttack, new GSvector2( mPos.x, mPos.y + mSize.y - Define.CARD_NUM_IMAGE_SIZE * 2 ) );
			mHPLabel.updateNum( mStr.mHP, new GSvector2( mPos.x + mSize.x - Define.CARD_NUM_IMAGE_SIZE * 2, mPos.y + mSize.y - Define.CARD_NUM_IMAGE_SIZE * 2 ) );
		}

		mTimer--;

		if( mTimer <= 0 ) mIsDead = true;
	}

	// 元の場所に戻る
	protected void moveBack(){

		// 終点への移動量
		GSvector2 velocity = Direction.getToVelocity( mPos, mLastPos );

		// 速度
		double distance = Direction.getDistance( mPos, mLastPos );

		mSpeed = Math.min( Math.max( distance / ( Define.WINDOW_SIZE.x / Define.CARD_MAX_SPEED ), Define.CARD_MIN_SPEED ), Define.CARD_MAX_SPEED );

		// 移動
		mPos.x += velocity.x * mSpeed;
		mPos.y += velocity.y * mSpeed;

		// 近い位置まできたら修正
		if( Math.abs( mLastPos.x - mPos.x ) < mSpeed * 1.1 ) mPos.x = mLastPos.x;
		if( Math.abs( mLastPos.y - mPos.y ) < mSpeed * 1.1 ) mPos.y = mLastPos.y;
	}

	// ゲッター
	public NumLabel getCostLabel(){ return mCostLabel; }
	public NumLabel getAttackLabel(){ return mAttackLabel; }
	public NumLabel getHPLabel(){ return mHPLabel; }
	public boolean getIsInitialize(){ return mIsInitialize; }
}
