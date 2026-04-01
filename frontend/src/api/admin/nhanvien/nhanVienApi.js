import axiosClient from "@/auth/axiosClient";

// hiển thị + tìm kiếm + phân trang
export const getAllNhanVien = async (params) => {
  return axiosClient.get("/admin/nhan-vien/hien-thi", { params });
};

// lấy detail theo id
export const getDetailNhanVien = async (id) => {
  return axiosClient.get(`/admin/nhan-vien/detail/${id}`);
};

// thêm nhân viên
export const addNhanVien = async (data) => {
  return axiosClient.post("/admin/nhan-vien/them", data);
};

// sửa nhân viên
export const updateNhanVien = async (id, data) => {
  return axiosClient.put(`/admin/nhan-vien/sua/${id}`, data);
};

// xóa nhân viên
export const deleteNhanVien = async (id) => {
  return axiosClient.delete(`/admin/nhan-vien/xoa/${id}`);
};

/// tài khoản nhân viên
export const getTaiKhoanNhanVien = async (idNhanVien) => {
  return axiosClient.get(`/admin/nhan-vien/${idNhanVien}/tai-khoan`);
};

export const addTaiKhoanNhanVien = async (idNhanVien, data) => {
  return axiosClient.post(`/admin/nhan-vien/${idNhanVien}/tai-khoan`, data);
};

export const updateTaiKhoanNhanVien = async (idNhanVien, data) => {
  return axiosClient.put(`/admin/nhan-vien/${idNhanVien}/tai-khoan`, data);
};

export const deleteTaiKhoanNhanVien = async (idNhanVien) => {
  return axiosClient.delete(`/admin/nhan-vien/${idNhanVien}/tai-khoan`);
};
