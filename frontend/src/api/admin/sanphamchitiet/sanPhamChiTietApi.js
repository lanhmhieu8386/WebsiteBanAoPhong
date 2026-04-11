import axiosClient from "@/auth/axiosClient";

// lấy danh sách sản phẩm chi tiết theo id sản phẩm
export const getChiTietBySanPhamId = (id) => {
  return axiosClient.get(`/admin/san-pham-chi-tiet/chi-tiet/san-pham/${id}`);
};

// lấy 1 sản phẩm chi tiết theo id
export const getSanPhamChiTietById = (id) => {
  return axiosClient.get(`/admin/san-pham-chi-tiet/detail/${id}`);
};

// thêm sản phẩm chi tiết
export const addSanPhamChiTiet = (data) => {
  return axiosClient.post("/admin/san-pham-chi-tiet/them", data);
};

// cập nhật sản phẩm chi tiết
export const updateSanPhamChiTiet = (id, data) => {
  return axiosClient.put(`/admin/san-pham-chi-tiet/sua/${id}`, data);
};

// xóa mềm sản phẩm chi tiết
export const deleteSanPhamChiTiet = (id) => {
  return axiosClient.put(`/admin/san-pham-chi-tiet/xoa/${id}`);
};
