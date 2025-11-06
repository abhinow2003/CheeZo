package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.PaymentDao;
import com.ust.pos.bean.PaymentBean;
import com.ust.pos.util.InMemoryDataStore;

public class PaymentDaoImplements implements PaymentDao {

    @Override
    public String addCard(PaymentBean card) {
        for (PaymentBean p : InMemoryDataStore.PAYMENT_LIST) {
            if (p.getCreditCardNumber().equals(card.getCreditCardNumber())) return "EXISTS";
        }
        InMemoryDataStore.PAYMENT_LIST.add(card);
        return card.getCreditCardNumber();
    }

    @Override
    public PaymentBean findByCardNumber(String cardNumber) {
        for (PaymentBean p : InMemoryDataStore.PAYMENT_LIST) {
            if (p.getCreditCardNumber().equals(cardNumber)) return p;
        }
        return null;
    }

    @Override
    public boolean update(PaymentBean updated) {
        for (int i = 0; i < InMemoryDataStore.PAYMENT_LIST.size(); i++) {
            if (InMemoryDataStore.PAYMENT_LIST.get(i).getCreditCardNumber().equals(updated.getCreditCardNumber())) {
                InMemoryDataStore.PAYMENT_LIST.set(i, updated);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean processPayment(String cardNumber, double amount) {
        PaymentBean card = findByCardNumber(cardNumber);
        if (card == null || card.getBalance() < amount) return false;
        card.setBalance(card.getBalance() - amount);
        update(card);
        return true;
    }

    @Override
    public boolean refund(String cardNumber, double amount) {
        PaymentBean card = findByCardNumber(cardNumber);
        if (card == null) return false;
        card.setBalance(card.getBalance() + amount);
        update(card);
        return true;
    }
}
