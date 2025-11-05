package com.ust.pos.dao.domain;

import com.ust.pos.bean.PaymentBean;

public interface PaymentDao {
    String addCard(PaymentBean card);
    PaymentBean findByCardNumber(String cardNumber);
    boolean update(PaymentBean card);
    boolean processPayment(String cardNumber, double amount);
    boolean refund(String cardNumber, double amount);
}

