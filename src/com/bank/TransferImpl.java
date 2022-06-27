package com.bank;

import java.sql.*;
import java.util.Vector;

public class TransferImpl implements Transfer {
	Connection con;
	PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4, pstmt5, pstmt6;
	public String msg = "";
	public Vector result = new Vector();
	
	void connectDB() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(Info.URL, Info.USR, Info.PWD);
			con.setAutoCommit(false);
			System.out.println("오라클 접속성공");
			pstmt1 = con.prepareStatement(Sql.SQL1);
			pstmt2 = con.prepareStatement(Sql.SQL2);
			pstmt3 = con.prepareStatement(Sql.SQL3);
			pstmt4 = con.prepareStatement(Sql.SQL4);
			pstmt5 = con.prepareStatement(Sql.SQL5);
			pstmt6 = con.prepareStatement(Sql.SQL6);
		} catch (SQLException se) {
			System.out.println(se);
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
		}
	}

	@Override
	public boolean isMember(String email) {
		try {
			pstmt1.setString(1, email);
			ResultSet rs = pstmt1.executeQuery();
			if(rs.next()) return true;
		} catch (SQLException se) {
		}
		msg = "수취인 이메일을 확인 할 수 없습니다.";
		return false;
		
	}

	@Override
	public boolean checkBalance(String email, long amount) {
		try {
			pstmt2.setString(1, email);
			ResultSet rs = pstmt2.executeQuery();
			if(rs.next()) {
				long balance = rs.getLong(1);
				//System.out.println(email + " 잔액: " + balance);
				if(balance >= amount) return true;
			}
		} catch (SQLException se) {
		}
		msg = "잔액이 부족합니다.";
		return false;
	}

	@Override
	public boolean minus(String sender, long amount) {
		try{
			pstmt3.setString(1, sender);
			pstmt3.setLong(2, amount);
			pstmt3.setString(3, sender);
			int i = pstmt3.executeUpdate();
			if (i>0){
				return true;
			}
		}catch (SQLException se){
		}
		msg = "송금에 실패하였습니다.";
		return false;
	}

	@Override
	public boolean plus(String receiver, long amount) {
		try{
			pstmt4.setString(1, receiver);
			pstmt4.setLong(2, amount);
			pstmt4.setString(3, receiver);
			int i = pstmt4.executeUpdate();
			if (i>0){
				return true;
			}
		}catch (SQLException se){
		}
		msg = "수취에 실패하였습니다.";
		return false;
	}

	@Override
	public boolean log(String sender, String receiver, long amount) {
		try{
			pstmt5.setString(1, sender);
			pstmt5.setString(2, receiver);
			pstmt5.setLong(3, amount);
			int i = pstmt5.executeUpdate();
			if (i>0){
				//System.out.println("로그 기록 성공");
				return true;
			}
		}catch (SQLException se){
			System.out.println("로그 예외: " + se);
		}
		return false;
	}

	@Override
	public void showResult(String sender, String receiver) {
		try {
			pstmt6.setString(1, sender);
			pstmt6.setString(2, receiver);
			ResultSet rs = pstmt6.executeQuery();
			while(rs.next()) {
				String email = rs.getString(1);
				String name = rs.getString(2);
				long balance = rs.getLong(3);
				Date date = rs.getDate(4);
				Date date2 = rs.getDate(5);
				result.add(email + ", " + name + ", " + balance + ", " + date + ", " + date2);
			}
		} catch (SQLException se) {
			System.out.println("showResult: " + se);
		}
	}

	@Override
	public void closeALL() {
		try{
			pstmt1.close();
			pstmt2.close();
			pstmt3.close();
			pstmt4.close();
			pstmt5.close();
			pstmt6.close();
			con.close();
		}catch(SQLException se){
			System.out.println(se);
		}
	}

	@Override
	public boolean transfer(String sender, String receiver, long amount) throws SQLException {
		connectDB();
		if(isMember(receiver)) { //수취인 메일 확인
			if(checkBalance(sender, amount)) { //송금인 잔액 확인
				if (minus(sender, amount)) { //송금인 잔액 차감
					if (plus(receiver, amount)) { //수취인 잔액 증감
						if (log(sender, receiver, amount)) { //로그 기록
							//거래가 정상적으로 종료 되었을 경우
							con.commit();
							return true;
						}						
					}
				}
			}
		}
		// 거래가 정상적으로 종료되지 않았을 경우 롤백
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeALL();
		return false;
	}

	public static void main(String[] args) {
		//new TransferImpl().init();
	}

}
