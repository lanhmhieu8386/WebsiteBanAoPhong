<script setup>
import { ref, onMounted } from "vue";
import router from "@/router";
import iconGioHang from "@/assets/icons/giohang.png";
const token = ref(null);
const name = ref("");

onMounted(() => {
  token.value = localStorage.getItem("token");
  name.value = localStorage.getItem("name");
});

const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("name");

  token.value = null;
  name.value = "";

  router.replace("/");
};
</script>

<template>
  <nav
    class="navbar navbar-expand-lg navbar-dark bg-danger fixed-top custom-nav"
  >
    <div class="container d-flex align-items-center">
      <router-link to="/" class="navbar-brand fw-bold brand-logo">
        ChiWiWis
      </router-link>

      <ul class="navbar-nav mx-auto gap-2">
        <li class="nav-item">
          <router-link to="/" class="nav-link">TRANG CHỦ</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/gioi-thieu" class="nav-link"
            >GIỚI THIỆU</router-link
          >
        </li>
        <li class="nav-item">
          <router-link to="/san-pham" class="nav-link">SẢN PHẨM</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/mua-sam" class="nav-link">MUA SẮM</router-link>
        </li>
      </ul>

      <div class="d-flex align-items-center gap-3">
        <template v-if="!token">
          <a
            href="tel:0332654198"
            class="contact-link text-white d-none d-xl-block"
          >
            <span class="phone-icon">📞</span> 0332654198
          </a>

          <div class="auth-buttons d-flex gap-2">
            <router-link to="/dang-nhap" class="btn btn-login">
              Đăng nhập
            </router-link>
            <router-link to="/dang-ky" class="btn btn-register">
              Đăng ký
            </router-link>
          </div>
        </template>

        <div v-else class="d-flex align-items-center gap-3">
          <router-link to="/gio-hang" class="cart-wrapper">
            <img :src="iconGioHang" alt="Giỏ hàng" class="cart-image-icon" />
            <span class="cart-badge">0</span>
          </router-link>

          <div class="user-info" @click="router.push('/tai-khoan')">
            <i class="bi bi-person-circle user-icon"></i>
            <div class="user-name">
              {{ name }}
            </div>
            <button class="logout-btn" @click.stop="logout">Đăng xuất</button>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.cart-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  transition: transform 0.2s ease;
}

/* Chỉnh ảnh to lên ở đây */
.cart-image-icon {
  width: 32px; /* Tăng lên 32px cho rõ */
  height: 32px;
  object-fit: contain;
}

/* Chỉnh lại vị trí số 0 cho khớp với icon to */
.cart-badge {
  position: absolute;
  top: -5px; /* Nhích lên một tí */
  right: -10px; /* Đẩy ra ngoài một tí */
  background-color: white;
  color: #dc3545;
  font-size: 11px;
  font-weight: 800;
  padding: 1px 5px;
  border-radius: 50%;
  min-width: 18px;
  text-align: center;
  border: 1px solid #dc3545; /* Thêm viền nhẹ cho nổi nếu thích */
}

.cart-wrapper:hover {
  transform: scale(1.1);
}
/* Giữ nguyên toàn bộ phần CSS cũ của mày bên dưới */
.logout-btn {
  background: none;
  border: none;
  color: white;
  font-size: 12px;
  cursor: pointer;
}
.logout-btn:hover {
  text-decoration: underline;
}
.user-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: white;
  cursor: pointer;
}
.user-icon {
  font-size: 26px;
}
.user-name {
  font-size: 12px;
  font-weight: 600;
}
.custom-nav {
  height: 80px;
  background-color: rgba(237, 161, 169, 0.95) !important;
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  transition: all 0.3s ease;
}
.brand-logo {
  font-size: 1.4rem;
  letter-spacing: -0.5px;
}
.nav-link {
  font-size: 0.85rem;
  font-weight: 700;
  color: rgba(255, 255, 255, 0.85) !important;
  padding: 8px 12px !important;
  transition: color 0.2s ease;
}
.nav-link:hover {
  color: #fff !important;
}
.nav-link.router-link-active {
  color: #fff !important;
  position: relative;
}
.nav-link.router-link-active::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 12px;
  right: 12px;
  height: 2px;
  background: white;
  border-radius: 2px;
}
.contact-link {
  text-decoration: none;
  font-weight: 600;
  font-size: 0.95rem;
}
.btn {
  padding: 8px 20px;
  border-radius: 50px;
  font-size: 0.9rem;
  font-weight: 600;
  transition: all 0.3s transform;
}
.btn:active {
  transform: scale(0.95);
}
.btn-login {
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.4);
  background: transparent;
}
.btn-login:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: white;
  color: white;
}
.btn-register {
  background: white;
  color: #dc3545;
  border: none;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}
.btn-register:hover {
  background: #f8f9fa;
  transform: translateY(-1px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
  color: #c82333;
}
</style>
