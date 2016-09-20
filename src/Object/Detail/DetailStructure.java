package Object.Detail;

public class DetailStructure {

	public int mCardID;
	public String mName;
	public boolean mIsSoldier;
	public int mCost;
	public int mAttack;
	public int mHP;

	// コンストラクタ
	public DetailStructure( int cardID, String name, boolean isSoldier, int cost, int attack, int hp ){

		mCardID = cardID;
		mName = name;
		mIsSoldier = isSoldier;
		mCost = cost;
		mAttack = attack;
		mHP = hp;
	}

	public DetailStructure() {
	}
}
