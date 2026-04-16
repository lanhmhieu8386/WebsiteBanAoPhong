<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import router from "@/router";
import iconGioHang from "@/assets/icons/giohang.png";
import logo from "@/assets/images/logo.png";

const token = ref(null);
const name = ref("");
const cartCount = ref(0);
const isScrolled = ref(false);

const updateCart = () => {
  cartCount.value = Number(localStorage.getItem("cartCount")) || 0;
};

const handleScroll = () => {
  isScrolled.value = window.scrollY > 30;
};

onMounted(() => {
  token.value = localStorage.getItem("token");
  name.value = localStorage.getItem("name");
  updateCart();
  window.addEventListener("update-cart", updateCart);
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("update-cart", updateCart);
  window.removeEventListener("scroll", handleScroll);
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
  <nav :class="['ethereal-nav', { 'nav-scrolled': isScrolled }]">
    <div class="light-beam"></div>

    <div class="container-fluid px-lg-5 nav-inner">
      <div class="nav-side">
        <router-link to="/" class="brand-monolith">
          <img :src="logo" alt="logo" class="brand-logo" />
          <div class="brand-text">
            <span class="brand-main">CHIWIWIS</span>
            <span class="brand-tag">Studio</span>
          </div>
        </router-link>
      </div>
      <div class="nav-center d-none d-lg-flex">
        <router-link to="/" class="nav-link-modern">
          <span class="link-text">Home</span>
          <span class="link-dot"></span>
        </router-link>
        <router-link to="/gioi-thieu" class="nav-link-modern">
          <span class="link-text">Giới thiệu</span>
          <span class="link-dot"></span>
        </router-link>
        <router-link to="/san-pham" class="nav-link-modern">
          <span class="link-text">Sản phẩm</span>
          <span class="link-dot"></span>
        </router-link>
        <router-link to="/mua-sam" class="nav-link-modern">
          <span class="link-text">Mua sắm</span>
          <span class="link-dot"></span>
        </router-link>
      </div>

      <div class="nav-side justify-content-end">
        <template v-if="!token">
          <div class="auth-module">
            <router-link to="/dang-nhap" class="auth-text"
              >Đăng nhập</router-link
            >
            <router-link to="/dang-ky" class="auth-btn-pill"
              >Tạo tài khoản</router-link
            >
          </div>
        </template>

        <div v-else class="portal-module">
          <router-link to="/gio-hang" class="cart-container">
            <div class="cart-orb">
              <img :src="iconGioHang" class="cart-svg" alt="cart" />
              <div class="cart-qty" v-if="cartCount > 0">{{ cartCount }}</div>
            </div>
          </router-link>

          <div class="profile-capsule" @click="router.push('/tai-khoan')">
            <div class="user-avatar-wrap">
              <div class="avatar-bg"></div>
              <span class="avatar-char">{{ name.charAt(0) }}</span>
            </div>
            <div class="user-details">
              <span class="u-greeting">Welcome</span>
              <span class="u-name">{{ name }}</span>
            </div>
            <button class="logout-gate" @click.stop="logout">
              <i class="bi bi-arrow-right-short"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200;400;700&family=Playfair+Display:italic&display=swap");

.brand-monolith {
  display: flex;
  align-items: center;
  gap: 10px;
  text-decoration: none;
}

.brand-logo {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 50%;
}

.brand-text {
  display: flex;
  flex-direction: column;
}
.ethereal-nav {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100px;
  background: rgba(255, 255, 255, 0.01);
  backdrop-filter: blur(0px);
  display: flex;
  align-items: center;
  z-index: 10000;
  transition: all 0.7s cubic-bezier(0.2, 1, 0.2, 1);
  font-family: "Montserrat", sans-serif;
}

.nav-scrolled {
  height: 80px;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(25px);
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.02);
}

.nav-inner {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-side {
  flex: 1;
  display: flex;
  align-items: center;
}

.brand-main {
  font-size: 1.6rem;
  font-weight: 700;
  color: #413c3c;
  letter-spacing: -1px;
}

.brand-tag {
  font-family: "Playfair Display", serif;
  font-size: 0.7rem;
  color: #999;
  letter-spacing: 2px;
  margin-top: -5px;
}

/* --- Menu: Floating Links --- */
.nav-center {
  gap: 50px;
}

.nav-link-modern {
  text-decoration: none;
  position: relative;
  padding: 10px 0;
}

.link-text {
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  color: #666;
  transition: all 0.4s ease;
}

.link-dot {
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 4px;
  height: 4px;
  background: #000;
  border-radius: 50%;
  transform: translateX(-50%) scale(0);
  transition: transform 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.nav-link-modern:hover .link-text,
.nav-link-modern.router-link-active .link-text {
  color: #000;
  letter-spacing: 4px;
}

.nav-link-modern:hover .link-dot,
.nav-link-modern.router-link-active .link-dot {
  transform: translateX(-50%) scale(1);
}

/* --- Auth & Buttons --- */
.auth-module {
  display: flex;
  align-items: center;
  gap: 25px;
}

.auth-text {
  text-decoration: none;
  font-size: 0.8rem;
  font-weight: 600;
  color: #1a1a1a;
}

.auth-btn-pill {
  text-decoration: none;
  background: #1a1a1a;
  color: #fff;
  padding: 12px 30px;
  border-radius: 50px;
  font-size: 0.75rem;
  font-weight: 600;
  letter-spacing: 1px;
  transition: all 0.4s;
}

.auth-btn-pill:hover {
  background: #444;
  transform: translateY(-2px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}

/* --- Portal & Cart --- */
.portal-module {
  display: flex;
  align-items: center;
  gap: 30px;
}

.cart-orb {
  width: 45px;
  height: 45px;
  background: #f8f8f8;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  border: 1px solid #eee;
  transition: all 0.3s;
}

.cart-orb:hover {
  background: #fff;
  transform: rotate(15deg) scale(1.1);
}

.cart-svg {
  width: 18px;
  opacity: 0.8;
}

.cart-qty {
  position: absolute;
  top: -2px;
  right: -2px;
  background: #000;
  color: #fff;
  font-size: 9px;
  font-weight: 800;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* --- Profile: Marble Card --- */
.profile-capsule {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 6px 6px 6px 15px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 50px;
  cursor: pointer;
  transition: 0.4s;
}

.profile-capsule:hover {
  border-color: #ccc;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
}

.user-avatar-wrap {
  position: relative;
  width: 34px;
  height: 34px;
}

.avatar-bg {
  position: absolute;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #f0f0f0, #d9d9d9);
  border-radius: 50%;
}

.avatar-char {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-weight: 700;
  font-size: 0.9rem;
  color: #1a1a1a;
}

.user-details {
  display: flex;
  flex-direction: column;
}

.u-greeting {
  font-size: 0.6rem;
  color: #aaa;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.u-name {
  font-size: 0.8rem;
  font-weight: 700;
  color: #1a1a1a;
}

.logout-gate {
  width: 34px;
  height: 34px;
  border: none;
  background: #f8f8f8;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  transition: all 0.3s;
}

.logout-gate:hover {
  background: #ffebeb;
  color: #ff4d4d;
}

/* --- Decorations --- */
.light-beam {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 1px;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(0, 0, 0, 0.05),
    transparent
  );
}

/* Responsive */
@media (max-width: 991px) {
  .ethereal-nav {
    height: 70px;
  }
  .nav-center {
    display: none;
  }
}
</style>
