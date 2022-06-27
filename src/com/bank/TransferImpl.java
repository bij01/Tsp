package com.bank;

import java.sql.SQLException;

public class TransferImpl implements Transfer {

	@Override
	public boolean isMember(String email) {
		System.out.println("email");
		return false;
	}

	@Override
	public boolean checkBalance(String email, long amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean minus(String sender, long amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean plus(String receiver, long amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean log(String sender, String receiver, long amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showResult(String sender, String receiver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeALL() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean transfer(String sender, String receiver, long amount) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
