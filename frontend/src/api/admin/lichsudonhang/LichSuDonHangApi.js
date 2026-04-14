import axiosClient from "@/auth/axiosClient";

// =========================
// LỊCH SỬ ĐƠN HÀNG
// =========================

// Lấy toàn bộ lịch sử đơn hàng
export const getDanhSachLichSuDonHang = async () => {
  return axiosClient.get("/admin/lich-su-don-hang/hien-thi");
};

// Lấy chi tiết 1 lịch sử đơn hàng theo id
export const getDetailLichSuDonHang = async (id) => {
  return axiosClient.get(`/admin/lich-su-don-hang/detail/${id}`);
};

// Lấy danh sách lịch sử theo id hóa đơn
export const getLichSuDonHangByHoaDonId = async (idHoaDon) => {
  return axiosClient.get(`/admin/lich-su-don-hang/hoa-don/${idHoaDon}`);
};
