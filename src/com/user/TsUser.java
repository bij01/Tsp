package com.user;

import java.sql.SQLException;
import com.bank.*;

public class TsUser {

	public static void main(String[] args) {
		TransferImpl t = new TransferImpl();
		try {
			boolean flag = t.transfer("one@daum.net", "two@naver.com", 50000L);
			if(flag) {
				System.out.println("이체 성공");
				t.showResult("one@daum.net", "two@naver.com");
				System.out.println("보낸 사람: " + t.result.get(0));
				System.out.println("받는 사람: " + t.result.get(1));
			} else {
				System.out.println("이체 실패: " + t.msg);
			}
		} catch (SQLException se) {
			System.out.println("이체 실패(예외): " + se);
		}
		
		
	}

}
