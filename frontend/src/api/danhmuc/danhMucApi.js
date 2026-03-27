import axiosClient from "@/auth/axiosClient";

const API_URL = "http://localhost:8080/api/customer/danh-muc";

export const getAllDanhMuc = () => {
  return axiosClient.get(`${API_URL}/hien-thi`);
};
