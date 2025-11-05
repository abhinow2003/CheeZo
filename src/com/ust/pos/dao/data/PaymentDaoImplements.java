package com.ust.pos.dao.data;


import com.ust.pos.dao.domain.PaymentDao;
import com.ust.pos.bean.PaymentBean;
import com.ust.pos.util.InMemoryDataStore;

public class PaymentDaoImplements implements PaymentDao {

    @Override
    public String addCard(PaymentBean card) {
        if (card == null || card.getCreditCardNumber() == null) return "FAIL";
        InMemoryDataStore.PAYMENT_MAP.put(card.getCreditCardNumber(), card);
        return card.getCreditCardNumber();
    }

    @Override
    public PaymentBean findByCardNumber(String cardNumber) {
        return InMemoryDataStore.PAYMENT_MAP.get(cardNumber);
    }

    @Override
    public boolean update(PaymentBean card) {
        if (card == null || card.getCreditCardNumber() == null) return false;
        InMemoryDataStore.PAYMENT_MAP.put(card.getCreditCardNumber(), card);
        return true;
    }

    @Override
    public boolean processPayment(String cardNumber, double amount) {
        PaymentBean card = InMemoryDataStore.PAYMENT_MAP.get(cardNumber);
        if (card == null) return false;

        if (card.getBalance() >= amount) {
            double newBalance = card.getBalance() - amount;
            card.setBalance(newBalance);
            InMemoryDataStore.PAYMENT_MAP.put(cardNumber, card);
            return true;
        }
        return false; 
    }

    @Override
    public boolean refund(String cardNumber, double amount) {
        PaymentBean card = InMemoryDataStore.PAYMENT_MAP.get(cardNumber);
        if (card == null) return false;

        double newBalance = card.getBalance() + amount;
        card.setBalance(newBalance);
        InMemoryDataStore.PAYMENT_MAP.put(cardNumber, card);
        return true;
    }
}
