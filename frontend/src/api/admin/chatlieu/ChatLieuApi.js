import axiosClient from "@/auth/axiosClient";

// =======================
// GET ALL
// =======================
export const getAllChatLieu = async () => {
  return axiosClient.get("/admin/chat-lieu/hien-thi");
};

// =======================
// GET DETAIL
// =======================
export const getChatLieuById = async (id) => {
  return axiosClient.get(`/admin/chat-lieu/chi-tiet/${id}`);
};

// =======================
// ADD
// =======================
export const addChatLieu = async (data) => {
  return axiosClient.post("/admin/chat-lieu/them", data);
};

// =======================
// UPDATE
// =======================
export const updateChatLieu = async (id, data) => {
  return axiosClient.put(`/admin/chat-lieu/sua/${id}`, data);
};

// =======================
// DELETE (XÓA MỀM)
// =======================
export const deleteChatLieu = async (id) => {
  return axiosClient.put(`/admin/chat-lieu/xoa/${id}`);
};
