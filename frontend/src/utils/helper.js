import Swal from "sweetalert2";
import nprogress from "nprogress";
import "nprogress/nprogress.css";

nprogress.configure({ showSpinner: false, trickleSpeed: 200 });

export const useUtils = () => {
  const notify = {
    success: (msg) => {
      Swal.fire({
        icon: "success",
        iconColor: "#d32f2f",
        title: msg || "Thành công",
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 2000,
        timerProgressBar: true,

        didOpen: (toast) => {
          const progressBar = toast.querySelector(".swal2-timer-progress-bar");
          if (progressBar) progressBar.style.backgroundColor = "#d32f2f";
        },
      });
    },
    error: (msg) => {
      Swal.fire({
        icon: "error",
        title: "Lỗi hệ thống",
        text: msg,
        didOpen: () => {
          const container = document.querySelector(".swal2-container");
          if (container) container.style.zIndex = "99999";
        },
      });
    },
    confirm: async (msg) => {
      const result = await Swal.fire({
        title: "Xác nhận",
        text: msg,
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d32f2f",
        cancelButtonColor: "#6c757d",
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy",
        didOpen: () => {
          const container = document.querySelector(".swal2-container");
          if (container) container.style.zIndex = "99999";
        },
      });
      return result.isConfirmed;
    },
  };

  return { notify, nprogress };
};
