import TrangChu from "@/component/customer/GioiThieu/TrangChu.vue";
import Login from "@/component/customer/Login/Login.vue";
import DangKy from "@/component/customer/Register/DangKy.vue";
import GioiThieu from "@/views/GioiThieu.vue";
import { createRouter, createWebHistory } from "vue-router";

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
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
