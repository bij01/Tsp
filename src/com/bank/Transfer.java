package com.bank;

import java.sql.SQLException;

public interface Transfer {
	boolean isMember(String email); //1. DQL(SELECT)
	boolean checkBalance(String email, long amount); //2. DQL(SELECT)
	boolean minus(String sender, long amount); //3. DDL(UPDATE)
	boolean plus(String receiver, long amount); //4. DDL(UPDATE)
	boolean log(String sender, String receiver, long amount); //5. DDL(INSERT)
	void showResult(String sender, String receiver); //6. DQL(SELECT);
	void closeALL(); //7.
	
	boolean transfer(String sender, String receiver, long amount) throws SQLException; //8.
}

/*
 1. (User가)계좌주의 이메일 유효성 확인 by sql1
 2. (User가)보내는 사람의 잔액이 이체금액보다 같거나 많은지 확인 by sql2
 3. 보내는 사람의 잔액을 이체금액만큼 차감 by sql3
 4. 받는 사람의 잔액을 이체금액만큼 증감 by sql4
 5. 이체내역을 로그에 기록 by sql5
 6. 이체결과 확인 by sql6
 7. 연결객체 닫기
 
 8. 고객 앱이 호출하기 위한 메소드 추가!
*/
