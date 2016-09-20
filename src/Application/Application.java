package Application;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Object.ObjectManager;
import Object.Character.StringLabel;

public class Application extends JFrame implements MouseListener,MouseMotionListener {

	public static Panel mPanel;
	public static ObjectManager mObj;		// オブジェクト管理者
	public static SelectTactician mSelectTactician;
	public static Turn mTurn;
	public static StringLabel mStringLabel;
	public static int mID;								// プレイヤーID

	public Application() {

		//IPアドレスの入力ダイアログを開く
		String ipAddress = JOptionPane.showInputDialog(null,"IPアドレスを入力してください","IPアドレスの入力",JOptionPane.QUESTION_MESSAGE);

		//入力がないときは，"localhost"とする
		if(ipAddress.equals("") ){ ipAddress = "localhost"; }

		// パネル生成
		mPanel = new Panel( this );
		this.add(mPanel);

		// パネルの設定
		mPanel.setLayout(null);
		mPanel.addMouseListener( this );
		mPanel.addMouseMotionListener( this );

		//ウィンドウを作成する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MyClient");
		setSize( (int)(Define.WINDOW_SIZE.x + Define.WINDOW_REVISION.x), (int)(Define.WINDOW_SIZE.y + Define.WINDOW_REVISION.y));

		// オブジェクト管理者生成
		mObj = new ObjectManager( this );

		// 文字画像生成
		mStringLabel = new StringLabel();

		// 軍師選択
		mSelectTactician = new SelectTactician();

		// ターン
		mTurn = new Turn();

		// IDの初期化
		mID = 0;

		//サーバに接続する
		Socket socket = null;
		try {
			//10000はポート番号．IP Addressで接続するPCを決めて，ポート番号でそのPC上動作するプログラムを特定する
			socket = new Socket(ipAddress, 10000);
		} catch (UnknownHostException e) {
			System.err.println("ホストの IP アドレスが判定できません: " + e);
		} catch (IOException e) {
			System.err.println("エラーが発生しました: " + e);
		}

		//受信用のスレッドを作成する
		MesgRecvThread mrt = new MesgRecvThread(socket);

		//スレッドを動かす（Runが動く）
		mrt.start();
	}

	// メイン
	public static void main(String[] args) {

		// アプリケーションを生成
		Application app = new Application();

		// 文字画像初期化
		mStringLabel.initialize();

		// 画面表示
		app.setVisible(true);

		int timer = 0;

		while( !mSelectTactician.getIsSelect() ){

			// FPS処理
			setFPS();

			timer++;
			if( timer > 180 ){
				System.out.println("notStart");
				timer = 0;
			}
		}

		//初期化
		mTurn.initialize();
		mObj.initialize();

		while( true ){

			// FPS処理
			setFPS();

			// 更新
			mTurn.update();
			mObj.update();
			mStringLabel.update();

			// 再描画
			app.repaint();
		}
	}

	// fps処理
	private static void setFPS(){

		long error = 0;
		int fps = 60;
		long idealSleep = (1000 << 16) / fps;
		long oldTime;
		long newTime = System.currentTimeMillis() << 16;

		oldTime = newTime;
		newTime = System.currentTimeMillis() << 16;
		long sleepTime = idealSleep - (newTime - oldTime) - error; // 休止できる時間
		if (sleepTime < 0x20000) sleepTime = 0x20000; // 最低でも2msは休止
		oldTime = newTime;

		try{
			Thread.sleep(sleepTime >> 16); // 休止
		}catch( Exception e ){
			e.printStackTrace();
		}

		newTime = System.currentTimeMillis() << 16;
		error = newTime - oldTime - sleepTime; // 休止時間の誤差
	}

	//ボタンをクリックしたときの処理
	public void mouseClicked(MouseEvent e) {

		if( !mSelectTactician.getIsSelect() ){

			mSelectTactician.click();
			return;
		}

		if( !mObj.getIsStart() || mObj.getIsEnd() ) return;

		mObj.getCardManager( true ).mouseMove( Define.MOUSE_STATUS_TYPE.CLICK );
		mObj.getCardManager( false ).mouseMove( Define.MOUSE_STATUS_TYPE.CLICK );
		mObj.getCharacterManager().getTactician(true).click();
		mObj.getCharacterManager().getTactician(false).click();

		mTurn.click();
	}

	//マウスがオブジェクトに入ったときの処理
	public void mouseEntered(MouseEvent e) {}

	//マウスがオブジェクトから出たときの処理
	public void mouseExited(MouseEvent e) {}

	//マウスでオブジェクトを押したときの処理（クリックとの違いに注意）
	public void mousePressed(MouseEvent e) {

		if( !mObj.getIsStart() || mObj.getIsEnd() ) return;

		if( !mTurn.getIsMyTurn() ) return;

		mObj.getCardManager( true ).mouseMove( Define.MOUSE_STATUS_TYPE.SELECT );
		mObj.getCharacterManager().getTactician(true).select();
	}

	//マウスで押していたオブジェクトを離したときの処理
	public void mouseReleased(MouseEvent e) {

		if( !mObj.getIsStart() || mObj.getIsEnd() ) return;

		if( !mTurn.getIsMyTurn() ) return;

		mObj.getCardManager( true ).mouseMove( Define.MOUSE_STATUS_TYPE.RELEASE );
		mObj.getCharacterManager().getTactician(true).release();
	}

	//マウスでオブジェクトとをドラッグしているときの処理
	public void mouseDragged(MouseEvent e) {

		if( !mObj.getIsStart() || mObj.getIsEnd() ) return;

		if( !mTurn.getIsMyTurn() ) return;

		mObj.setMousePos( e.getPoint() );

		mObj.getCardManager( true ).mouseMove( Define.MOUSE_STATUS_TYPE.DRAG );
		mObj.getCharacterManager().getTactician(true).drag();
	}

	//マウスがオブジェクト上で移動したときの処理
	public void mouseMoved(MouseEvent e){

		mObj.setMousePos( e.getPoint() );
	}

	// セッター
	public static void setID( int id ){ mID = id; }

	// ゲッター
	public static Panel getPanel(){ return mPanel; }
	public static ObjectManager getObj(){ return mObj; }
	public static SelectTactician getSelectTactician(){ return mSelectTactician; }
	public static Turn getTurn(){ return mTurn; }
	public static StringLabel getStringLabel(){ return mStringLabel; }
	public static int getID(){ return mID; }
}
