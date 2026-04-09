import axiosClient from "@/auth/axiosClient";
const API_URL = "http://localhost:8080/api/customer/san-pham";

export const getAllSanPham = () => {
  return axiosClient.get(`${API_URL}/hien-thi`);
};

export const getSanPhamByDanhMuc = (idDanhMuc) => {
  return axiosClient.get(`${API_URL}/by-danh-muc/${idDanhMuc}`);
};

export const detailSanPham = (idSanPham) => {
  return axiosClient.get(`${API_URL}/detail/${idSanPham}`);
};
