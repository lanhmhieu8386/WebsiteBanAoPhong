import axiosClient from "@/auth/axiosClient";

const API_URL = "http://localhost:8080/api/customer/hoa-don";

export const thanhToanDonHang = (formData) => {
  return axiosClient.post(`${API_URL}/checkout`, formData);
};

export const donHangCuaToi = () => {
  return axiosClient.get(`${API_URL}/my-order`);
};

export const hoaDonChiTiet = (id) => {
  return axiosClient.get(`${API_URL}/${id}`);
};

export const huyDonHang = (id) => {
  return axiosClient.put(`${API_URL}/cancel/${id}`);
};
