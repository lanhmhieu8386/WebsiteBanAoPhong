import axiosClient from "@/auth/axiosClient";

// ==================== DANH SÁCH ====================

// Lấy danh sách đơn hàng: trạng thái 1, 2, 3
export const getDanhSachDonHang = async () => {
  return axiosClient.get("/admin/hoa-don/don-hang");
};

// Lấy danh sách hóa đơn: trạng thái 4, 5
export const getDanhSachHoaDon = async () => {
  return axiosClient.get("/admin/hoa-don/hoa-don");
};

// ==================== CHI TIẾT ====================

// Xem chi tiết hóa đơn
export const getDetailHoaDon = async (id) => {
  return axiosClient.get(`/admin/hoa-don/detail/${id}`);
};

// ==================== ĐỔI TRẠNG THÁI ====================

// Đổi trạng thái bên tab Đơn hàng
export const capNhatTrangThaiDonHang = async (
  id,
  trangThaiMoi,
  nguoiCapNhat = "Admin",
) => {
  return axiosClient.put(`/admin/hoa-don/don-hang/${id}/trang-thai`, null, {
    params: {
      trangThaiMoi,
      nguoiCapNhat,
    },
  });
};

// Đổi trạng thái bên tab Hóa đơn
export const capNhatTrangThaiHoaDon = async (
  id,
  trangThaiMoi,
  nguoiCapNhat = "Admin",
) => {
  return axiosClient.put(`/admin/hoa-don/hoa-don/${id}/trang-thai`, null, {
    params: {
      trangThaiMoi,
      nguoiCapNhat,
    },
  });
};
