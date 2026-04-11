import axiosClient from "@/auth/axiosClient";

// =======================
// GET ALL
// =======================
export const getAllThuongHieu = async () => {
  return axiosClient.get("/admin/thuong-hieu/hien-thi");
};

// =======================
// GET DETAIL
// =======================
export const getThuongHieuById = async (id) => {
  return axiosClient.get(`/admin/thuong-hieu/chi-tiet/${id}`);
};

// =======================
// ADD
// =======================
export const addThuongHieu = async (data) => {
  return axiosClient.post("/admin/thuong-hieu/them", data);
};

// =======================
// UPDATE
// =======================
export const updateThuongHieu = async (id, data) => {
  return axiosClient.put(`/admin/thuong-hieu/sua/${id}`, data);
};

// =======================
// DELETE (XÓA MỀM)
// =======================
export const deleteThuongHieu = async (id) => {
  return axiosClient.put(`/admin/thuong-hieu/xoa/${id}`);
};
