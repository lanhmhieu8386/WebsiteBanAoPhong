import axiosClient from "@/auth/axiosClient";

// =======================
// GET ALL
// =======================
export const getAllHinhAnh = async () => {
  return axiosClient.get("/admin/hinh-anh/hien-thi");
};

// =======================
// GET DETAIL
// =======================
export const getHinhAnhById = async (id) => {
  return axiosClient.get(`/admin/hinh-anh/chi-tiet/${id}`);
};

// =======================
// ADD
// =======================
export const addHinhAnh = async (data) => {
  return axiosClient.post("/admin/hinh-anh/them", data);
};

// =======================
// UPDATE
// =======================
export const updateHinhAnh = async (id, data) => {
  return axiosClient.put(`/admin/hinh-anh/sua/${id}`, data);
};

// =======================
// DELETE (XÓA CỨNG)
// =======================
export const deleteHinhAnh = async (id) => {
  return axiosClient.delete(`/admin/hinh-anh/xoa/${id}`);
};
