import axiosClient from "@/auth/axiosClient";

// =======================
// GET ALL
// =======================
export const getAllKichCo = async () => {
  return axiosClient.get("/admin/kich-co/hien-thi");
};

// =======================
// GET DETAIL
// =======================
export const getKichCoById = async (id) => {
  return axiosClient.get(`/admin/kich-co/chi-tiet/${id}`);
};

// =======================
// ADD
// =======================
export const addKichCo = async (data) => {
  return axiosClient.post("/admin/kich-co/them", data);
};

// =======================
// UPDATE
// =======================
export const updateKichCo = async (id, data) => {
  return axiosClient.put(`/admin/kich-co/sua/${id}`, data);
};

// =======================
// DELETE (XÓA CỨNG)
// =======================
export const deleteKichCo = async (id) => {
  return axiosClient.delete(`/admin/kich-co/xoa/${id}`);
};
