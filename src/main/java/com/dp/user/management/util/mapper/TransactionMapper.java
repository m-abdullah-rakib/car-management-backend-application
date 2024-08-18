package com.dp.user.management.util.mapper;

import com.dp.user.management.dto.response.ExpenseResponse;
import com.dp.user.management.dto.response.IncomeResponse;
import com.dp.user.management.dto.response.TransactionResponse;
import com.dp.user.management.model.Expense;
import com.dp.user.management.model.Income;
import com.dp.user.management.model.RecentTransactions;

public class TransactionMapper {
    public static TransactionResponse mapToResponse(Income income) {
        TransactionResponse transactionResponse = new TransactionResponse();
        mapCommonFields(transactionResponse, income.getDate(), income.getId(), income.getAmount(), income.getRemark(), income.getInvoiceFilePath(), income.getLat(), income.getLon(), income.getUserId(), income.getCarId(), income.getStatus());
        transactionResponse.setType("INCOME");
        transactionResponse.setIncomeId(income.getId());
        transactionResponse.setIncome(new IncomeResponse(income.getId(), income.getType(), income.getTips(), income.getOffer(), income.getUserId(), income.getCarId()));
        return transactionResponse;
    }

    public static TransactionResponse mapToResponse(Expense expense) {
        TransactionResponse transactionResponse = new TransactionResponse();
        mapCommonFields(transactionResponse, expense.getDate(), expense.getId(), expense.getAmount(), expense.getRemark(), expense.getInvoiceFilePath(), expense.getLat(), expense.getLon(), expense.getUserId(), expense.getCarId(), expense.getStatus());
        transactionResponse.setType("EXPENSE");
        transactionResponse.setExpenseId(expense.getId());
        transactionResponse.setExpense(new ExpenseResponse(expense.getId(), expense.getType(), expense.getParkingDurationMin(), expense.getFuelVendor(), expense.getFuelVolume(), expense.getFuelDetails()));
        return transactionResponse;
    }

    public static TransactionResponse mapToResponse(RecentTransactions recentTransactions) {
        TransactionResponse transactionResponse = new TransactionResponse();

        mapCommonFields(transactionResponse, recentTransactions.getDate(), recentTransactions.getId(), recentTransactions.getAmount(), recentTransactions.getRemark(), recentTransactions.getInvoiceFilePath(), recentTransactions.getLat(), recentTransactions.getLon(), recentTransactions.getUserId(), recentTransactions.getCarId(), recentTransactions.getStatus());

        if (recentTransactions.getSource().equals("INCOME")) {
            transactionResponse.setType("INCOME");
            transactionResponse.setIncomeId(recentTransactions.getId());
            transactionResponse.setIncome(new IncomeResponse(recentTransactions.getId(), recentTransactions.getType(), recentTransactions.getTips(), recentTransactions.getOffer(), recentTransactions.getUserId(), recentTransactions.getCarId()));
        } else {
            transactionResponse.setType("EXPENSE");
            transactionResponse.setExpenseId(recentTransactions.getId());
            transactionResponse.setExpense(new ExpenseResponse(recentTransactions.getId(), recentTransactions.getType(), recentTransactions.getParkingDurationMin(), recentTransactions.getFuelVendor(), recentTransactions.getFuelVolume(), recentTransactions.getFuelDetails()));
        }

        return transactionResponse;
    }

    private static void mapCommonFields(TransactionResponse transactionResponse, String date, Long id, Double amount, String remark, String invoiceFilePath, double lat, double lon, Long userId, Integer carId, String status) {
        transactionResponse.setId(id);
        transactionResponse.setDate(date);
        transactionResponse.setAmount(amount);
        transactionResponse.setRemark(remark);
        transactionResponse.setInvoiceFilePath(invoiceFilePath);
        transactionResponse.setLat(lat);
        transactionResponse.setLon(lon);
        transactionResponse.setUserId(userId);
        transactionResponse.setCarId(carId);
        transactionResponse.setStatus(status);
    }
}
