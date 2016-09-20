package Application;

public interface Define {

	// ウィンドウ関連
	GSvector2 WINDOW_REVISION = new GSvector2( 8, 34 );
	GSvector2 WINDOW_SIZE = new GSvector2( 1000, 750 );

	// エフェクト
	GSvector2 IMPACT_EFFECT_RESIZE = new GSvector2( 240, 240 );
	int IMPACT_EFFECT_TIME = 5;
	GSvector2 POINTER_EFFECT_RESIZE = new GSvector2( 256, 256 );
	double POINTER_EFFECT_SIZE_X = 50;
	GSvector2 ATTACK_EFFECT_RESIZE = new GSvector2( 240, 240 );
	GSvector2 ATTACK_EFFECT_SIZE = new GSvector2( 200, 200 );
	int ATTACK_EFFECT_TIME = 3;
	GSvector2 WINLOSE_EFFECT_SIZE = new GSvector2( WINDOW_SIZE.x *0.7, WINDOW_SIZE.y * 0.7 );
	GSvector2 WINLOSE_EFFECT_RESIZE = new GSvector2( 256, 128 );


	// カード本体関連
	GSvector2 CARD_SIZE = new GSvector2( 80, 120 );
	GSvector2 CARD_RESIZE = new GSvector2( 400, 600 );
	double CARD_MAX_SPEED = 100.0;
	double CARD_MIN_SPEED = 1.0;
	int MOUSE_ON_TIME = 40;
	GSvector2 MYDECK_POS = new GSvector2( WINDOW_SIZE.x - CARD_SIZE.x - 50, WINDOW_SIZE.y / 2 + 30 );
	GSvector2 ENEMYDECK_POS = new GSvector2( WINDOW_SIZE.x - CARD_SIZE.x - 50, WINDOW_SIZE.y / 2 - CARD_SIZE.y - 30 );

	// マウス関連
	enum MOUSE_STATUS_TYPE{
		CLICK, SELECT, RELEASE, DRAG
	}

	// フィールド関連
	double FIELD_MYHAND = WINDOW_SIZE.y - CARD_SIZE.y * 1.8;
	double FIELD_ENEMYHAND = CARD_SIZE.y * 0.8;
	double FIELD_MYCARD_POSY = FIELD_MYHAND - CARD_SIZE.y;
	double FIELD_ENEMYCARD_POSY = CARD_SIZE.y + 100;
	double[] FIELD_CARD_POSX = { 260 ,360 ,460 ,560 ,660, 760 };
	int MAX_FIELD_CARD = 6;

	// カード情報関連
	GSvector2 CARD_EXPLANATION_SIZE = new GSvector2( 400, 600 );
	int CARD_EXPLANATION_TIME = 300;
	double CARD_EXPLANATION_RIGHT_X = WINDOW_SIZE.x - CARD_EXPLANATION_SIZE.x;
	double CARD_EXPLANATION_LEFT_X = 0;
	double CARD_EXPLANATION_Y = ( WINDOW_SIZE.y - CARD_EXPLANATION_SIZE.y ) / 2;
	int EXPLANATION_STR_SIZE = 30;
	int MAX_HAND_CARD = 10;
	int FIRST_HAND_CARD = 3;
	int CARD_PLAY_WAIT = 60;
	enum CARD_TYPE{
			NONE, DECK, MYHAND, ENEMYHAND, MYFIELD, ENEMYFIELD
	}

	// カードの数値関連
	int CARD_NUM_IMAGE_SIZE = 30;
	int TACTICIAN_NUM_IMAGE_SIZE = 60;
	GSvector2 CARD_NUM_IMAGE_RESIZE = new GSvector2( 64, 64 );
	enum CARD_NUM_TYPE{
		NONE, COST, ATTACK, HP
	}
	int DAMAGECARE_NUM_SIZE = 120;
	int DAMAGECARE_NUM_TIME = 60;

	// クリーチャー関連
	int ATTACK_TIME = 10;
	int DAMAGE_TIME = 20;

	// 軍師関連
	GSvector2 TACTICIAN_SIZE = new GSvector2( 200, 200 );
	GSvector2 TACTICIAN_RESIZE = new GSvector2( 256, 256 );
	GSvector2 TACTICIAN_MYPOS = new GSvector2( 30, WINDOW_SIZE.y / 2 + CARD_SIZE.y / 2 );
	GSvector2 TACTICIAN_ENEMYPOS = new GSvector2( 30, WINDOW_SIZE.y / 2 - TACTICIAN_SIZE.y - CARD_SIZE.y / 2 );
	enum TACTICIAN_ID{
		SONKEN, SYOKATURYO, SIBAI, TOTAKU
	}
	String[] TACTICIAN_IMAGE_NAME = { "tactician/t_sonken", "tactician/t_syokaturyo", "tactician/t_sibai", "tactician/t_totaku" };
	String[] DECK_FILE_NAME = { "go", "syoku", "gi", "gun" };
	String CARD_LIST_FILE_NAME = "cardlist";
	int TACTICIAN_MAX_HP = 30;
	int TACTICIAN_POWER_MANA = 2;

	// ターン関連
	int TURN_DISTANCE_TIME = 90;
	GSvector2 TURN_BUTTON_IMAGE_SIZE = new GSvector2( 100, 100 );
	GSvector2 TURN_BUTTON_IMAGE_RESIZE = new GSvector2( 128, 128 );
	GSvector2 TURN_BUTTON_IMAGE_POS = new GSvector2( 80, WINDOW_SIZE.y / 2 - TURN_BUTTON_IMAGE_SIZE.y / 2 );

	// 文字関連
	String STRING_FILE_NAME = "string";
	GSvector2 STRING_SIZE = new GSvector2( 300, 100 );
	GSvector2 STRING_RESIZE = new GSvector2( 300, 100 );
	GSvector2 TURN_STRING_POS = new GSvector2( -1000, WINDOW_SIZE.y / 2 - STRING_SIZE.y / 2 );
	double TURN_STRING_MOVE = -30;
	int STRING_TIME = 60;
	enum STRING_TYPE{
		 MYTURN, ENDTURN, LOAD, PREPARATION, SELECT_ENEMY, SELECT_ENEMY_SOLDIER, SELECT_MY, SELECT_MY_SOLDIER, ATTACK_TAUNT, NOT_MANA, ALREADY_ATTACK
	}

	// カードアビリティ
	enum CARD_ABILITY{
		NONE, CHARGE, TAUNT, SPELL, BATTLECRY, DEATHRATTLE
	}

	// メッセージ
	String MSG = "__";
	String MSG_START_TURN = "MsgStartTurn";
	String MSG_SET_TACTICIAN = "MsgSetTactician";
	String MSG_SET_START = "MsgSetStart";
	String MSG_DRAW_CARD = "MsgDrawCard";
	String MSG_CHANGE_TURN = "MsgChangeTurn";
	String MSG_PUT_HANDCARD = "MsgPutHandCard";
	String MSG_PLAY_SPELL = "MsgPlaySpell";
	String MSG_ATTACK = "MsgAttack";
	String MSG_TACTICIAN_POWER = "MsgTacticianPower";
	String MSG_POINTER_FIRST = "MsgPointerFirst";
	String MSG_POINTER_TARGET = "MsgPointerTarget";
	String MSG_POINTER_RESET = "MsgPointerReset";

	// 終了処理
	int END_TIMER = 300;
	int[] END_IMPACT_TIMER = { 240, 220, 200, 180, 160, 140, 120, 100 };
}




