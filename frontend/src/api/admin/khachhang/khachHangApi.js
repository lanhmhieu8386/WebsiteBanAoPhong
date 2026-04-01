import axiosClient from "@/auth/axiosClient";

// hiển thị + tìm kiếm + phân trang
export const getAllKhachHang = async (params) => {
  return axiosClient.get("/admin/khach-hang/hien-thi", { params });
};

// lấy detail theo id
export const getDetailKhachHang = async (id) => {
  return axiosClient.get(`/admin/khach-hang/detail/${id}`);
};

// thêm khách hàng
export const addKhachHang = async (data) => {
  return axiosClient.post("/admin/khach-hang/them", data);
};

// sửa khách hàng
export const updateKhachHang = async (id, data) => {
  return axiosClient.put(`/admin/khach-hang/sua/${id}`, data);
};

// xóa khách hàng
export const deleteKhachHang = async (id) => {
  return axiosClient.delete(`/admin/khach-hang/xoa/${id}`);
};

/// tài khoản khách hàng
export const getTaiKhoanKhachHang = async (idKhachHang) => {
  return axiosClient.get(`/admin/khach-hang/${idKhachHang}/tai-khoan`);
};

export const addTaiKhoanKhachHang = async (idKhachHang, data) => {
  return axiosClient.post(`/admin/khach-hang/${idKhachHang}/tai-khoan`, data);
};

export const updateTaiKhoanKhachHang = async (idKhachHang, data) => {
  return axiosClient.put(`/admin/khach-hang/${idKhachHang}/tai-khoan`, data);
};

export const deleteTaiKhoanKhachHang = async (idKhachHang) => {
  return axiosClient.delete(`/admin/khach-hang/${idKhachHang}/tai-khoan`);
};
