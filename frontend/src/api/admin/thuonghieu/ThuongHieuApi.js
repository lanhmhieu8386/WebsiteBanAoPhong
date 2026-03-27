import axiosClient from "@/auth/axiosClient";

export const getAllThuongHieu = async () => {
  return axiosClient.get("/admin/thuong-hieu/hien-thi");
};
