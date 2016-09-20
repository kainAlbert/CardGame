package Object.Detail;

import java.util.List;

import Application.Application;
import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Object.Collision;
import Object.Card.CardManager;
import Object.Card.SoldierCard;
import Object.Character.CharacterBase;
import Object.Character.NumLabel;
import Object.Character.Tactician;

public class DetailBase extends CharacterBase{

	protected NumLabel mCostLabel;
	protected NumLabel mAttackLabel;
	protected NumLabel mHPLabel;
	protected int mCardID;
	protected String mName;
	protected int mCost;
	protected int mAttack;
	protected int mRevisionAttack;
	protected int mHP;
	protected boolean mIsSoldier;
	protected boolean mIsMy;
	protected Define.CARD_ABILITY mAbility1;
	protected Define.CARD_ABILITY mAbility2;
	protected boolean mIsPlay;
	protected int mPlayTimer;
	protected CharacterBase mSelectCharacter;

	// コンストラクタ
	public DetailBase( boolean isMy ){

		mIsMy = isMy;

		// 各数字の文字ラベル生成
		mCostLabel = new NumLabel();
		mAttackLabel = new NumLabel();
		mHPLabel = new NumLabel();
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		mCardID = cardID;
		mAbility1 = Define.CARD_ABILITY.NONE;
		mAbility2 = Define.CARD_ABILITY.NONE;
		mIsPlay = false;
		mPlayTimer = Define.CARD_PLAY_WAIT;
		mRevisionAttack = 0;

		// 詳細を読み込む
		DetailStructure str = DetailReader.readDetail( cardID );

		mCost = str.mCost;
		mName = str.mName;
		mAttack = str.mAttack;
		mHP = str.mHP;
		mIsSoldier = str.mIsSoldier;

		// 軍師ならここで終了
		if( cardID >= DefineCardID.TACTICIAN_SONKEN ){

			mPos = pos;
			mSize = size;
			return;
		}

		// 敵の場のカードでなければ裏面画像を使う
		String fileName = "detail/" + mCardID;

		if( !mIsMy && type != Define.CARD_TYPE.ENEMYFIELD.ordinal() ){

			fileName = "card_back";
		}

		// 親クラスの初期化
		super.initialize( fileName, pos, size,
				new GSvector2( Define.CARD_RESIZE.x / 2, Define.CARD_RESIZE.y / 2 ), 0, type);

		// 数値ラベルクラス初期化
		mCostLabel.initialize( Define.CARD_NUM_TYPE.COST.ordinal(), Define.CARD_NUM_IMAGE_SIZE );
		mAttackLabel.initialize( Define.CARD_NUM_TYPE.ATTACK.ordinal(), Define.CARD_NUM_IMAGE_SIZE );
		mHPLabel.initialize( Define.CARD_NUM_TYPE.HP.ordinal(), Define.CARD_NUM_IMAGE_SIZE );
	}

	// 更新
	public void update( GSvector2 pos, GSvector2 size ){

		super.update();

		mPos = pos;
		mSize = size;

		// 敵の手札なら表示しない
		if( !mIsMy && mType != Define.CARD_TYPE.ENEMYFIELD.ordinal() ) return;

		// 各数字の位置設定
		mCostLabel.updateNum( mCost, new GSvector2( pos.x, pos.y ) );

		// 兵士以外は表示しない
		if( !mIsSoldier ) return;

		mAttackLabel.updateNum( Math.max( mAttack + mRevisionAttack, 0 ), new GSvector2( pos.x, pos.y + size.y - Define.CARD_NUM_IMAGE_SIZE ) );
		mHPLabel.updateNum( mHP, new GSvector2( pos.x + size.x - Define.CARD_NUM_IMAGE_SIZE, pos.y + size.y - Define.CARD_NUM_IMAGE_SIZE ) );
	}

	// ダメージ
	public void damage( int d ){

		mHP = Math.max( mHP - d, 0 );

		mDamageTimer = Define.DAMAGE_TIME;
	}

	// 回復
	public void care( int c ){

		mHP = Math.min( mHP + c, Define.TACTICIAN_MAX_HP );
	}

	// パワー変更
	public void powerChange( int changePower ){

		mAttack = Math.max( mAttack + changePower, 0 );
	}

	// このターンのみの攻撃力アップを設定
	public void setRevisionAttack( int num ){

		mRevisionAttack += num;
	}

	// このターンのみの攻撃力アップを初期化
	public void initRevisionAttack(){

		mRevisionAttack = 0;
	}

	// カードをプレイ
	public void play(){

		mIsPlay = true;
	}

	// 使用条件
	public boolean useCondition(){ return true; }

	// 使用条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 手を離した場所が手札の位置ではないか
		boolean isPosY = mousePos.y < Define.FIELD_MYCARD_POSY + Define.CARD_SIZE.y;

		// ひとつでも満たさなければfalse
		if( !isPosY || !isHand ) return false;

		// マナ不足
		if( ((Tactician)tactician).getMana() < mCost ){

			Application.getStringLabel().setType( Define.STRING_TYPE.NOT_MANA );
			Application.getStringLabel().setPos();
			return false;
		}

		// 兵士召喚条件
		if( mIsSoldier ) return soldierCondition();

		// 呪文使用条件
		return spellCondition();
	}

	// 兵士条件
	protected boolean soldierCondition(){

		// 自分のフィールドのカードの数を取得
		int myFieldNum = Application.getObj().getCardManager( mIsMy ).searchTypeNum( Define.CARD_TYPE.MYFIELD );

		// フィールドのカードの数が5未満か
		return  myFieldNum < Define.MAX_FIELD_CARD;
	}

	// 呪文条件
	protected boolean spellCondition(){

		return true;
	}

	// 選択している兵士を返す
	protected CharacterBase getSelectSoldier( boolean isMy ){

		// マウス位置を取得
		GSvector2 mousePos = Application.getObj().getMousePos();

		// カードリスト取得
		List<CharacterBase> list = Application.getObj().getCardManager( isMy ).getCardList();

		// タイプ
		int type = isMy ? Define.CARD_TYPE.MYFIELD.ordinal() : Define.CARD_TYPE.ENEMYFIELD.ordinal();

		for( int i=0; i<list.size(); i++ ){

			if( list.get(i).getType() != type ) continue;

			if( Collision.isCollisionSquareDot( list.get(i).getPos(), list.get(i).getSize(), mousePos ) ) return list.get(i);
		}

		return null;
	}

	// 選択している軍師を返す
	protected CharacterBase getSelectTactician( boolean isMy ){

		// マウス位置を取得
		GSvector2 mousePos = Application.getObj().getMousePos();

		// 軍師を取得
		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( isMy );

		if( Collision.isCollisionSquareDot( tactician.getPos(), tactician.getSize(), mousePos ) ){

			return tactician;
		}

		return null;
	}

	// 軍師か兵士を選択する条件
	protected boolean getConditionTorS( boolean isMy ){

		if( !isSelectTorS( isMy ) ){

			Application.getStringLabel().setType( isMy ? Define.STRING_TYPE.SELECT_MY : Define.STRING_TYPE.SELECT_ENEMY );
			Application.getStringLabel().setPos();
			return false;
		}

		return true;
	}

	// 軍師か兵士を選択したか
	private boolean isSelectTorS( boolean isMy ){

		// 選択している敵兵士を取得
		mSelectCharacter = getSelectSoldier( isMy );

		if( mSelectCharacter != null ) return true;

		// 選択している軍師を取得
		mSelectCharacter = getSelectTactician( isMy );

		return mSelectCharacter != null;
	}

	// 兵士を選択する条件
	protected boolean getConditionS( boolean isMy ){

		// 選択している敵兵士を取得
		mSelectCharacter = getSelectSoldier( isMy );

		if( mSelectCharacter != null ) return true;

		Application.getStringLabel().setType( isMy ? Define.STRING_TYPE.SELECT_MY_SOLDIER : Define.STRING_TYPE.SELECT_ENEMY_SOLDIER );
		Application.getStringLabel().setPos();

		return false;
	}

	// 指定したIDの兵士を召喚
	protected void createSoldier( int cardID, boolean isMy ){

		CardManager cm = Application.getObj().getCardManager( isMy );

		// タイプ
		Define.CARD_TYPE type = isMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// Detail生成
		DetailBase detail = DetailReader.getDetail( cardID, isMy );

		detail.initialize( cardID, new GSvector2( mPos.x, mPos.y ), new GSvector2( mSize.x, mSize.y ), type.ordinal() );

		// フィールドカードを生成
		CharacterBase card = new SoldierCard( isMy );

		((SoldierCard)card).initialize(
				detail,
				new GSvector2( mPos.x, mPos.y )
				);

		// リストに追加
		cm.addCardList( card );
	}

	// 指定したアビリティか？
	public boolean isAbility( Define.CARD_ABILITY abi ){

		return mAbility1 == abi || mAbility2 == abi;
	}

	// 選択先を設定
	public void setSelectCharacter( CharacterBase select ){

		mSelectCharacter = select;
	}

	// ゲッター
	public NumLabel getCostLabel(){ return mCostLabel; }
	public NumLabel getAttackLabel(){ return mAttackLabel; }
	public NumLabel getHPLabel(){ return mHPLabel; }
	public int getCardID(){ return mCardID; }
	public String getName(){ return mName; }
	public int getCost(){ return mCost; }
	public int getAttack(){ return Math.max( mAttack + mRevisionAttack, 0 ); }
	public int getHP(){ return mHP; }
	public boolean getIsSoldier(){ return mIsSoldier; }
	public boolean getIsPlay(){ return mIsPlay; }
	public CharacterBase getSelectCharacter(){ return mSelectCharacter; }

}
