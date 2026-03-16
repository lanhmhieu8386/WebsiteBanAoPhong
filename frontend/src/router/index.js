import TrangChu from "@/component/customer/GioiThieu/TrangChu.vue";
import Login from "@/component/customer/Login/Login.vue";
import DangKy from "@/component/customer/Register/DangKy.vue";
import GioiThieu from "@/views/GioiThieu.vue";
import { createRouter, createWebHistory } from "vue-router";

//admin
import TongQuan from "@/views/admin/TongQuan.vue";
import AdminLayout from "@/views/admin/AdminLayout.vue";
import HoaDon from "@/views/admin/HoaDon.vue";
import SanPham from "@/views/admin/SanPham.vue";
import DanhMuc from "@/views/admin/DanhMuc.vue";
import SanPhamChiTiet from "@/views/admin/SanPhamChiTiet.vue";

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

  //admin
  {
    path: "/admin",
    component: AdminLayout,
    children: [
      { path: "tong-quan", component: TongQuan },
      { path: "hoa-don", component: HoaDon },
      { path: "san-pham", component: SanPham },
      {
        path: "san-pham/:id",
        name: "SanPhamChiTiet",
        component: SanPhamChiTiet,
      },
      { path: "danh-muc", component: DanhMuc },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
