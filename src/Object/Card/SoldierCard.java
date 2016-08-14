package Object.Card;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Collision;
import Object.Character.CharacterBase;
import Object.Character.Tactician;
import Object.Effect.AttackEffect;
import Object.Effect.PointerEffect;

public class SoldierCard extends Card{

	private GSvector2 mFirstPos;
	private GSvector2 mTargetPos;
	private GSvector2 mTargetSize;
	private int mAttackTimer;

	// コンストラクタ
	public SoldierCard( boolean isMy ){

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 lastPos ){

		super.initialize();

		mPos = pos;
		mLastPos = lastPos;
		mType = mIsMy ? Define.CARD_TYPE.MYFIELD.ordinal() : Define.CARD_TYPE.ENEMYFIELD.ordinal();

		super.initializeDetail( cardID );

		mFirstPos = new GSvector2( lastPos.x, lastPos.y );
		mTargetPos = new GSvector2();
		mTargetSize = new GSvector2();
		mAttackTimer = 0;
	}

	// 更新
	public void update(){

		super.update();

		// 攻撃処理
		attackAction();

		// 死亡処理
		deadAction();
	}

	// 攻撃処理
	private void attackAction(){

		if( mAttackTimer <= 0 ) return;

		mAttackTimer--;

		// タイマー前半は何もしない
		if( mAttackTimer > Define.ATTACK_TIME / 2 ) return;

		mLastPos = new GSvector2( mTargetPos.x, mTargetPos.y );

		// タイマー終了時の処理
		if( mAttackTimer > 0 ) return;

		mLastPos = new GSvector2( mFirstPos.x, mFirstPos.y );

		CharacterBase e = new AttackEffect( new GSvector2( mTargetPos.x + mTargetSize.x / 2, mTargetPos.y + mTargetSize.y / 2 ) );

		Application.getObj().getEffectManager().addEffectList( e );
	}

	// 死亡処理
	private void deadAction(){

		if( mDetail.getHP() > 0 ) return;

		if( mDamageTimer > 0 ) return;

		mIsDead = true;
	}

	// クリックした時
	public void click(){

		Application.getObj().getCardManager( true ).createExplanation( mID, mPos, mSize );
	}

	// 選択
	public void select(){

		if( !mIsMy ) return;

		mIsSelect = true;

		CharacterBase p = Application.getObj().getEffectManager().getPointer();

		((PointerEffect)p).setFirstPos( new GSvector2( mPos.x + mSize.x / 2, mPos.y + mSize.y / 2 ) );
	}

	// 選択解除
	public void release(){

		if( !mIsMy ) return;

		if( !mIsSelect ) return;

		// 選択解除
		mIsSelect = false;

		// マウス位置を取得
		GSvector2 mousePos = Application.getObj().getMousePos();

		// ポインターリセット
		CharacterBase p = Application.getObj().getEffectManager().getPointer();

		((PointerEffect)p).reset();

		// 敵兵士に攻撃
		if( attackEnemySoldier( mousePos ) ) return;

		// 敵軍師に攻撃
		attackEnemyTactician( mousePos );
	}

	// 敵クリーチャーに攻撃
	private boolean attackEnemySoldier( GSvector2 mousePos ){

		// マウスの位置に敵がいるか
		int enemyID = Application.getObj().getCardManager( !mIsMy ).searchPosType( Define.CARD_TYPE.ENEMYFIELD, new GSvector2( mousePos.x, mousePos.y ) );

		// いなければ終了
		if( enemyID == -1 ) return false;

		CharacterBase enemy = Application.getObj().getCardManager( !mIsMy ).searchID( enemyID );

		((SoldierCard)enemy).damage( mDetail.getAttack() );

		mAttackTimer = Define.ATTACK_TIME;

		mTargetPos = new GSvector2( enemy.getPos().x, enemy.getPos().y );

		mTargetSize = new GSvector2( enemy.getSize().x, enemy.getSize().y );

		return true;
	}

	// 敵軍師に攻撃
	private void attackEnemyTactician( GSvector2 mousePos ){

		CharacterBase t = Application.getObj().getCharacterManager().getTactician( !mIsMy );

		// マウス位置に軍師がいなければ終了
		if( !Collision.isCollisionSquareDot( t.getPos(), t.getSize(), mousePos ) ) return;

		mAttackTimer = Define.ATTACK_TIME;

		mTargetPos = new GSvector2( t.getPos().x, t.getPos().y );

		mTargetSize = new GSvector2( t.getSize().x, t.getSize().y );

		((Tactician)Application.getObj().getCharacterManager().getTactician( !mIsMy )).damage( mDetail.getAttack() );
	}

	// ドラッグ
	public void drag(){

		if( !mIsMy ) return;

		// マウス位置を取得
		GSvector2 mousePos = Application.getObj().getMousePos();

		CharacterBase p = Application.getObj().getEffectManager().getPointer();

		((PointerEffect)p).setTargetPos( new GSvector2( mousePos.x, mousePos.y ) );
	}

	// ダメージを受ける
	public void damage( int d ){

		mDetail.damage( d );

		mDamageTimer = Define.DAMAGE_TIME;
	}
}