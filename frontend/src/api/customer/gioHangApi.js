import axiosClient from "@/auth/axiosClient";

const API_URL = "http://localhost:8080/api/gio-hang";

export const addGioHang = (data) => {
  return axiosClient.post(`${API_URL}/them`, data);
};
