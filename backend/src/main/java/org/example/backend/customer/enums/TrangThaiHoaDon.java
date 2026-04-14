package org.example.backend.customer.enums;

public enum TrangThaiHoaDon {

    CHO_XAC_NHAN(1, "Chờ xác nhận"),
    DA_XAC_NHAN(2, "Đã xác nhận"),
    DANG_GIAO(3, "Đang giao"),
    HOAN_THANH(4, "Hoàn thành"),
    HUY(5, "Đã hủy");

    private final int value;
    private final String label;

    TrangThaiHoaDon(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static TrangThaiHoaDon fromValue(int value) {
        for (TrangThaiHoaDon t : values()) {
            if (t.value == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}