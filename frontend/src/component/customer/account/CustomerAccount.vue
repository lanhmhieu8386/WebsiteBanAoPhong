<script setup>
import { ref, onMounted } from "vue";

// khai báo biến reactive để tránh lỗi "not defined on instance"
const name = ref("nguyễn văn a");
const activeTab = ref("info");

onMounted(() => {
  // đồng bộ tên từ localstorage nếu có
  const savedName = localStorage.getItem("name");
  if (savedName) {
    name.value = savedName;
  }
});

const openPasswordModal = () => {
  alert("tính năng đổi mật khẩu đang được xử lý!");
};
</script>

<template>
  <div class="profile-page-wrapper">
    <div class="container">
      <div class="main-glass-card">
        <aside class="sidebar-navigation">
          <div class="user-profile-section">
            <div class="avatar-container">
              <div class="avatar-ring"></div>
              <img
                src="https://api.dicebear.com/7.x/avataaars/svg?seed=Felix"
                alt="avatar"
                class="user-avatar"
              />
              <button class="mini-upload-btn">
                <i class="bi bi-camera"></i>
              </button>
            </div>
            <h3 class="user-name-display">{{ name }}</h3>
            <span class="badge-gold-gradient">Thành viên Gold</span>
          </div>

          <nav class="nav-menu">
            <button
              :class="['menu-btn', { active: activeTab === 'info' }]"
              @click="activeTab = 'info'"
            >
              <i class="bi bi-person-vcard"></i> Hồ sơ cá nhân
            </button>
            <button
              :class="['menu-btn', { active: activeTab === 'orders' }]"
              @click="activeTab = 'orders'"
            >
              <i class="bi bi-box-seam"></i> Lịch sử đơn hàng
            </button>
            <div class="nav-divider"></div>
            <button class="menu-btn security-btn" @click="openPasswordModal">
              <i class="bi bi-shield-lock"></i> Đổi mật khẩu
            </button>
          </nav>
        </aside>

        <main class="content-viewport">
          <transition name="page-slide" mode="out-in">
            <div v-if="activeTab === 'info'" key="info" class="tab-content">
              <header class="content-header">
                <h2>Thông tin cá nhân</h2>
                <p>Quản lý thông tin để bảo mật tài khoản tốt hơn</p>
              </header>

              <div class="info-bento-grid">
                <div class="input-card shadow-soft">
                  <label class="label-bold">Họ và tên</label>
                  <input
                    type="text"
                    v-model="name"
                    class="modern-input"
                    placeholder="nhập họ tên..."
                  />
                </div>

                <div class="input-card shadow-soft">
                  <label class="label-bold">Giới tính</label>
                  <div class="gender-selection">
                    <label class="gender-option">
                      <input type="radio" name="gender" value="male" checked />
                      <span class="gender-label">Nam</span>
                    </label>
                    <label class="gender-option">
                      <input type="radio" name="gender" value="female" />
                      <span class="gender-label">Nữ</span>
                    </label>
                    <label class="gender-option">
                      <input type="radio" name="gender" value="other" />
                      <span class="gender-label">Khác</span>
                    </label>
                  </div>
                </div>

                <div class="input-card shadow-soft">
                  <label class="label-bold">Số điện thoại</label>
                  <input type="tel" value="033 265 4198" class="modern-input" />
                </div>

                <div class="input-card shadow-soft">
                  <label class="label-bold">Email</label>
                  <input
                    type="email"
                    value="khachhang@foursquare.com"
                    class="modern-input"
                  />
                </div>

                <div class="input-card shadow-soft">
                  <label class="label-bold">Ngày sinh</label>
                  <input type="date" value="1995-10-20" class="modern-input" />
                </div>

                <div class="input-card full-width shadow-soft">
                  <label class="label-bold">Địa chỉ</label>
                  <input
                    type="text"
                    value="123 đường láng, đống đa, hà nội"
                    class="modern-input"
                  />
                </div>
              </div>

              <div class="action-footer">
                <span class="save-action">
                  <i class="bi bi-check2-circle"></i>
                  Lưu thay đổi
                </span>
              </div>
            </div>
          </transition>
        </main>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* layout tổng thể */
.action-footer {
  margin-top: 30px;
  display: flex;
  justify-content: flex-end;
}

.save-action {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  color: #64748b;
  cursor: pointer;
  transition: 0.25s;
  font-size: 14px;
}

.save-action i {
  font-size: 18px;
}

.save-action:hover {
  color: #dc3545;
  transform: translateX(4px);
}

/* nâng cấp style giới tính */
.gender-selection {
  display: flex;
  gap: 10px;
  margin-top: 5px;
}

.gender-option {
  cursor: pointer;
  flex: 1;
}

.gender-option input {
  display: none;
}

.gender-label {
  display: block;
  text-align: center;
  padding: 10px;
  background: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 14px;
  font-size: 13px;
  font-weight: 600;
  transition: 0.25s;
  color: #64748b;
}

.gender-option:hover .gender-label {
  background: #f1f5f9;
}

.gender-option input:checked + .gender-label {
  background: #fff;
  border: 1px solid #dc3545;
  color: #dc3545;
  box-shadow: 0 4px 12px rgba(220, 53, 69, 0.1);
}

.gender-option input:checked + .gender-label {
  background: #2e976d;
  color: white;
  box-shadow: 0 4px 12px rgba(30, 41, 59, 0.2);
}

/* nâng cấp các nút bấm chính */
.btn-primary-2026 {
  background: linear-gradient(135deg, #1e293b 0%, #334155 100%);
  color: white;
  padding: 16px 45px;
  border-radius: 18px;
  border: none;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  margin-top: 30px;
  letter-spacing: 0.5px;
  position: relative;
  overflow: hidden;
}

.btn-primary-2026:hover {
  transform: translateY(-3px);
  box-shadow: 0 15px 30px rgba(30, 41, 59, 0.2);
  background: linear-gradient(135deg, #dc3545 0%, #b02a37 100%);
}

.btn-primary-2026:active {
  transform: translateY(0);
}

/* nút upload nhỏ ở avatar */
.mini-upload-btn {
  position: absolute;
  bottom: -2px;
  right: -2px;
  width: 36px;
  height: 36px;
  background: white;
  color: #1e293b;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
  transition: 0.3s;
}

.mini-upload-btn:hover {
  background: #1e293b;
  color: white;
  transform: rotate(90deg);
}

/* menu button bên trái */
.menu-btn {
  border: 1px solid transparent;
}

.menu-btn.active {
  background: white;
  color: #dc3545;
  box-shadow: 0 10px 25px rgba(220, 53, 69, 0.1);
  border: 1px solid rgba(220, 53, 69, 0.05);
}

.security-btn {
  background: rgba(239, 68, 68, 0.05);
}

.security-btn:hover {
  background: #ef4444 !important;
  color: white !important;
}

.profile-page-wrapper {
  min-height: 100vh;
  padding: 80px 0;
  background: #f8fafc;
  background-image:
    radial-gradient(at 0% 0%, rgba(220, 53, 69, 0.05) 0px, transparent 50%),
    radial-gradient(at 100% 100%, rgba(220, 53, 69, 0.05) 0px, transparent 50%);
  font-family: "inter", sans-serif;
  color: #1e293b;
}

.container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 0 20px;
}

/* khung kính mờ light mode */
.main-glass-card {
  display: grid;
  grid-template-columns: 300px 1fr;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(15px);
  border: 1px solid #ffffff;
  border-radius: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  min-height: 600px;
}

/* sidebar */
.sidebar-navigation {
  background: rgba(255, 255, 255, 0.4);
  padding: 40px 20px;
  border-right: 1px solid #f1f5f9;
}

.user-profile-section {
  text-align: center;
  margin-bottom: 40px;
}

.avatar-container {
  position: relative;
  width: 100px;
  height: 100px;
  margin: 0 auto 15px;
}

.user-avatar {
  width: 100%;
  height: 100%;
  border-radius: 30px;
  object-fit: cover;
  border: 4px solid white;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.05);
}

.mini-upload-btn {
  position: absolute;
  bottom: -5px;
  right: -5px;
  width: 32px;
  height: 32px;
  background: #1e293b;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-name-display {
  font-weight: 700;
  font-size: 1.1rem;
  margin-bottom: 5px;
}

.badge-gold-gradient {
  background: linear-gradient(90deg, #b8860b, #daa520);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-size: 11px;
  font-weight: 800;
  letter-spacing: 1px;
}

/* menu điều hướng */
.nav-menu {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.menu-btn {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 20px;
  border: none;
  background: transparent;
  border-radius: 18px;
  color: #64748b;
  font-weight: 500;
  transition: 0.25s;
  cursor: pointer;
  text-align: left;
}

.menu-btn.active {
  background: white;
  color: #dc3545;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.menu-btn:hover:not(.active) {
  background: rgba(255, 255, 255, 0.5);
  transform: translateX(5px);
}

.security-btn {
  color: #ef4444;
}

.nav-divider {
  height: 1px;
  background: #f1f5f9;
  margin: 10px 20px;
}

/* vùng nội dung chính */
.content-viewport {
  padding: 50px;
  background: white;
}

.content-header {
  margin-bottom: 35px;
}

.content-header h2 {
  font-size: 24px;
  font-weight: 800;
}

.content-header p {
  color: #94a3b8;
  font-size: 14px;
}

/* grid thông tin cá nhân */
.info-bento-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.input-card {
  background: #fbfcfd;
  padding: 15px 20px;
  border-radius: 20px;
  border: 1px solid #f1f5f9;
}

.full-width {
  grid-column: span 2;
}

.label-bold {
  font-weight: 900;
  font-size: 11px;
  color: #1a1a1a;
  margin-bottom: 5px;
  display: block;
}

.modern-input {
  width: 100%;
  border: none;
  background: transparent;
  padding: 8px 0;
  font-size: 15px;
  color: #334155;
  outline: none;
}

/* nút bấm chính */
.btn-primary-2026 {
  background: #1e293b;
  color: white;
  padding: 16px 40px;
  border-radius: 20px;
  border: none;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
  margin-top: 30px;
}

.btn-primary-2026:hover {
  background: #dc3545;
  box-shadow: 0 10px 20px rgba(220, 53, 69, 0.2);
}

/* đơn hàng */
.order-glass-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background: #fbfcfd;
  border: 1px solid #f1f5f9;
  border-radius: 24px;
  margin-bottom: 15px;
}

.order-id-tag {
  font-weight: 700;
  color: #1e293b;
}

.status-dot-active {
  background: #ecfdf5;
  color: #059669;
  padding: 4px 12px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: 600;
  margin-left: 10px;
}

.order-total {
  font-weight: 800;
  color: #dc3545;
  font-size: 18px;
}

.btn-icon-only {
  background: white;
  border: 1px solid #f1f5f9;
  width: 40px;
  height: 40px;
  border-radius: 12px;
  cursor: pointer;
}

/* hiệu ứng chuyển trang mượt */
.page-slide-enter-active,
.page-slide-leave-active {
  transition: all 0.3s ease;
}

.page-slide-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.page-slide-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
