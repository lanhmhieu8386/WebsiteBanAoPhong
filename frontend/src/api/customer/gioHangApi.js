import axiosClient from "@/auth/axiosClient";

const API_URL = "http://localhost:8080/api/gio-hang";

export const addGioHang = (data) => {
  return axiosClient.post(`${API_URL}/them`, data);
};

export const hienThiGioHang = () => {
  return axiosClient.get(`${API_URL}/my-cart`);
};
export const updateGioHang = (data) => {
  return axiosClient.put(`${API_URL}/update`, data);
};

export const deleteGioHang = (id) => {
  return axiosClient.delete(`${API_URL}/remove/${id}`);
};
