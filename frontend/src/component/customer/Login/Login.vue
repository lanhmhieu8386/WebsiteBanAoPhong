<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUtils } from "@/utils/helper";
const router = useRouter();
const { notify, nprogress } = useUtils();
import { login } from "@/api/customer/authApi";
const role = ref("customer");
const username = ref("");
const password = ref("");
const rememberMe = ref(false);
const isLoading = ref(false);

const handleLogin = async () => {
  try {
    isLoading.value = true;
    nprogress.start();

    const res = await login({
      email: username.value,
      password: password.value,
    });

    const data = res.data;

    console.log("LOGIN DATA:", data);

    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);
    localStorage.setItem("email", data.email);
    localStorage.setItem("loginTime", Date.now());
    localStorage.setItem("name", data.name);

    notify.success("Đăng nhập thành công");

    // redirect theo role
    if (data.role === "ADMIN" || data.role === "NHAN_VIEN") {
      router.push("/admin/tong-quan");
    } else {
      router.push("/");
    }
  } catch (err) {
    notify.error(err.response?.data?.message || "Đăng nhập thất bại");
  } finally {
    isLoading.value = false;
    nprogress.done();
  }
};

const goToRegister = () => {
  router.push("/dang-ky");
};
</script>

<template>
  <div class="login-wrapper">
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
    </div>

    <div
      class="container d-flex align-items-center justify-content-center min-vh-100"
    >
      <div class="login-card">
        <div class="text-center mb-4">
          <!-- <div class="logo-wrapper mb-2">
            <img
              src="@/assets/images/logodatn.png"
              alt="Logo"
              class="main-logo"
            />
          </div> -->
          <h2 class="fw-bold text-dark-gradient">ChiWiWi xin chào</h2>
          <p class="text-muted small">Vui lòng chọn vai trò để tiếp tục</p>
        </div>

        <div class="role-selector mb-4">
          <div :class="['glider', role]"></div>
          <button
            type="button"
            :class="{ active: role === 'customer' }"
            @click="role = 'customer'"
          >
            Khách hàng
          </button>
          <button
            type="button"
            :class="{ active: role === 'admin' }"
            @click="role = 'admin'"
          >
            Quản trị viên
          </button>
        </div>

        <form @submit.prevent="handleLogin">
          <div class="input-modern mb-3">
            <label>Tên đăng nhập *</label>
            <div class="input-wrapper">
              <i class="bi bi-person"></i>
              <input
                v-model="username"
                type="text"
                placeholder="Tài khoản..."
                required
              />
            </div>
          </div>

          <div class="input-modern mb-3">
            <label>Mật khẩu *</label>
            <div class="input-wrapper">
              <i class="bi bi-shield-lock"></i>
              <input
                v-model="password"
                type="password"
                placeholder="••••••••"
                required
              />
            </div>
          </div>

          <div
            class="d-flex justify-content-between align-items-center mb-4 px-1 small"
          >
            <div class="checkbox-wrapper">
              <input type="checkbox" id="remember" v-model="rememberMe" />
              <label for="remember" class="text-muted ms-2 cursor-pointer"
                >Ghi nhớ đăng nhập</label
              >
            </div>
            <a href="#" class="forgot-link">Quên mật khẩu?</a>
          </div>

          <button
            class="btn-submit w-100 mb-4"
            type="submit"
            :disabled="isLoading"
          >
            <span v-if="!isLoading">Đăng nhập ngay</span>
            <div
              v-else
              class="spinner-border spinner-border-sm text-light"
            ></div>
          </button>
        </form>

        <div v-if="role === 'customer'">
          <p class="text-center text-muted small">
            Chưa có tài khoản?
            <span class="action-link" @click="goToRegister">Đăng ký ngay</span>
          </p>
        </div>

        <div v-else class="text-center">
          <p class="text-muted small">
            Vui lòng tuân thủ quy định bảo mật dành cho quản trị viên
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap");

/* Tìm class .login-card và sửa max-width lên 550px hoặc 600px */
.login-card {
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(25px);
  width: 100%;
  max-width: 580px; /* Tăng từ 480px lên 580px để form rộng hơn */
  padding: 60px; /* Tăng padding từ 45px lên 60px để thoáng hơn */
  border-radius: 32px;
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.12);
  z-index: 1;
}

/* Tăng kích thước các ô input để cân xứng với form to */
.input-wrapper input {
  width: 100%;
  padding: 16px 15px 16px 50px; /* Tăng độ cao của input */
  border: 1.5px solid #eee;
  border-radius: 16px; /* Bo góc tròn hơn một chút */
  transition: 0.3s;
  font-size: 16px; /* Chữ to hơn cho dễ đọc */
}

/* Căn chỉnh lại icon trong input cho khớp với độ cao mới */
.input-wrapper i {
  position: absolute;
  left: 20px; /* Đẩy icon vào trong thêm chút */
  top: 50%;
  transform: translateY(-50%);
  color: #8b1e1e;
  font-size: 20px; /* Icon to hơn */
}

/* Nút bấm cũng cần to và cao hơn */
.btn-submit {
  background: #8b1e1e;
  color: white;
  border: none;
  padding: 18px; /* Tăng từ 14px lên 18px */
  border-radius: 16px;
  font-weight: 700;
  font-size: 16px;
  transition: 0.3s;
}

/* Nút Google cũng vậy */
.btn-google {
  background: white;
  border: 1.5px solid #eee;
  padding: 14px; /* Tăng độ cao */
  border-radius: 16px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.2s;
}

.login-wrapper {
  font-family: "Plus Jakarta Sans", sans-serif;
  min-height: 100vh;
  background: #fdfdfd url("@/assets/images/bannerdangnhap.jpg") center/cover
    no-repeat;
  position: relative;
  overflow: hidden;
}

/* Hiệu ứng trang trí nền */
.bg-decoration .circle {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.45;
}
.circle-1 {
  width: 450px;
  height: 450px;
  background: #8b1e1e;
  top: -100px;
  right: -50px;
}
.circle-2 {
  width: 350px;
  height: 350px;
  background: #c15656;
  bottom: -100px;
  left: -50px;
}

.text-dark-gradient {
  background: linear-gradient(135deg, #1a1a1a 0%, #4a4a4a 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

/* Tab Selector */
.role-selector {
  display: flex;
  background: #f0f0f0;
  padding: 5px;
  border-radius: 16px;
  position: relative;
}

.role-selector button {
  flex: 1;
  border: none;
  background: none;
  padding: 12px;
  font-weight: 700;
  font-size: 14px;
  color: #777;
  z-index: 2;
  transition: 0.3s;
}

.role-selector button.active {
  color: #fff;
}

.glider {
  position: absolute;
  height: calc(100% - 10px);
  width: calc(50% - 5px);
  background: #8b1e1e;
  border-radius: 12px;
  transition: transform 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
}
.glider.admin {
  transform: translateX(100%);
}

/* Input field */
.input-modern label {
  font-weight: 700;
  font-size: 13px;
  margin-bottom: 6px;
  display: block;
  color: #333;
}

.input-wrapper {
  position: relative;
}
.input-wrapper i {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #8b1e1e;
}
.input-wrapper input:hover {
  border-color: #c8c8c8;
}
.input-wrapper input {
  width: 100%;
  padding: 12px 15px 12px 42px;
  border: 1.5px solid #eee;
  border-radius: 12px;
  transition: 0.3s;
}

.input-wrapper input:focus {
  outline: none;
  border-color: #8b1e1e;
  box-shadow: 0 0 0 4px rgba(139, 30, 30, 0.1);
}

.btn-submit {
  background: #8b1e1e;
  color: white;
  border: none;
  padding: 14px;
  border-radius: 12px;
  font-weight: 700;
  transition: 0.3s;
}

.btn-submit:hover:not(:disabled) {
  background: #a32424;
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(139, 30, 30, 0.2);
}

.btn-google {
  background: white;
  border: 1.5px solid #eee;
  padding: 10px;
  border-radius: 12px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.2s;
}
.btn-google:hover {
  background: #f7f7f7;
}

.divider {
  text-align: center;
  position: relative;
}
.divider::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background: #eee;
}
.divider span {
  background: transparent;
  padding: 0 15px;
  color: #999;
  font-size: 15px;
  position: relative;
}

.action-link,
.forgot-link {
  color: #8b1e1e;
  font-weight: 700;
  text-decoration: none;
  cursor: pointer;
}

.action-link:hover {
  text-decoration: underline;
}
.cursor-pointer {
  cursor: pointer;
}
.main-logo {
  width: 220px;
  display: block;
  margin: 0 auto;
}
</style>
