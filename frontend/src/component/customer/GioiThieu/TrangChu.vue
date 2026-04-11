<script setup>
import { onMounted, ref, computed } from "vue";
import khuyenmai1 from "@/assets/images/khuyenmai1.png";
import khuyenmai2 from "@/assets/images/khuyenmai2.jpg";
import aoPhong1 from "@/assets/images/aophong1.jpg";
import aoPhong2 from "@/assets/images/aophong2.jpg";
import aoPhong3 from "@/assets/images/aophong3.jpg";
import aoPhong4 from "@/assets/images/aophong4.jpg";
import aoPhong5 from "@/assets/images/aophong5.jpg";
import aoPhong6 from "@/assets/images/aophong6.jpg";

const promoImages = [khuyenmai1, khuyenmai2];
import { useRouter } from "vue-router";
import { getAllDanhMuc } from "@/api/danhmuc/danhMucApi";
import { getAllSanPham } from "@/api/sanpham/sanPhamApi";
import { getSanPhamByDanhMuc } from "@/api/sanpham/sanPhamApi";
import { detailSanPham } from "@/api/sanpham/sanPhamApi";
const router = useRouter();
const danhMucList = ref([]);
const selectedDanhMuc = ref(null);
const sanPhamList = ref([]);
const currentPage = ref(1);
const pageSize = 6;
const selectedProduct = ref(null);
const showModal = ref(false);

const goSanpham = () => {
  router.push("/san-pham");
};
const selectDanhMuc = (id) => {
  selectedDanhMuc.value = id;
  loadSanPhamByDanhMuc(id);
};

const scrollBooking = () => {
  const el = document.getElementById("booking-section");
  if (el) {
    el.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  }
};

const features = [
  { icon: '<i class="bi bi-shield-check"></i>', text: "Cotton 100%" },
  { icon: '<i class="bi bi-wind"></i>', text: "Thoáng mát" },
  { icon: '<i class="bi bi-stars"></i>', text: "Thiết kế hiện đại" },
  { icon: '<i class="bi bi-truck"></i>', text: "Giao hàng nhanh" },
];

const goChiTiet = async (id) => {
  try {
    const res = await detailSanPham(id);
    selectedProduct.value = res.data;
    showModal.value = true;
  } catch (err) {
    console.error("Lỗi load chi tiết", err);
  }
};

const loadSanPham = async () => {
  try {
    const res = await getAllSanPham();
    sanPhamList.value = res.data;
  } catch (err) {
    console.error("Lỗi lấy sản phẩm", err);
  }
};

const loadSanPhamByDanhMuc = async (id) => {
  try {
    const res = await getSanPhamByDanhMuc(id);
    sanPhamList.value = res.data;
    currentPage.value = 1; // reset về trang 1
  } catch (err) {
    console.error("Lỗi lọc sản phẩm", err);
  }
};
const loadDanhMuc = async () => {
  try {
    const res = await getAllDanhMuc();
    danhMucList.value = res.data;
  } catch (err) {
    console.error("Lỗi lấy danh mục", err);
  }
};
const initAnimation = () => {
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) entry.target.classList.add("active");
      });
    },
    { threshold: 0.15 },
  );

  document
    .querySelectorAll(
      ".reveal, .reveal-left, .reveal-right, .reveal-up, .reveal-down",
    )
    .forEach((el) => observer.observe(el));
};
onMounted(() => {
  loadDanhMuc();
  loadSanPham();
  initAnimation();
});

const paginatedSanPham = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  return sanPhamList.value.slice(start, start + pageSize);
});

const totalPages = computed(() => {
  return Math.ceil(sanPhamList.value.length / pageSize);
});
</script>

<template>
  <main class="home-body">
    <section class="hero-section">
      <img
        src="@/assets/images/banner.jpg"
        class="w-100 d-block hero-img"
        alt="Banner"
      />
    </section>

    <section class="reveal py-5 container">
      <h2 class="section-title text-center mb-5">ƯU ĐÃI HOT TRONG TUẦN</h2>
      <div class="row g-4">
        <div class="col-md-6" v-for="(img, index) in promoImages" :key="index">
          <div class="promo-item overflow-hidden rounded-4 shadow-sm">
            <img
              :src="img"
              alt="Promo"
              class="img-fluid hover-zoom promo-img"
            />
          </div>
        </div>
      </div>
    </section>

    <section class="reveal py-5 bg-texture">
      <div class="container text-center">
        <h3 class="fw-bold mb-5 text-danger-dark">
          ÁO PHÔNG CHẤT LƯỢNG CAO - FORM DÁNG CHUẨN UNISEX
        </h3>
        <div class="row g-4">
          <div
            class="col-6 col-md-3 reveal"
            :class="[
              index === 0
                ? 'reveal-left'
                : index === 1
                  ? 'reveal-up'
                  : index === 2
                    ? 'reveal-down'
                    : 'reveal-right',
            ]"
            v-for="(feat, index) in features"
            :key="index"
          >
            <div class="feature-card">
              <div class="icon-box mb-3" v-html="feat.icon"></div>
              <p class="small fw-bold text-secondary text-uppercase">
                {{ feat.text }}
              </p>
            </div>
          </div>
        </div>
        <div class="mt-4 d-flex justify-content-center gap-3">
          <button
            class="btn btn-danger-outline rounded-pill px-4 btn-modern"
            @click="goSanpham"
          >
            XEM SẢN PHẨM
          </button>

          <button class="floating-booking" @click="scrollBooking">
            <i class="bi bi-cart-check"></i>
            Mua sắm ngay
          </button>
        </div>
      </div>
    </section>

    <section class="reveal py-5 container">
      <div
        class="d-flex flex-wrap justify-content-between align-items-center mb-4 border-bottom pb-3"
      >
        <h2 class="section-title mb-0">DANH MỤC SẢN PHẨM</h2>
        <div class="menu-categories d-flex gap-3 overflow-auto mt-3 mt-md-0">
          <span
            class="category-item"
            v-for="item in danhMucList"
            :key="item.id"
            :class="{ active: selectedDanhMuc === item.id }"
            @click="selectDanhMuc(item.id)"
          >
            {{ item.tenDanhMuc }}
          </span>
        </div>
      </div>

      <div class="row g-4">
        <div
          class="col-6 col-lg-4"
          v-for="item in paginatedSanPham"
          :key="item.id"
        >
          <div class="product-card p-3 shadow-sm border rounded-4">
            <div class="product-img-box">
              <img
                :src="item.image || aoPhong1"
                class="product-img hover-zoom"
                :alt="item.tenSanPham"
              />
            </div>

            <h5 class="product-title text-uppercase">
              {{ item.tenSanPham }}
            </h5>

            <p class="product-price text-danger fw-bold">{{ item.giaMin }}đ</p>

            <!-- 👇 GIỮ NGUYÊN 2 NÚT -->
            <div class="d-flex gap-2">
              <button
                class="btn btn-outline-warning btn-sm flex-grow-1 fw-bold btn-add"
                @click="muaNgay(item)"
              >
                MUA NGAY
              </button>

              <button
                class="btn btn-light btn-sm text-muted btn-detail"
                @click="goChiTiet(item.id)"
              >
                Chi tiết
              </button>
            </div>
          </div>
        </div>
        <div class="d-flex justify-content-center mt-4 gap-2">
          <button
            class="btn btn-sm btn-outline-danger"
            :disabled="currentPage === 1"
            @click="currentPage--"
          >
            ←
          </button>

          <button
            v-for="page in totalPages"
            :key="page"
            class="btn btn-sm"
            :class="page === currentPage ? 'btn-danger' : 'btn-outline-danger'"
            @click="currentPage = page"
          >
            {{ page }}
          </button>

          <button
            class="btn btn-sm btn-outline-danger"
            :disabled="currentPage === totalPages"
            @click="currentPage++"
          >
            →
          </button>
        </div>
      </div>
    </section>

    <section class="reveal py-5 bg-light">
      <div class="container row align-items-center mx-auto">
        <div class="col-lg-7 position-relative">
          <div class="gallery-stack">
            <img
              src="https://via.placeholder.com/400x300"
              class="img-1 shadow-lg rounded-4"
              alt=""
            />
            <img
              src="https://via.placeholder.com/300x400"
              class="img-2 shadow-lg rounded-4"
              alt=""
            />
          </div>
        </div>
        <div class="col-lg-5 ps-lg-5">
          <h2 class="text-danger fw-bold">PHONG CÁCH THỜI TRANG ĐƯỜNG PHỐ</h2>
          <ul class="list-unstyled mt-4">
            <li class="mb-3">✨ Chất liệu Cotton mềm mịn, thấm hút mồ hôi</li>
            <li class="mb-3">✨ Công nghệ in hiện đại, không bong tróc</li>
            <li>✨ Đội ngũ tư vấn size tận tâm, chính xác</li>
          </ul>
        </div>
      </div>
    </section>

    <section class="reveal py-5 container text-center">
      <h2 class="section-title mb-5">ĐÁNH GIÁ TỪ KHÁCH HÀNG</h2>
      <div class="row g-4">
        <div class="col-md-4" v-for="i in [1, 2, 3]" :key="i">
          <div class="feedback-card p-4 rounded-4 shadow-sm">
            <img
              src="https://via.placeholder.com/300x200"
              class="rounded-3 mb-3 w-100 hover-zoom"
              alt=""
            />
            <p class="fst-italic small">
              "Áo mặc rất mát, form đẹp đúng như mô tả. Sẽ ủng hộ shop dài
              dài..."
            </p>
            <div class="mt-3">
              <h6 class="mb-0 fw-bold">KHÁCH HÀNG THỨ {{ i }}</h6>
              <span class="text-muted smaller">Verified Buyer</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="booking-section" class="reveal py-5 booking-parallax">
      <div class="container">
        <div class="booking-form shadow-lg mx-auto p-4 p-md-5">
          <h4 class="text-center fw-bold mb-4">ĐĂNG KÝ NHẬN TIN KHUYẾN MÃI</h4>
          <div class="booking-grid">
            <div class="form-group full-width">
              <label>Họ tên (*)</label>
              <input type="text" placeholder="Nhập họ tên" />
            </div>
            <div class="form-group">
              <label>Điện thoại (*)</label>
              <input type="tel" placeholder="Số điện thoại" />
            </div>
            <div class="form-group">
              <label>Email</label>
              <input type="email" placeholder="Email" />
            </div>
            <div class="form-group full-width">
              <label>Địa chỉ nhận quà</label>
              <input type="text" placeholder="Nhập địa chỉ" />
            </div>
          </div>
          <button class="btn btn-danger w-100 mt-4 py-3 rounded-pill fw-bold">
            GỬI THÔNG TIN NGAY
          </button>
        </div>
      </div>
    </section>
  </main>
  <!-- MODAL -->
  <div v-if="showModal" class="modal-overlay" @click.self="showModal = false">
    <div class="modal-box modern">
      <button class="btn-close" @click="showModal = false">×</button>

      <div v-if="selectedProduct" class="modal-content-grid">
        <!-- 👇 LEFT: IMAGE -->
        <div class="modal-left">
          <img :src="selectedProduct.image || aoPhong1" class="modal-img" />
        </div>

        <!-- 👇 RIGHT: INFO -->
        <div class="modal-right">
          <h3 class="fw-bold text-danger">
            {{ selectedProduct.tenSanPham }}
          </h3>

          <p class="text-muted small">
            {{ selectedProduct.moTa }}
          </p>

          <div class="meta">
            <span>Danh mục: {{ selectedProduct.tenDanhMuc }}</span>
            <span>Thương hiệu: {{ selectedProduct.tenThuongHieu }}</span>
            <span>Chất liệu: {{ selectedProduct.tenChatLieu }}</span>
          </div>

          <h4 class="price mt-3">{{ selectedProduct.giaMin }}đ</h4>

          <!-- 👇 BIẾN THỂ -->
          <div class="variant-list">
            <h6 class="fw-bold mb-2">Chọn biến thể</h6>

            <div
              v-for="ct in selectedProduct.chiTietList"
              :key="ct.id"
              class="variant-card"
            >
              <div>{{ ct.tenMauSac }} - {{ ct.tenKichCo }}</div>

              <div class="text-danger fw-bold">{{ ct.gia }}đ</div>

              <small class="text-muted"> Còn {{ ct.soLuongTon }} </small>
            </div>
          </div>

          <!-- 👇 BUTTON -->
          <button class="btn btn-danger w-100 mt-3 fw-bold">MUA NGAY</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-box.modern {
  width: 900px;
  max-width: 95%;
  border-radius: 20px;
  padding: 0;
  overflow: hidden;
}

/* GRID */
.modal-content-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
}

/* LEFT */
.modal-left {
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* RIGHT */
.modal-right {
  padding: 24px;
}

.meta {
  display: flex;
  flex-direction: column;
  gap: 4px;
  font-size: 0.85rem;
  color: #666;
}

.price {
  font-size: 1.6rem;
  font-weight: bold;
  color: #dc3545;
}

/* VARIANT */
.variant-list {
  margin-top: 16px;
  max-height: 200px;
  overflow-y: auto;
}

.variant-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border: 1px solid #eee;
  border-radius: 10px;
  margin-bottom: 8px;
  transition: 0.25s;
}

.variant-card:hover {
  background: #fff5f5;
  border-color: #dc3545;
  transform: scale(1.02);
}

/* MOBILE */
@media (max-width: 768px) {
  .modal-content-grid {
    grid-template-columns: 1fr;
  }

  .modal-box.modern {
    width: 95%;
  }
}
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-box {
  background: white;
  padding: 24px;
  border-radius: 16px;
  width: 500px;
  max-height: 80vh;
  overflow-y: auto;
  position: relative;
}

.btn-close {
  position: absolute;
  top: 10px;
  right: 14px;
  border: none;
  background: none;
  font-size: 22px;
  cursor: pointer;
}

.variant-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}
/* Giữ nguyên toàn bộ CSS cũ của mày vì nó đang rất đẹp */
/* Chỉ thay đổi màu sắc nếu mày muốn chuyển từ đỏ lẩu sang màu khác, nếu không thì cứ để đỏ vẫn rất nổi bật */
/* Tao đã giữ nguyên toàn bộ các class CSS từ đoạn code trước */
.product-img-box {
  width: 100%;
  height: 220px;
  overflow: hidden;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f8f8;
}

.product-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.promo-img {
  width: 100%;
  height: 260px;
  object-fit: cover;
  transition: transform 0.8s ease;
}
.icon-box {
  font-size: 38px;
  color: #dc3545;
  transition: 0.3s;
}

.feature-card:hover .icon-box {
  transform: scale(1.2) rotate(6deg);
  color: #ff7a45;
}

.home-body {
  background: linear-gradient(180deg, #fff, #fff6f6);
}

/* =========================
   HERO
========================= */

.hero-img {
  border-radius: 14px;
  box-shadow:
    0 20px 60px rgba(0, 0, 0, 0.18),
    0 4px 12px rgba(0, 0, 0, 0.1);
  transition:
    transform 0.9s ease,
    filter 0.6s;
}

.hero-img:hover {
  transform: scale(1.03);
  filter: brightness(1.05);
}

/* =========================
   TITLE
========================= */

.section-title {
  font-size: 2rem;
  font-weight: 800;
  letter-spacing: 1px;
  background: linear-gradient(45deg, #b80000, #ff3c3c);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

/* =========================
   PROMO CARD
========================= */

.promo-img {
  max-width: 100%;
  height: auto;
  object-fit: contain;
  display: block;
  margin: auto;
}

.promo-item::after {
  content: "";
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, transparent, rgba(0, 0, 0, 0.25));
  opacity: 0;
  transition: 0.5s;
}

.promo-item:hover::after {
  opacity: 1;
}

.promo-img {
  transition: transform 0.8s ease;
}

.promo-item:hover .promo-img {
  transform: scale(1.12);
}

/* =========================
   FEATURE CARD
========================= */

.feature-card {
  padding: 26px;
  border-radius: 20px;
  background: white;
  position: relative;
  transition: 0.4s;

  box-shadow:
    0 10px 25px rgba(0, 0, 0, 0.08),
    0 2px 8px rgba(0, 0, 0, 0.05);
}

.feature-card::before {
  content: "";
  position: absolute;
  inset: 0;
  border-radius: 20px;
  padding: 1px;
  background: linear-gradient(45deg, #ff4d4d, #ffc107);
  -webkit-mask:
    linear-gradient(#000 0 0) content-box,
    linear-gradient(#000 0 0);
  -webkit-mask-composite: xor;
  opacity: 0;
  transition: 0.4s;
}

.feature-card:hover {
  transform: translateY(-12px) scale(1.04);
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
}

.feature-card:hover::before {
  opacity: 1;
}

.icon-box {
  font-size: 32px;
}

/* =========================
   MENU TAB
========================= */

.category-item {
  font-weight: 700;
  color: #777;
  cursor: pointer;
  padding-bottom: 4px;
  transition: 0.25s;
}

.category-item:hover {
  color: #dc3545;
}

.category-item.active {
  color: #dc3545;
  border-bottom: 2px solid #dc3545;
}

/* =========================
   PRODUCT CARD
========================= */

.product-card {
  background: white;
  border-radius: 18px;
  overflow: hidden;

  transition: 0.35s;

  box-shadow:
    0 12px 30px rgba(0, 0, 0, 0.08),
    0 2px 10px rgba(0, 0, 0, 0.04);
}

.product-card:hover {
  transform: translateY(-12px) scale(1.02);

  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.18);
}

.product-title {
  font-size: 0.95rem;
  font-weight: 800;
  letter-spacing: 0.4px;
}

.product-price {
  font-size: 1rem;
}

/* image */

.hover-zoom {
  transition: transform 0.8s cubic-bezier(0.2, 0.6, 0.3, 1);
}

.product-card:hover .hover-zoom {
  transform: scale(1.12);
}

/* =========================
   BUTTON
========================= */

.btn-modern {
  font-weight: 700;
  transition: 0.35s;

  position: relative;
  overflow: hidden;
}
.btn-modern::before {
  content: "";
  position: absolute;
  top: 0;
  left: -120%;

  width: 120%;
  height: 100%;

  background: linear-gradient(
    120deg,
    transparent,
    rgba(255, 255, 255, 0.35),
    transparent
  );

  transform: skewX(-25deg);
  transition: 0.6s;
}

.btn-modern:hover::before {
  left: 120%;
}

.btn-danger-outline {
  border: 2px solid #dc3545;
  color: #dc3545;
}

.btn-warning-gold {
  background: linear-gradient(135deg, #ffc107, #ffdd55);
  border: none;
}

.btn-modern:hover {
  transform: translateY(-3px);

  box-shadow: 0 12px 25px rgba(0, 0, 0, 0.2);
}

/* add button */

.btn-add {
  transition: 0.3s;
}

.btn-add:hover {
  background: linear-gradient(135deg, #ffc107, #ffe07a);
  color: #000;
  border-color: #ffc107;
}

/* =========================
   GALLERY
========================= */

.gallery-stack {
  position: relative;
}

.img-1 {
  width: 65%;
  transition: 0.7s;
}

.img-2 {
  width: 45%;
  position: absolute;
  right: 0;
  bottom: -40px;
  transition: 0.7s;
}

.gallery-stack:hover .img-1 {
  transform: translateY(-10px) rotate(-2deg);
}

.gallery-stack:hover .img-2 {
  transform: translateY(10px) rotate(2deg);
}

/* =========================
   FEEDBACK CARD
========================= */

.feedback-card {
  background: white;
  transition: 0.35s;

  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.08);
}

.feedback-card:hover {
  transform: translateY(-10px);

  box-shadow: 0 30px 60px rgba(0, 0, 0, 0.18);
}

/* =========================
   BOOKING FORM
========================= */
.booking-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 18px;
}

.full-width {
  grid-column: span 2;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-group label {
  font-weight: 600;
  font-size: 0.9rem;
  color: #555;
}
.booking-form {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);

  border-radius: 22px;

  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.18);
}

.form-group input {
  border: 1px solid #eee;
  background: #fafafa;
  border-radius: 12px;
  padding: 12px;
  transition: 0.3s;
}

.form-group input:focus {
  border-color: #dc3545;
  outline: none;

  box-shadow: 0 0 0 3px rgba(220, 53, 69, 0.15);
}

/* =========================
   REVEAL ANIMATION
========================= */

.reveal {
  opacity: 0;
  transform: translateY(70px) scale(0.95);
  transition:
    transform 0.9s cubic-bezier(0.2, 0.6, 0.3, 1),
    opacity 0.8s ease;
}

.reveal.active {
  opacity: 1;
  transform: translateY(0) scale(1);
}

/* =========================
   MOBILE
========================= */
@media (max-width: 768px) {
  .booking-grid {
    grid-template-columns: 1fr;
  }

  .full-width {
    grid-column: span 1;
  }
}
@media (max-width: 768px) {
  .hero-img {
    border-radius: 10px;
  }

  .section-title {
    font-size: 1.6rem;
  }
}

/* reveal directions */

.reveal-left {
  opacity: 0;
  transform: translateX(-80px);
  transition: all 0.9s cubic-bezier(0.2, 0.6, 0.3, 1);
}

.reveal-right {
  opacity: 0;
  transform: translateX(80px);
  transition: all 0.9s cubic-bezier(0.2, 0.6, 0.3, 1);
}

.reveal-up {
  opacity: 0;
  transform: translateY(80px);
  transition: all 0.9s cubic-bezier(0.2, 0.6, 0.3, 1);
}

.reveal-down {
  opacity: 0;
  transform: translateY(-80px);
  transition: all 0.9s cubic-bezier(0.2, 0.6, 0.3, 1);
}

.reveal-left.active,
.reveal-right.active,
.reveal-up.active,
.reveal-down.active {
  opacity: 1;
  transform: translate(0);
}

/* =========================
FLOATING BOOKING BUTTON
========================= */

.floating-booking {
  display: flex;
  align-items: center;
  gap: 8px;

  padding: 14px 20px;

  border-radius: 40px;
  border: none;

  font-weight: 700;

  color: white;

  background: linear-gradient(135deg, #dc3545, #ff6a6a);

  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.25);

  transition: 0.3s;
  position: relative; /* thêm dòng này */
  overflow: hidden;
}

.floating-booking:hover {
  transform: translateY(-4px) scale(1.05);

  box-shadow: 0 18px 40px rgba(0, 0, 0, 0.35);
}

/* pulse animation */

.floating-booking::after {
  content: "";
  position: absolute;
  inset: 0;

  border-radius: 40px;

  background: rgba(220, 53, 69, 0.4);

  animation: pulseBooking 2s infinite;
  pointer-events: none;
}

@keyframes pulseBooking {
  0% {
    transform: scale(1);
    opacity: 0.6;
  }

  70% {
    transform: scale(1.5);
    opacity: 0;
  }

  100% {
    opacity: 0;
  }
}
/* ... (phần còn lại của CSS giữ nguyên) ... */
</style>
