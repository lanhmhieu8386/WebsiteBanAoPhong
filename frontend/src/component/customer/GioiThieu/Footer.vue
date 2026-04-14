<template>
  <footer class="centered-luxury-footer">
    <div class="dust-particles"></div>

    <div class="container position-relative py-5">
      <div class="footer-hero text-center mb-5">
        <div class="logo-wrapper">
          <h1 class="main-brand">CHIWIWIS</h1>
          <div class="brand-line"></div>
        </div>
        <p class="brand-philosophy">THE NEW STANDARD OF LUXURY LIVING</p>
      </div>

      <nav class="footer-nav-center mb-5">
        <ul class="nav-list">
          <li><router-link to="/" class="nav-item">Trang chủ</router-link></li>
          <li>
            <router-link to="/gioi-thieu" class="nav-item"
              >Giới thiệu</router-link
            >
          </li>
          <li>
            <router-link to="/san-pham" class="nav-item">Sản phẩm</router-link>
          </li>
          <li>
            <router-link to="/mua-sam" class="nav-item">Mua sắm</router-link>
          </li>
          <li>
            <router-link to="/tin-tuc" class="nav-item">Tin tức</router-link>
          </li>
          <li>
            <router-link to="/tuyen-dung" class="nav-item"
              >Tuyển dụng</router-link
            >
          </li>
        </ul>
      </nav>

      <div class="footer-interaction text-center mb-5">
        <div class="social-cluster mb-4">
          <a href="#" class="social-circle">FACEBOOK</a>
          <a href="#" class="social-circle">INSTAGRAM</a>
          <a href="#" class="social-circle">TIKTOK</a>
        </div>

        <div class="contact-chips">
          <div class="chip">
            <span class="chip-label">CALL</span>
            <span class="chip-value">0332654198</span>
          </div>
          <div class="chip-separator"></div>
          <div class="chip">
            <span class="chip-label">EMAIL</span>
            <span class="chip-value">hieulm24@gmail.com</span>
          </div>
        </div>
      </div>

      <div class="footer-legal-grid text-center">
        <div class="legal-content">
          <p class="legal-text">
            Công ty TNHH 4 thành viên ChiWiWi • MST: 0108704192 cấp 25/01/2026
          </p>
          <p class="address-text">
            B10, Dự án tổ hợp công trình hỗn hợp số 53 Triều Khúc, Thanh Xuân,
            Hà Nội
          </p>
        </div>
        <div class="copyright-bar mt-4">
          <span class="copy-symbol">©</span> 2026 CHIWIWI'S GROUP. ALL RIGHTS
          RESERVED.
        </div>
      </div>
    </div>
  </footer>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Cinzel:wght@400;700&family=Inter:wght@200;400;600&display=swap");

.centered-luxury-footer {
  background: #ffffff;
  color: #000;
  padding: 80px 0 40px;
  font-family: "Inter", sans-serif;
  border-top: 1px solid #f0f0f0;
  position: relative;
  overflow: hidden;
}

/* Hiệu ứng hạt bụi sang chảnh */
.dust-particles {
  position: absolute;
  inset: 0;
  background-image: radial-gradient(#eee 1px, transparent 1px);
  background-size: 40px 40px;
  opacity: 0.3;
  pointer-events: none;
}

/* Brand Section */
.main-brand {
  font-family: "Cinzel", serif;
  font-size: 3rem;
  font-weight: 700;
  letter-spacing: 15px;
  margin-bottom: 10px;
  color: #1a1a1a;
}

.brand-line {
  width: 60px;
  height: 2px;
  background: #000;
  margin: 0 auto;
}

.brand-philosophy {
  font-size: 0.65rem;
  letter-spacing: 5px;
  color: #999;
  margin-top: 20px;
  font-weight: 600;
}

/* Nav Center */
.nav-list {
  list-style: none;
  padding: 0;
  display: flex;
  justify-content: center;
  gap: 40px;
  flex-wrap: wrap;
}

.nav-item {
  text-decoration: none;
  color: #666;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 2px;
  transition: 0.4s;
}

.nav-item:hover {
  color: #000;
  letter-spacing: 4px;
}

/* Social & Contact Clusters */
.social-cluster {
  display: flex;
  justify-content: center;
  gap: 30px;
}

.social-circle {
  text-decoration: none;
  color: #000;
  font-size: 0.65rem;
  font-weight: 700;
  letter-spacing: 1px;
  position: relative;
  padding: 5px 0;
}

.social-circle::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 1px;
  background: #000;
  transition: 0.3s;
}

.social-circle:hover::after {
  width: 100%;
}

.contact-chips {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
}

.chip {
  display: flex;
  flex-direction: column;
}

.chip-label {
  font-size: 0.55rem;
  color: #aaa;
  font-weight: 800;
  letter-spacing: 1px;
}

.chip-value {
  font-size: 0.9rem;
  font-weight: 400;
}

.chip-separator {
  width: 1px;
  height: 30px;
  background: #eee;
}

/* Legal Section */
.footer-legal-grid {
  border-top: 1px solid #f9f9f9;
  padding-top: 40px;
}

.legal-text,
.address-text {
  font-size: 0.75rem;
  color: #888;
  margin-bottom: 5px;
  font-weight: 300;
}

.copyright-bar {
  font-size: 0.7rem;
  font-weight: 700;
  color: #000;
  letter-spacing: 2px;
}

.copy-symbol {
  font-size: 1.2rem;
  vertical-align: middle;
}

/* Responsive */
@media (max-width: 768px) {
  .nav-list {
    gap: 20px;
  }
  .main-brand {
    font-size: 2rem;
    letter-spacing: 8px;
  }
  .contact-chips {
    flex-direction: column;
  }
  .chip-separator {
    display: none;
  }
}
</style>
