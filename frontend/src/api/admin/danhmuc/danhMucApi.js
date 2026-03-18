import axiosClient from "@/auth/axiosClient";

export const getAllDanhMuc = async () => {
  return axiosClient.get("/admin/danh-muc/hien-thi");
};
