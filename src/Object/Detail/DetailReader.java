package Object.Detail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import Application.Define;
import Application.DefineCardID;
import Object.Detail.DetailList.Gi.GiAblation;
import Object.Detail.DetailList.Gi.GiCallCharge;
import Object.Detail.DetailList.Gi.GiHoga;
import Object.Detail.DetailList.Gi.GiKakoton;
import Object.Detail.DetailList.Gi.GiKakuka;
import Object.Detail.DetailList.Gi.GiOi;
import Object.Detail.DetailList.Gi.GiShooting;
import Object.Detail.DetailList.Gi.GiSoso;
import Object.Detail.DetailList.Gi.GiTyoko;
import Object.Detail.DetailList.Gi.GiWeaken;
import Object.Detail.DetailList.Go.GoFireBall;
import Object.Detail.DetailList.Go.GoFlames;
import Object.Detail.DetailList.Go.GoIntellect;
import Object.Detail.DetailList.Go.GoJohujin;
import Object.Detail.DetailList.Go.GoLightning;
import Object.Detail.DetailList.Go.GoRikuson;
import Object.Detail.DetailList.Go.GoSekihekiBigFire;
import Object.Detail.DetailList.Go.GoSonsyoko;
import Object.Detail.DetailList.Go.GoSyuyu;
import Object.Detail.DetailList.Go.GoTerror;
import Object.Detail.DetailList.Gun.GunCatastrophe;
import Object.Detail.DetailList.Gun.GunEnsyo;
import Object.Detail.DetailList.Gun.GunGensi;
import Object.Detail.DetailList.Gun.GunKojun;
import Object.Detail.DetailList.Gun.GunKosonsan;
import Object.Detail.DetailList.Gun.GunRiju;
import Object.Detail.DetailList.Gun.GunRyohu;
import Object.Detail.DetailList.Gun.GunSyukuyu;
import Object.Detail.DetailList.Gun.GunTyokaku;
import Object.Detail.DetailList.Gun.GunTyosen;
import Object.Detail.DetailList.Other.OtherArrowShot;
import Object.Detail.DetailList.Other.OtherHigherCharge;
import Object.Detail.DetailList.Other.OtherHigherInfantry;
import Object.Detail.DetailList.Other.OtherHigherSpear;
import Object.Detail.DetailList.Other.OtherMidiumCharge;
import Object.Detail.DetailList.Other.OtherMidiumInfantry;
import Object.Detail.DetailList.Other.OtherMidiumSpear;
import Object.Detail.DetailList.Other.OtherNoviceCharge;
import Object.Detail.DetailList.Other.OtherNoviceInfantry;
import Object.Detail.DetailList.Other.OtherNoviceSpear;
import Object.Detail.DetailList.Syoku.SyokuGien;
import Object.Detail.DetailList.Syoku.SyokuGigantic;
import Object.Detail.DetailList.Syoku.SyokuKanginpei;
import Object.Detail.DetailList.Syoku.SyokuKankogo;
import Object.Detail.DetailList.Syoku.SyokuKanu;
import Object.Detail.DetailList.Syoku.SyokuKotyu;
import Object.Detail.DetailList.Syoku.SyokuNature;
import Object.Detail.DetailList.Syoku.SyokuReinforcement;
import Object.Detail.DetailList.Syoku.SyokuRyubi;
import Object.Detail.DetailList.Syoku.SyokuTyohi;
import Object.Detail.DetailList.Tactician.TacticianSibai;
import Object.Detail.DetailList.Tactician.TacticianSonken;
import Object.Detail.DetailList.Tactician.TacticianSyokaturyo;
import Object.Detail.DetailList.Tactician.TacticianTotaku;

public class DetailReader {

	public static DetailStructure readDetail( int cardID ){

		BufferedReader br = null;

		try{
			File file = new File( "txt/" + Define.CARD_LIST_FILE_NAME + ".txt" );
			br = new BufferedReader(new FileReader(file));

		}catch( FileNotFoundException e ){
			e.printStackTrace();
		}

		// 読み込んで返す
		return readText( br, cardID );
	}

	// テキストファイルの読み込み
	private static DetailStructure readText( BufferedReader br, int cardID ){

		if( br == null ) return null;

		String str = "";
		String[] item = null;
		DetailStructure eStr = null;

		// 同じIDが見つかるまで
		while( str != null ){

			try{
				str = br.readLine();

				item = str.split("\t");

				if( Integer.parseInt(item[0]) == cardID ) break;

				if( Integer.parseInt(item[0]) == -1 ) return null;

			}catch( Exception e ){}

		}

		eStr = new DetailStructure(
				cardID,
				item[1],
				Integer.parseInt(item[2]) == 1,
				Integer.parseInt(item[3]),
				Integer.parseInt(item[4]),
				Integer.parseInt(item[5])
				);

		return eStr;
	}

	// IDによってDetailを生成
	public static DetailBase getDetail( int cardID, boolean isMy ){



		switch( cardID ){

		// 呉
		case DefineCardID.SYUYU:
			return new GoSyuyu( isMy );
		case DefineCardID.RIKUSON:
			return new GoRikuson( isMy );
		case DefineCardID.SONSYOKO:
			return new GoSonsyoko( isMy );
		case DefineCardID.JOHUJIN:
			return new GoJohujin( isMy );
		case DefineCardID.LIGHTNING:
			return new GoLightning( isMy );
		case DefineCardID.FIREBALL:
			return new GoFireBall( isMy );
		case DefineCardID.FLAMES:
			return new GoFlames( isMy );
		case DefineCardID.SEKIHEKI_LARGE_FIRE:
			return new GoSekihekiBigFire( isMy );
		case DefineCardID.INTELLECT:
			return new GoIntellect( isMy );
		case DefineCardID.TERROR:
			return new GoTerror( isMy );

		// 蜀
		case DefineCardID.RYUBI:
			return new SyokuRyubi( isMy );
		case DefineCardID.KANU:
			return new SyokuKanu( isMy );
		case DefineCardID.TYOHI:
			return new SyokuTyohi( isMy );
		case DefineCardID.KOTYU:
			return new SyokuKotyu( isMy );
		case DefineCardID.GIEN:
			return new SyokuGien( isMy );
		case DefineCardID.KANGINPEI:
			return new SyokuKanginpei( isMy );
		case DefineCardID.KANKOGO:
			return new SyokuKankogo( isMy );
		case DefineCardID.REINFORCEMENT:
			return new SyokuReinforcement( isMy );
		case DefineCardID.GIGANTIC:
			return new SyokuGigantic( isMy );
		case DefineCardID.NATURE:
			return new SyokuNature( isMy );

		// 魏
		case DefineCardID.SOSO:
			return new GiSoso( isMy );
		case DefineCardID.KAKOTON:
			return new GiKakoton( isMy );
		case DefineCardID.TYOKO:
			return new GiTyoko( isMy );
		case DefineCardID.KAKUKA:
			return new GiKakuka( isMy );
		case DefineCardID.HOGA:
			return new GiHoga( isMy );
		case DefineCardID.OI:
			return new GiOi( isMy );
		case DefineCardID.WEAKEN:
			return new GiWeaken( isMy );
		case DefineCardID.CALL_CHARGE:
			return new GiCallCharge( isMy );
		case DefineCardID.SHOOTING:
			return new GiShooting( isMy );
		case DefineCardID.ABLATION:
			return new GiAblation( isMy );

		// 群
		case DefineCardID.RYOHU:
			return new GunRyohu( isMy );
		case DefineCardID.TYOKAKU:
			return new GunTyokaku( isMy );
		case DefineCardID.KOJUN:
			return new GunKojun( isMy );
		case DefineCardID.RIJU:
			return new GunRiju( isMy );
		case DefineCardID.SYUKUYU:
			return new GunSyukuyu( isMy );
		case DefineCardID.GENSI:
			return new GunGensi( isMy );
		case DefineCardID.KOSONSAN:
			return new GunKosonsan( isMy );
		case DefineCardID.ENSYO:
			return new GunEnsyo( isMy );
		case DefineCardID.TYOSEN:
			return new GunTyosen( isMy );
		case DefineCardID.CATASTROPHE:
			return new GunCatastrophe( isMy );

		// 共通
		case DefineCardID.NOVICE_INFANTRY:
			return new OtherNoviceInfantry( isMy );
		case DefineCardID.MIDIUM_INFANTRY:
			return new OtherMidiumInfantry( isMy );
		case DefineCardID.HIGHER_INFANTRY:
			return new OtherHigherInfantry( isMy );
		case DefineCardID.NOVICE_CHARGE:
			return new OtherNoviceCharge( isMy );
		case DefineCardID.MIDIUM_CHARGE:
			return new OtherMidiumCharge( isMy );
		case DefineCardID.HIGHER_CHARGE:
			return new OtherHigherCharge( isMy );
		case DefineCardID.NOVICE_SPEAR:
			return new OtherNoviceSpear( isMy );
		case DefineCardID.MIDIUM_SPEAR:
			return new OtherMidiumSpear( isMy );
		case DefineCardID.HIGHER_SPEAR:
			return new OtherHigherSpear( isMy );
		case DefineCardID.ARROW_SHOT:
			return new OtherArrowShot( isMy );

		// 軍師
		case DefineCardID.TACTICIAN_SONKEN:
			return new TacticianSonken( isMy );
		case DefineCardID.TACTICIAN_SYOKATURYO:
			return new TacticianSyokaturyo( isMy );
		case DefineCardID.TACTICIAN_SIBAI:
			return new TacticianSibai( isMy );
		case DefineCardID.TACTICIAN_TOTAKU:
			return new TacticianTotaku( isMy );

		default:
			return new DetailBase( isMy );
		}
	}
}
