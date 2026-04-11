import axiosClient from "@/auth/axiosClient";

// =======================
// GET ALL
// =======================
export const getAllMauSac = async () => {
  return axiosClient.get("/admin/mau-sac/hien-thi");
};

// =======================
// GET DETAIL
// =======================
export const getMauSacById = async (id) => {
  return axiosClient.get(`/admin/mau-sac/chi-tiet/${id}`);
};

// =======================
// ADD
// =======================
export const addMauSac = async (data) => {
  return axiosClient.post("/admin/mau-sac/them", data);
};

// =======================
// UPDATE
// =======================
export const updateMauSac = async (id, data) => {
  return axiosClient.put(`/admin/mau-sac/sua/${id}`, data);
};

// =======================
// DELETE (XÓA CỨNG)
// =======================
export const deleteMauSac = async (id) => {
  return axiosClient.delete(`/admin/mau-sac/xoa/${id}`);
};
