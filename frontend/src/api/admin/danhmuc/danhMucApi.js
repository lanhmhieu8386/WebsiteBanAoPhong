import axiosClient from "@/auth/axiosClient";

// =======================
// GET ALL
// =======================
export const getAllDanhMuc = async () => {
  return axiosClient.get("/admin/danh-muc/hien-thi");
};

// =======================
// GET DETAIL
// =======================
export const getDanhMucById = async (id) => {
  return axiosClient.get(`/admin/danh-muc/chi-tiet/${id}`);
};

// =======================
// ADD
// =======================
export const addDanhMuc = async (data) => {
  return axiosClient.post("/admin/danh-muc/them", data);
};

// =======================
// UPDATE
// =======================
export const updateDanhMuc = async (id, data) => {
  return axiosClient.put(`/admin/danh-muc/sua/${id}`, data);
};

// =======================
// DELETE (XÓA MỀM)
// =======================
export const deleteDanhMuc = async (id) => {
  return axiosClient.put(`/admin/danh-muc/xoa/${id}`);
};
