package com.bank;

public class Sql {
	static final String SQL1="select * from ACC where EMAIL=?"; //회원확인
	static final String SQL2="select BALANCE from ACC where EMAIL=?"; //잔액확인
	static final String SQL3="update ACC set BALANCE=((select BALANCE from ACC where EMAIL=?)-?) where EMAIL=?"; //차감
	static final String SQL4="update ACC set BALANCE=((select BALANCE from ACC where EMAIL=?)+?) where EMAIL=?"; //증감
	static final String SQL5="insert into TRAN_LOG values(TRAN_LOG_SEQ.nextval, ?, ?, ?, CURRENT_TIMESTAMP)"; //로그추가
	static final String SQL6="select * from ACC where EMAIL=? or EMAIL=?"; //거래한 두 사람의 계좌 확인
}
