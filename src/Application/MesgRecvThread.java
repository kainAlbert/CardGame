package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import GetMsg.GetMsg;

//メッセージ受信のためのスレッド
public class MesgRecvThread extends Thread {

	private Socket mSocket;
	public static PrintWriter mOut;				//出力用のライター

	// コンストラクタ
	public MesgRecvThread(Socket s){

		mSocket = s;

		try{
			mOut = new PrintWriter(mSocket.getOutputStream(), true);
		}catch( Exception e ){
			e.printStackTrace();
		}
	}

	//通信状況を監視し，受信データによって動作する
	public void run() {

		try{
			InputStreamReader sisr = new InputStreamReader(mSocket.getInputStream());
			BufferedReader br = new BufferedReader(sisr);

			// ループ
			while(true) {

				//データを一行分だけ読み込んでみる
				String inputLine = br.readLine();

				//読み込んだときにデータが読み込まれたかどうかをチェックする
				if (inputLine != null) {

					//デバッグ（動作確認用）にコンソールに出力する
					System.out.println(inputLine);

					GetMsg.getMsg( inputLine );
				}else{
					break;
				}

			}
			mSocket.close();
		} catch (IOException e) {
			System.err.println("エラーが発生しました: " + e);
		}
	}

	// サーバーに出力
	public static void outServer( String msg ){

		//送信データをバッファに書き出す
		mOut.println( msg );

		//送信データをフラッシュ（ネットワーク上にはき出す）する
		mOut.flush();
	}
}
