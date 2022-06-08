package mainPackage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SQLManager {
	Connection connection;
	
	public SQLManager(Connection connection) {
		this.connection = connection;
	}
	
	public void select() {
		PreparedStatement preparedStatement = null;
		try {
			// [definition] SQL statement
			String sql = "SELECT * FROM ohjbroyee ORDER BY id";
			// [prepare] SQL statement
			preparedStatement = connection.prepareStatement(sql);
			// [execute] SQL statement
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("----------result----------");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("myName");
				String bloodType = resultSet.getString("brood_type");
				float height = resultSet.getFloat("height");
				Date birthday = resultSet.getDate("birthday");
				String first_lover = resultSet.getString("first_lover");
				
				//[output]
				System.out.println("id: " + id);
				System.out.println("name: " + name);
				System.out.println("bloodType: " + bloodType);
				System.out.println("height: " + height);
				System.out.println("birthday: " + birthday);
				System.out.println("first_lover: " + first_lover);
				System.out.print("-----------------\n");
			}
		} catch (SQLException e) {
			throw new RuntimeException("EMPLOYEEテーブルのSELECTに失敗しました", e);
		} finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    System.out.println("ステートメントの解放に成功しました");
                }
            } catch (SQLException e2) {
                throw new RuntimeException("ステートメントの解放に失敗しました", e2);
            }
        }
	}
	
	public void searchFromName() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("社員名を入力してください");
		String empName = scanner.next();
		scanner.close();
		PreparedStatement preparedStatement = null;
		try {
//			String sql = "SELECT id, brood_type, height, birthday, first_lover FROM OHJBROYEE WHERE myName = ?";
			String sql = "SELECT id, myName, brood_type, height, birthday, first_lover FROM OHJBROYEE WHERE myName LIKE '%" + empName + "%'";
			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setString(1, empName);

			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("----------result----------");
			while (resultSet.next()) {
				int empNo = resultSet.getInt("id");
				String name = resultSet.getString("myName");
				String empBroodType = resultSet.getString("brood_type");
				Float empHeight = resultSet.getFloat("height");
				Date birthday = resultSet.getDate("birthday");
				String empFirstLover = resultSet.getString("first_lover");
				System.out.println("社員番号:" + empNo);
				System.out.println("社員名:" + name);
				System.out.println("血液型:" + empBroodType);
				System.out.println("身長:" + empHeight);
				System.out.println("生年月日:" + birthday);
				System.out.println("初恋の人:" + empFirstLover);
				System.out.print("-----------------\n");
			}

		} catch (SQLException e) {
			throw new RuntimeException("該当するデータが見つかりません", e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
					System.out.println("ステートメントの解放に成功しました");
				}
			} catch (SQLException e2) {
				throw new RuntimeException("ステートメントの解放に失敗しました", e2);
			}

		}

	}
	
	public void insert() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("社員番号を入力してください");
        int empNo = scanner.nextInt();
        System.out.println("社員名を入力してください");
        String empName = scanner.next();
        System.out.println("血液型を入力してください");
        String empBloodType = scanner.next();
        System.out.println("身長を入力してください");
        float empHeight = scanner.nextFloat();
        System.out.println("生年月日を入力してください");
        Date birthday = Date.valueOf(scanner.next());
        System.out.println("初恋の人を入力してください");
        String empFirstLover = scanner.next();
        scanner.close();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO OHJBROYEE (id, myName, brood_type, height, birthday, first_lover) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, empNo);
            preparedStatement.setString(2, empName);
            preparedStatement.setString(3, empBloodType);
            preparedStatement.setFloat(4, empHeight);
            preparedStatement.setDate(5, birthday);
            preparedStatement.setString(6, empFirstLover);
            int result = preparedStatement.executeUpdate();
            System.out.println("登録結果：" + result);
        } catch (SQLException e) {
            throw new RuntimeException("INSERTに失敗しました", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    System.out.println("ステートメントの解放に成功しました");
                }
            } catch (SQLException e2) {
                throw new RuntimeException("ステートメントの解放に失敗しました", e2);
            }
        }
    }
	
	public void update() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("現在の社員番号を入力してください");
		int empNo = scanner.nextInt();
		System.out.println("新しい社員番号を入力してください");
		int newEmpNo = scanner.nextInt();
		System.out.println("新しい社員名を入力してください");
		String newEmpName = scanner.next();
		System.out.println("新しい血液型を入力してください");
		String newBloodType = scanner.next();
		System.out.println("新しい身長を入力してください");
		float newHeight = scanner.nextFloat();
		System.out.println("新しい生年月日を入力してください");
		Date newBirthday = Date.valueOf(scanner.next());
		System.out.println("新しい初恋の人の名前を入力してください");
		String newFirstLover = scanner.next();
		ConnectionManager connectionManager = new ConnectionManager();
		try {
//			Connection connection = connectionManager.getConnection();
			// ステートメントの定義
			PreparedStatement preparedStatement = null;
			try {
				// SQLの定義
				String sql = "UPDATE OHJBROYEE SET id = ?, myName = ?, brood_type = ?, height = ?, birthday = ?, first_lover=? WHERE id = ?;";
				// SQLの作成(準備)
				preparedStatement = connection.prepareStatement(sql);
				// SQLバインド変数への値設定
				preparedStatement.setInt(1, newEmpNo);
				preparedStatement.setString(2, newEmpName);
				preparedStatement.setString(3, newBloodType);
				preparedStatement.setFloat(4, newHeight);
				preparedStatement.setDate(5, newBirthday);
				preparedStatement.setString(6, newFirstLover);
				preparedStatement.setInt(7, empNo);
				// SQLの実行
				int result = preparedStatement.executeUpdate();
				System.out.println("登録結果:" + result);
				connectionManager.commit();
			} catch (SQLException e) {
				connectionManager.rollback();
				throw new RuntimeException("OHJBROYEEテーブルのUPDATEに失敗しました", e);
			} finally {
				try {
					if (preparedStatement != null) {
						preparedStatement.close();
						System.out.println("ステートメントの解放に成功しました");
					}
				} catch (SQLException e) {
					throw new RuntimeException("ステートメントの解放に失敗しました", e);
				}
			}
		} finally {
			connectionManager.closeConnection();
		}
	}
	
	public void delete() {
		Scanner scanner = new Scanner(System.in);
        System.out.println("社員番号を入力してください");
        int empNo = scanner.nextInt();
        
        // ステートメントの定義
        PreparedStatement preparedStatement = null;
        try {
            // SQLの定義
            String sql = "DELETE FROM OHJBROYEE WHERE ID = ?";
            // SQLの作成(準備)
            preparedStatement = connection.prepareStatement(sql);
            // SQLバインド変数への値設定
            preparedStatement.setInt(1, empNo);
            
            // SQLの実行
            int result = preparedStatement.executeUpdate();
            System.out.println("登録結果:" + result);
            
        } catch (SQLException e) {
            throw new RuntimeException("OHJBROYEEテーブルのDELETEに失敗しました", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    System.out.println("ステートメントの解放に成功しました");
                }
            } catch (SQLException e) {
                throw new RuntimeException("ステートメントの解放に失敗しました", e);
            }
        }
	}
}
