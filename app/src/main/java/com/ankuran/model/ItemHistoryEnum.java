package com.ankuran.model;

public class ItemHistoryEnum {
    public static final int HISTORY_TYPE_ADD = 1;
    public static final int HISTORY_TYPE_REMOVE = 2;

    public static final int HISTORY_REASON_STOCK_NEW = 1;
    public static final int HISTORY_REASON_STOCK_TRANSFER = 1;
    public static final int HISTORY_REASON_STOCK_TRANSFER_REVERSAL = 1;
    public static final int HISTORY_REASON_SALE = 1;

    public enum HistoryType {
        ADD, REMOVE
    }

    public enum HistoryReason {
        STOCK_NEW, STOCK_TRANSFER, STOCK_TRANSFER_REVERSAL, SALE,OTHERS
    }

    public static HistoryType getHistoryTypeFrom(Long historyId) {
        if (historyId == null) {
            return null;
        } else if (historyId == HISTORY_TYPE_ADD) {
            return HistoryType.ADD;
        } else if (historyId == HISTORY_TYPE_REMOVE) {
            return HistoryType.REMOVE;
        } else {
            return null;
        }
    }

    public static int getHistoryTypeIdFrom(HistoryType historyType) {
        if (HistoryType.ADD.equals(historyType))
            return HISTORY_TYPE_ADD;
        else if (HistoryType.REMOVE.equals(historyType))
            return HISTORY_TYPE_REMOVE;
        else
            return 0;
    }

    public static HistoryReason getHistoryReasonFrom(Long historyReasonId) {
        if (historyReasonId == null) {
            return null;
        } else if (historyReasonId == HISTORY_REASON_STOCK_NEW) {
            return HistoryReason.STOCK_NEW;
        } else if (historyReasonId == HISTORY_REASON_STOCK_TRANSFER) {
            return HistoryReason.STOCK_TRANSFER;
        } else if (historyReasonId == HISTORY_REASON_STOCK_TRANSFER_REVERSAL) {
            return HistoryReason.STOCK_TRANSFER_REVERSAL;
        } else if (historyReasonId == HISTORY_REASON_SALE) {
            return HistoryReason.SALE;
        } else {
            return null;
        }
    }

    public static int getHistoryReasonIdFrom(HistoryReason historyReason) {
        if (HistoryReason.STOCK_NEW.equals(historyReason)) {
            return HISTORY_REASON_STOCK_NEW;
        } else if (HistoryReason.STOCK_TRANSFER.equals(historyReason)) {
            return HISTORY_REASON_STOCK_TRANSFER;
        } else if (HistoryReason.STOCK_TRANSFER_REVERSAL.equals(historyReason)) {
            return HISTORY_REASON_STOCK_TRANSFER_REVERSAL;
        } else if (HistoryReason.SALE.equals(historyReason)) {
            return HISTORY_REASON_SALE;
        } else {
            return 0;
        }
    }
}
