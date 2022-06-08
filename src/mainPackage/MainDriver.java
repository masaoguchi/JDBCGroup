package mainPackage;

import java.sql.Connection;
import java.util.Scanner;

public class MainDriver {

	public static void main(String[] args) {
		while(true) {
			ConnectionManager connectionManager = new ConnectionManager();
	        
	        try {
	            Connection connection = connectionManager.getConnection();
	            SQLManager sqlManager = new SQLManager(connection);
	            
	            String message = "１、一覧表示\n２、名前検索\n３、メンバー登録\n４、メンバー更新\n５、メンバー削除\n９、終了\n＞選択してください";
	            System.out.println(message);
	            
	            Scanner scanner = new Scanner(System.in);
	            int option = scanner.nextInt();
	            
	            switch (option) {
	            	case 1:
	            		sqlManager.select();
	            		break;
	            	case 2:
	            		sqlManager.searchFromName();
	            		break;
	            	case 3:
	            		sqlManager.insert();
	            		break;
	            	case 4:
	            		sqlManager.update();
	            		break;
	            	case 5:
	            		sqlManager.delete();
	            		break;
	            	default:
	            		break;
	            }
	            
	            connectionManager.commit();
	        } catch (RuntimeException e) {
	          connectionManager.rollback();
	          throw new RuntimeException("OHJBROYEEテーブルの処理に失敗しました", e);
	        }finally {
	            connectionManager.closeConnection();
	            System.out.println("\n続けますか？\nY＞続ける");
	            Scanner scanner = new Scanner(System.in);
	            String answer = scanner.next();
	            
	            if (!answer.equals("Y")) {
	            	scanner.close();
	            	break;
	            }
	            
	        }
		}
		

	}
	
	

}
