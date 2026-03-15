import TrangChu from "@/component/customer/GioiThieu/TrangChu.vue";
import Login from "@/component/customer/Login/Login.vue";
import DangKy from "@/component/customer/Register/DangKy.vue";
import GioiThieu from "@/views/GioiThieu.vue";
import { createRouter, createWebHistory } from "vue-router";
import { useUtils } from "@/utils/helper";
import Testloginthanhcong from "@/component/admin/testloginthanhcong.vue";
const { notify } = useUtils();
const routes = [
  {
    path: "/",
    component: GioiThieu,
    children: [
      {
        path: "",
        name: "trangChu",
        component: TrangChu,
      },
    ],
  },

  {
    path: "/dang-nhap",
    name: "dangNhap",
    component: Login,
  },

  {
    path: "/dang-ky",
    name: "dangKy",
    component: DangKy,
  },

  { path: "/admin/tong-quan", name: "tongQuan", component: Testloginthanhcong },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");
  const loginTime = localStorage.getItem("loginTime");
  const timeout = 30 * 60 * 1000;

  if (token && loginTime) {
    const now = Date.now();

    if (now - loginTime > timeout) {
      localStorage.removeItem("token");
      localStorage.removeItem("loginTime");

      notify.error("Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại");

      setTimeout(() => {
        next("/dang-nhap");
      }, 1500);

      return;
    }
  }

  if (to.meta.requiresAuth && !token) {
    notify.error("Bạn cần đăng nhập để tiếp tục");

    setTimeout(() => {
      next("/dang-nhap");
    }, 1500);
  } else {
    next();
  }
});

export default router;
