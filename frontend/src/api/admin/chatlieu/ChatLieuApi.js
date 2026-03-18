import axiosClient from "@/auth/axiosClient";

export const getAllChatLieu = async () => {
  return axiosClient.get("/admin/chat-lieu/hien-thi");
};
