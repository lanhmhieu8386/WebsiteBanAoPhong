import axiosClient from "@/auth/axiosClient";

// =======================
// NHÂN VIÊN
// =======================

// lấy tài khoản theo id nhân viên
export const getTaiKhoanNhanVien = async (idNhanVien) => {
  return axiosClient.get(`/admin/tai-khoan/nhan-vien/${idNhanVien}`);
};

// thêm tài khoản cho nhân viên
export const addTaiKhoanNhanVien = async (idNhanVien, data) => {
  return axiosClient.post(`/admin/tai-khoan/nhan-vien/${idNhanVien}`, data);
};

// cập nhật tài khoản nhân viên
export const updateTaiKhoanNhanVien = async (idNhanVien, data) => {
  return axiosClient.put(`/admin/tai-khoan/nhan-vien/${idNhanVien}`, data);
};

// xóa tài khoản nhân viên
export const deleteTaiKhoanNhanVien = async (idNhanVien) => {
  return axiosClient.delete(`/admin/tai-khoan/nhan-vien/${idNhanVien}`);
};

// =======================
// KHÁCH HÀNG
// =======================

// lấy tài khoản theo id khách hàng
export const getTaiKhoanKhachHang = async (idKhachHang) => {
  return axiosClient.get(`/admin/tai-khoan/khach-hang/${idKhachHang}`);
};

// thêm tài khoản cho khách hàng
export const addTaiKhoanKhachHang = async (idKhachHang, data) => {
  return axiosClient.post(`/admin/tai-khoan/khach-hang/${idKhachHang}`, data);
};

// cập nhật tài khoản khách hàng
export const updateTaiKhoanKhachHang = async (idKhachHang, data) => {
  return axiosClient.put(`/admin/tai-khoan/khach-hang/${idKhachHang}`, data);
};

// xóa tài khoản khách hàng
export const deleteTaiKhoanKhachHang = async (idKhachHang) => {
  return axiosClient.delete(`/admin/tai-khoan/khach-hang/${idKhachHang}`);
};

// lấy danh sách vai trò
export const getAllVaiTro = async () => {
  return axiosClient.get("/admin/vai-tro/hien-thi");
};
