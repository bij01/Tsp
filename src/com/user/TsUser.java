package com.user;

import java.sql.SQLException;
import com.bank.*;

public class TsUser {

	public static void main(String[] args) {
		Transfer t = new TransferImpl();
		try {
			boolean flag = t.transfer("one@daum.net", "two@naver.com", 50000L);
			if(flag) {
				System.out.println("이체 성공");
			} else {
				System.out.println("이체 실패");
			}
		} catch (SQLException se) {
			System.out.println("이체 실패(예외): " + se);
		}
		
		t.showResult("one@daum.net", "two@naver.com");
	}

}
