import axios from "axios";
import { useUtils } from "@/utils/helper";
const { notify, nprogress } = useUtils();
const axiosClient = axios.create({
  baseURL: "http://localhost:8080/api",
});

axiosClient.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      // hết hạn đăng nhập
      if (error.response.status === 401) {
        notify.error("Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại");

        localStorage.removeItem("token");
        localStorage.removeItem("loginTime");

        setTimeout(() => {
          window.location.href = "/dang-nhap";
        }, 1500);
      }

      // không có quyền
      if (error.response.status === 403) {
        notify.error("Bạn không có quyền thực hiện chức năng này");
      }
    }

    return Promise.reject(error);
  },
);

export default axiosClient;
