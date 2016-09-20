package Object.Card;

import java.util.ArrayList;
import java.util.List;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Application.MesgRecvThread;
import Object.Character.CharacterBase;
import Object.Character.NumLabel;
import Object.Character.Tactician;
import Object.Detail.DetailBase;

public class DeckCard extends Card{

	private NumLabel mNumLabel;
	private List<CharacterBase> mDeckList;
	private boolean mIsStartHand;

	// コンストラクタ
	public DeckCard( boolean isMy ){

		super( isMy );

		mNumLabel = new NumLabel();

		mDeckList = new ArrayList<CharacterBase>();

		mDetail = new DetailBase( isMy );
	}

	// 初期化
	public void initialize(){

		GSvector2 pos = mIsMy ?
				new GSvector2( Define.MYDECK_POS.x, Define.MYDECK_POS.y ) :
				new GSvector2( Define.ENEMYDECK_POS.x, Define.ENEMYDECK_POS.y );

		super.initialize( "card_back",
				pos,
				new GSvector2( Define.CARD_SIZE.x, Define.CARD_SIZE.y ),
				new GSvector2( Define.CARD_RESIZE.x, Define.CARD_RESIZE.y ),
				Application.getObj().getCardManager( mIsMy ).getCardID(),
				Define.CARD_TYPE.DECK.ordinal()
				);

		mNumLabel.initialize( Define.CARD_NUM_TYPE.ATTACK.ordinal(), Define.CARD_NUM_IMAGE_SIZE );

		CharacterBase t = Application.getObj().getCharacterManager().getTactician( mIsMy );

		mDeckList = DeckReader.readDeck( ((Tactician)t).getID(), mIsMy, new GSvector2( mPos.x, mPos.y ) );

		mIsStartHand = false;
	}

	// 更新
	public void update(){

		mNumLabel.updateNum( mDeckList.size(), new GSvector2( mPos.x + mSize.x / 2, mPos.y + mSize.y / 2 - Define.CARD_NUM_IMAGE_SIZE / 2 ) );

		if( mIsStartHand || !mIsMy ) return;

		for( int i=0; i<Define.FIRST_HAND_CARD; i++ ){

			drawCard();
		}

		mIsStartHand = true;
	}

	// カードを引く
	public void drawCard(){

		if( mDeckList.size() == 0 ) return;

		CharacterBase card = mDeckList.get(0);

		Application.getObj().getCardManager( mIsMy ).addCardList( card );

		mDeckList.remove(0);

		DetailBase d = ((Card)card).getDetail();
		int cardID = d.getCardID();

		// カードを引くを送信
		String msg = Application.getID() + Define.MSG + Define.MSG_DRAW_CARD + Define.MSG + cardID;
		MesgRecvThread.outServer( msg );
	}

	// IDを指定してカードを引く
	public void drawCard( int cardID ){

		for( int i=0; i<mDeckList.size(); i++ ){

			CharacterBase card = mDeckList.get(i);
			DetailBase detail = ((Card)card).getDetail();

			if( detail.getCardID() != cardID ) continue;

			Application.getObj().getCardManager( mIsMy ).addCardList( card );

			mDeckList.remove(i);

			return;
		}
	}

	// クリック
	public void click(){}

	// 選択
	public void select(){}

	// 選択解除
	public void release(){}

	// ドラッグ
	public void drag(){}

	// ゲッター
	public NumLabel getNumLabel(){ return mNumLabel; }
	public List<CharacterBase> getList(){ return mDeckList; }
}
