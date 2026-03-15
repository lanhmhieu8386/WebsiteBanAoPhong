import axiosClient from "@/auth/axiosClient";

export const login = (data) => {
  return axiosClient.post("/auth/login", data);
};
export const register = (data) => {
  return axiosClient.post("/auth/register", data);
};
