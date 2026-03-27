sửa cái này
<script setup>
import { ref, watch } from "vue";
import { useRoute } from "vue-router";
import logo from "@/assets/logo/logo.png";

/* ===== PHÂN QUYỀN ===== */
const role = Number(localStorage.getItem("role"));

defineProps({
  collapsed: Boolean,
});

/* ===== ICON MENU ===== */
import hoaDon from "@/assets/icons/datban.png";
import sanPham from "@/assets/icons/datban.png";
import danhMuc from "@/assets/icons/danhmuc.png";
import nhanVien from "@/assets/icons/taiKhoan.png";
import { useRouter } from "vue-router";

const router = useRouter();

const logout = () => {
  router.push("/dang-nhap");
};

const route = useRoute();

/* ===== MENU ===== */
const menus = [
  {
    name: "Hóa đơn",
    path: "/admin/hoa-don",
    icon: hoaDon,
  },
  {
    name: "Sản phẩm",
    path: "/admin/san-pham",
    icon: sanPham,
  },
  {
    name: "Danh mục",
    path: "/admin/danh-muc",
    icon: danhMuc,
  },
  {
    name: "Quản lý nhân viên",
    path: "/admin/nhan-vien",
    icon: nhanVien,
  },
];

/* ===== QUẢN LÝ SUBMENU ===== */
const openMenus = ref({});

const toggleSubmenu = (name) => {
  openMenus.value[name] = !openMenus.value[name];
};

watch(
  () => route.path,
  () => {
    menus.forEach((menu) => {
      if (menu.children) {
        openMenus.value[menu.name] = menu.children.some((child) =>
          route.path.startsWith(child.path),
        );
      }
    });
  },
  { immediate: true },
);

/* ===== LỌC MENU THEO ROLE ===== */
const filteredMenus = menus
  .filter((item) => {
    if (!item.role) return true;
    return item.role.includes(role);
  })
  .map((item) => {
    if (!item.children) return item;

    return {
      ...item,
      children: item.children.filter((child) => {
        if (!child.role) return true;
        return child.role.includes(role);
      }),
    };
  });

/* ===== ACTIVE MENU ===== */
const isActive = (path) => route.path === path;

const isParentActive = (item) => {
  if (!item.children) return route.path.startsWith(item.path);
  return item.children.some((child) => route.path.startsWith(child.path));
};
</script>

<template>
  <div class="sidebar d-flex flex-column">
    <!-- LOGO -->
    <div class="logo-container text-center align-items-center gap-2 px-3 py-3">
      <img :src="logo" width="100" />
      <span v-if="!collapsed" class="brand-name"></span>
    </div>
    <!-- MENU -->
    <ul class="nav flex-column px-3 py-4 gap-1">
      <li v-for="item in filteredMenus" :key="item.name">
        <!-- MENU KHÔNG SUB -->
        <router-link
          v-if="!item.children"
          :to="item.path"
          class="nav-link d-flex align-items-center gap-3 px-3 py-2 rounded-3 menu-item"
          active-class="menu-active"
        >
          <img :src="item.icon" width="18" height="18" />
          <span v-if="!collapsed">{{ item.name }}</span>
        </router-link>
      </li>
    </ul>

    <!-- LOGOUT -->
    <div class="logout-container px-3 pb-4">
      <button class="logout-btn w-100 px-3 py-2" @click="logout">
        <span v-if="!collapsed">Đăng xuất</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.menu-item {
  font-family: var(--font-family);
  font-size: 14px;
  font-weight: 500;
  color: var(--color-text-muted);
  cursor: pointer;
  border-radius: 8px;
  transition: all 0.15s ease;
  text-decoration: none;
}

.menu-item:hover {
  background-color: #f5f7fb;
}

.menu-active {
  background-color: #eef3ff !important;
  color: #2f5bea !important;
  font-weight: 600;
}

.submenu-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 12px;
  border-radius: var(--radius-sm);
  font-family: var(--font-family);
  font-size: 14px;
  font-weight: 400;
  color: var(--color-text);
  text-decoration: none;
  cursor: pointer;
  transition: background 0.15s ease;
}

.submenu-item:hover {
  background-color: rgba(92, 226, 250, 0.15);
}

.submenu-active {
  color: #2f5bea !important;
  font-weight: 500;
}

ul {
  list-style: none;
  padding-left: 0;
  margin: 0;
}

.submenu {
  overflow: hidden;
}

:deep(.collapsed) .menu-item {
  justify-content: center;
  padding-left: 10px !important;
  padding-right: 10px !important;
}

.sidebar {
  width: 240px;
  min-width: 240px;
  height: 100vh;
  background: white;
  border-right: 1px solid #eee;
}
.logout-btn {
  border: none;
  background: #ffecec;
  color: #e53935;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  transition: 0.2s;
}

.logout-btn:hover {
  background: #ffd6d6;
}
.sidebar {
  width: 240px;
  min-width: 240px;
  height: 100vh;
  background: white;
  border-right: 1px solid #eee;
  display: flex;
  flex-direction: column;
}

.logout-container {
  margin-top: auto;
}
</style>
