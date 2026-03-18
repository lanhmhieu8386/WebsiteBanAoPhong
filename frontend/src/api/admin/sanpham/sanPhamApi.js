import axiosClient from "@/auth/axiosClient";

// lấy danh sách sản phẩm
export const getSanPham = () => {
  return axiosClient.get("/admin/san-pham/hien-thi");
};

export const getSanPhamById = (id) => {
  return axiosClient.get(`/admin/san-pham/chi-tiet/${id}`);
};

// thêm sản phẩm
export const addSanPham = (data) => {
  return axiosClient.post("/admin/san-pham/them", data);
};

// cập nhật
export const updateSanPham = (id, data) => {
  return axiosClient.put(`/admin/san-pham/sua/${id}`, data);
};

// xoá
export const deleteSanPham = (id) => {
  return axiosClient.delete(`/admin/san-pham/xoa/${id}`);
};
