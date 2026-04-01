import axiosClient from "@/auth/axiosClient";

const API_URL = "http://localhost:8080/api/khach-hang";

export const getThongTin = () => {
  const email = localStorage.getItem("email");

  return axiosClient.get(`${API_URL}/thong-tin`, {
    params: { email },
  });
};
export const updateThongTin = (data, email) => {
  return axiosClient.put(`/api/khach-hang/cap-nhat?email=${email}`, data);
};
