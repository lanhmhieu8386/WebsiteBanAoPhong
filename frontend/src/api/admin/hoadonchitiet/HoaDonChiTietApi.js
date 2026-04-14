import axiosClient from "@/auth/axiosClient";

// Lấy danh sách chi tiết theo hóa đơn
export const getDanhSachHoaDonChiTiet = async (idHoaDon) => {
  return axiosClient.get(`/admin/hoa-don-chi-tiet/hoa-don/${idHoaDon}`);
};

// Lấy chi tiết 1 dòng hóa đơn chi tiết
export const getDetailHoaDonChiTiet = async (id) => {
  return axiosClient.get(`/admin/hoa-don-chi-tiet/detail/${id}`);
};
