import axiosClient from "@/auth/axiosClient";

// lấy danh sách sản phẩm chi tiết
export const getSanPhamChiTiet = () => {
  return axiosClient.get("/admin/san-pham-chi-tiet/hien-thi");
};

// lấy chi tiết theo id
export const getChiTietBySanPhamId = (id) => {
  return axiosClient.get(`/admin/san-pham-chi-tiet/chi-tiet/${id}`);
};

// thêm sản phẩm chi tiết
export const addSanPhamChiTiet = (data) => {
  return axiosClient.post("/admin/san-pham-chi-tiet", data);
};

// cập nhật
export const updateSanPhamChiTiet = (id, data) => {
  return axiosClient.put(`/admin/san-pham-chi-tiet/${id}`, data);
};

// xoá
export const deleteSanPhamChiTiet = (id) => {
  return axiosClient.delete(`/admin/san-pham-chi-tiet/${id}`);
};
