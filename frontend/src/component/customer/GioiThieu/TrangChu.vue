<script setup>
import { onMounted, ref, computed, onUnmounted } from "vue";
import { useRouter } from "vue-router";
import khuyenmai1 from "@/assets/images/khuyenmai1.png";
import khuyenmai2 from "@/assets/images/khuyenmai2.png";
import aophong1 from "@/assets/images/aophong1.png";
import aophong2 from "@/assets/images/aophong2.png";
import banner1 from "@/assets/images/banner4.png";
import banner2 from "@/assets/images/banner5.png";
import banner3 from "@/assets/images/banner1.jpg";
import banner4 from "@/assets/images/banner2.jpg";
import banner5 from "@/assets/images/banner3.jpg";

import { getAllDanhMuc } from "@/api/danhmuc/danhMucApi";
import { getAllSanPham } from "@/api/sanpham/sanPhamApi";
import { getSanPhamByDanhMuc } from "@/api/sanpham/sanPhamApi";
import { detailSanPham } from "@/api/sanpham/sanPhamApi";

const router = useRouter();
const promoImages = [khuyenmai1, khuyenmai2];
const banners = [banner1, banner2, banner3, banner4, banner5];
const features = [
  { icon: '<i class="bi bi-patch-check"></i>', text: "Premium Cotton" },
  { icon: '<i class="bi bi-moisture"></i>', text: "Ultra Breathable" },
  { icon: '<i class="bi bi-vector-pen"></i>', text: "Signature Design" },
  { icon: '<i class="bi bi-box-seam"></i>', text: "Priority Shipping" },
];

const danhMucList = ref([]);
const selectedDanhMuc = ref(null);
const sanPhamList = ref([]);
const currentPage = ref(1);
const pageSize = 6;
const selectedProduct = ref(null);
const showModal = ref(false);
const currentIndex = ref(0);
let interval = null;

onMounted(() => {
  interval = setInterval(() => {
    currentIndex.value = (currentIndex.value + 1) % banners.length;
  }, 1500);
  loadDanhMuc();
  loadSanPham();
  initAnimation();
});

onUnmounted(() => {
  clearInterval(interval);
});

const scrollNext = () => {
  window.scrollTo({
    top: window.innerHeight - 80,
    behavior: "smooth",
  });
};

const goSanpham = () => router.push("/san-pham");

const selectDanhMuc = (id) => {
  selectedDanhMuc.value = id;
  loadSanPhamByDanhMuc(id);
};

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
    selectedDanhMuc.value = null;
  } catch (err) {
    console.error("Lỗi lấy sản phẩm", err);
  }
};

const loadSanPhamByDanhMuc = async (id) => {
  try {
    const res = await getSanPhamByDanhMuc(id);
    sanPhamList.value = res.data;
    currentPage.value = 1;
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
    { threshold: 0.1 },
  );
  document
    .querySelectorAll(".reveal, .reveal-up")
    .forEach((el) => observer.observe(el));
};

const paginatedSanPham = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  return sanPhamList.value.slice(start, start + pageSize);
});

const totalPages = computed(() =>
  Math.ceil(sanPhamList.value.length / pageSize),
);
</script>

<template>
  <main class="home-body">
    <section class="hero-zen">
      <div class="hero-container">
        <img
          v-for="(banner, index) in banners"
          :key="index"
          :src="banner"
          class="hero-slide"
          :class="{ active: index === currentIndex }"
        />
        <div class="hero-overlay">
          <div class="hero-content reveal-up">
            <span class="eyebrow">Season 2026 Collection</span>
            <h1 class="hero-title">Elevate Your Presence</h1>
            <button class="btn-zen-primary" @click="goSanpham">
              KHÁM PHÁ NGAY
            </button>
          </div>
        </div>
        <div class="scroll-hint" @click="scrollNext">
          <span class="scroll-text">Explore</span>
          <div class="scroll-bar"></div>
        </div>
      </div>
    </section>

    <div class="section-divider">
      <div class="vertical-line"></div>
    </div>

    <section class="reveal py-5 container">
      <div class="section-header text-center mb-5">
        <h2 class="zen-title">Exclusive Offers</h2>
        <div class="zen-line"></div>
      </div>
      <div class="row g-4">
        <div class="col-md-6" v-for="(img, index) in promoImages" :key="index">
          <div class="zen-promo-card">
            <img :src="img" alt="Promo" class="promo-img-zen" />
            <div class="promo-badge">Limited</div>
          </div>
        </div>
      </div>
    </section>

    <section class="reveal py-5 luxury-bg">
      <div class="container">
        <div class="row g-4">
          <div
            class="col-6 col-md-3 reveal-up"
            v-for="(feat, index) in features"
            :key="index"
          >
            <div class="zen-feature-card">
              <div class="feat-icon" v-html="feat.icon"></div>
              <p class="feat-text">{{ feat.text }}</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="reveal py-5 container mt-5">
      <div class="d-flex flex-column align-items-center mb-5">
        <h2 class="zen-title">Product Heritage</h2>
        <div class="zen-category-tabs mt-4">
          <span
            class="tab-item"
            :class="{ active: selectedDanhMuc === null }"
            @click="loadSanPham"
            >All</span
          >
          <span
            class="tab-item"
            v-for="item in danhMucList"
            :key="item.id"
            :class="{ active: selectedDanhMuc === item.id }"
            @click="selectDanhMuc(item.id)"
          >
            {{ item.tenDanhMuc }}
          </span>
        </div>
      </div>

      <div class="row g-4 px-2">
        <div
          class="col-6 col-lg-4"
          v-for="item in paginatedSanPham"
          :key="item.id"
        >
          <div class="zen-product-card">
            <div class="product-visual">
              <img
                :src="item.image || aophong1"
                class="product-img"
                :alt="item.tenSanPham"
              />
              <div class="product-actions">
                <button class="action-btn" @click="goChiTiet(item.id)">
                  <i class="bi bi-eye"></i>
                </button>
              </div>
            </div>
            <div class="product-info">
              <h3 class="product-name">{{ item.tenSanPham }}</h3>
              <p class="product-price">{{ item.giaMin.toLocaleString() }}đ</p>
              <button class="btn-buy-now" @click="goChiTiet(item.id)">
                Shop Now
              </button>
            </div>
          </div>
        </div>
      </div>

      <div class="zen-pagination mt-5">
        <button
          class="page-arrow"
          :disabled="currentPage === 1"
          @click="currentPage--"
        >
          <i class="bi bi-chevron-left"></i>
        </button>
        <span
          v-for="page in totalPages"
          :key="page"
          class="page-num"
          :class="{ active: page === currentPage }"
          @click="currentPage = page"
        >
          {{ page }}
        </span>
        <button
          class="page-arrow"
          :disabled="currentPage === totalPages"
          @click="currentPage++"
        >
          <i class="bi bi-chevron-right"></i>
        </button>
      </div>
    </section>

    <section class="reveal py-5 zen-newsletter">
      <div class="container">
        <div class="glass-form mx-auto">
          <div class="form-header text-center mb-4">
            <h3>Tham gia Inner Circle</h3>
            <p>
              Nhận quyền truy cập sớm vào các đợt ra mắt và nội dung độc quyền.
            </p>
          </div>
          <div class="zen-grid">
            <input type="text" placeholder="Họ tên" class="zen-input" />
            <input type="tel" placeholder="Điện thoại" class="zen-input" />
            <input type="email" placeholder="Email " class="zen-input full" />
          </div>
          <button class="btn-submit-luxury mt-4">Mua ngay</button>
        </div>
      </div>
    </section>
  </main>

  <div
    v-if="showModal"
    class="zen-modal-overlay"
    @click.self="showModal = false"
  >
    <div class="zen-modal-box">
      <button class="zen-close" @click="showModal = false">
        <i class="bi bi-x-lg"></i>
      </button>
      <div v-if="selectedProduct" class="zen-modal-grid">
        <div class="modal-gallery">
          <img :src="selectedProduct.image || aoPhong1" class="img-fluid" />
        </div>
        <div class="modal-details">
          <span class="modal-cat">{{ selectedProduct.tenDanhMuc }}</span>
          <h2 class="modal-title">{{ selectedProduct.tenSanPham }}</h2>
          <p class="modal-desc">{{ selectedProduct.moTa }}</p>
          <div class="modal-price">
            {{ selectedProduct.giaMin.toLocaleString() }}đ
          </div>
          <div class="variant-selector mt-4">
            <p class="small-label">Available Options</p>
            <div class="variant-scroll">
              <div
                v-for="ct in selectedProduct.chiTietList"
                :key="ct.id"
                class="v-card"
              >
                <span>{{ ct.tenMauSac }} / {{ ct.tenKichCo }}</span>
                <span class="v-price">{{ ct.gia.toLocaleString() }}đ</span>
              </div>
            </div>
          </div>
          <button class="btn-zen-primary w-100 mt-4">Add To Collection</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Syncopate:wght@700&family=Plus+Jakarta+Sans:wght@200;400;500;600;700&display=swap");

.home-body {
  font-family: "Plus Jakarta Sans", sans-serif;
  background-color: #ffffff;
  color: #1a1a1a;
  overflow-x: hidden;
}

/* --- HERO ZEN --- */
.hero-zen {
  height: 85vh;
  background: #000;
  position: relative;
  overflow: hidden;
}
.hero-zen::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 150px;
  background: linear-gradient(to top, #fff, transparent);
  z-index: 15;
}
.hero-slide {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  opacity: 0;
  filter: brightness(0.7);
  transition:
    opacity 1.5s cubic-bezier(0.4, 0, 0.2, 1),
    transform 3s ease;
  transform: scale(1.1);
}
.hero-slide.active {
  opacity: 1;
  transform: scale(1);
}
.hero-overlay {
  position: absolute;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
}
.hero-title {
  font-family: "Syncopate", sans-serif;
  font-size: clamp(2.5rem, 6vw, 5.5rem);
  color: #fff;
  text-transform: uppercase;
  letter-spacing: -2px;
  line-height: 0.9;
  margin: 20px 0;
}
.eyebrow {
  font-size: 0.8rem;
  text-transform: uppercase;
  letter-spacing: 5px;
  color: rgba(255, 255, 255, 0.7);
}

/* --- SCROLL HINT --- */
.scroll-hint {
  position: absolute;
  bottom: 40px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
  cursor: pointer;
  z-index: 20;
}
.scroll-text {
  font-size: 0.65rem;
  text-transform: uppercase;
  letter-spacing: 4px;
  color: #fff;
  opacity: 0.8;
}
.scroll-bar {
  width: 1px;
  height: 50px;
  background: rgba(255, 255, 255, 0.2);
  position: relative;
  overflow: hidden;
}
.scroll-bar::after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #fff;
  animation: scroll-anim 2s infinite cubic-bezier(0.77, 0, 0.175, 1);
}
@keyframes scroll-anim {
  0% {
    transform: translateY(-100%);
  }
  50% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(100%);
  }
}

/* --- SECTION DIVIDER --- */
.section-divider {
  height: 120px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #fff;
  position: relative;
  z-index: 5;
}
.vertical-line {
  width: 1px;
  height: 60px;
  background: linear-gradient(to bottom, #000, transparent);
}

/* --- PRODUCT CARD --- */
.zen-product-card {
  position: relative;
  transition: all 0.6s cubic-bezier(0.165, 0.84, 0.44, 1);
}
.product-visual {
  border-radius: 4px;
  overflow: hidden;
  background: #f9f9f9;
  aspect-ratio: 3/4;
}
.product-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: 1.2s cubic-bezier(0.19, 1, 0.22, 1);
}
.zen-product-card:hover {
  transform: translateY(-8px);
}
.zen-product-card:hover .product-img {
  transform: scale(1.08);
}

.product-actions {
  position: absolute;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.03);
  opacity: 0;
  transition: 0.4s;
}
.zen-product-card:hover .product-actions {
  opacity: 1;
}
.action-btn {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: #fff;
  border: none;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  transform: translateY(20px);
  transition: 0.4s;
}
.zen-product-card:hover .action-btn {
  transform: translateY(0);
}

.product-info {
  padding: 15px 0;
}
.product-name {
  font-size: 0.75rem;
  font-weight: 700;
  text-transform: uppercase;
  color: #999;
  letter-spacing: 1px;
}
.product-price {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 5px 0 15px;
}
.btn-buy-now {
  background: none;
  border: 1px solid #eee;
  padding: 7px 20px;
  font-size: 0.65rem;
  font-weight: 700;
  text-transform: uppercase;
  transition: 0.3s;
}
.btn-buy-now:hover {
  background: #000;
  color: #fff;
  border-color: #000;
}

/* --- BUTTONS & TABS --- */
.btn-zen-primary {
  background: #1a1a1a;
  color: #fff;
  border: none;
  padding: 15px 45px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 2px;
  transition: 0.4s;
}
.btn-zen-primary:hover {
  background: #000;
  transform: translateY(-3px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.zen-category-tabs {
  display: flex;
  gap: 30px;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}
.tab-item {
  font-size: 0.75rem;
  font-weight: 700;
  color: #bbb;
  cursor: pointer;
  text-transform: uppercase;
  position: relative;
  padding: 10px 0;
}
.tab-item.active {
  color: #000;
}
.tab-item.active::after {
  content: "";
  position: absolute;
  bottom: -11px;
  left: 0;
  width: 100%;
  height: 2px;
  background: #000;
}

/* --- MODAL --- */
.zen-modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(255, 255, 255, 0.98);
  backdrop-filter: blur(10px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}
.zen-modal-box {
  width: 1100px;
  max-width: 95%;
  background: #fff;
  padding: 50px;
  position: relative;
  box-shadow: 0 50px 150px rgba(0, 0, 0, 0.1);
}
.zen-close {
  position: absolute;
  top: 20px;
  right: 20px;
  background: none;
  border: none;
  font-size: 1.5rem;
}
.zen-modal-grid {
  display: grid;
  grid-template-columns: 1.2fr 1fr;
  gap: 50px;
}
.modal-cat {
  font-size: 0.7rem;
  text-transform: uppercase;
  letter-spacing: 3px;
  color: #aaa;
}
.modal-title {
  font-family: "Syncopate", sans-serif;
  font-size: 2rem;
  margin: 10px 0;
}
.v-card {
  border: 1px solid #eee;
  padding: 15px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  transition: 0.3s;
  cursor: pointer;
}
.v-card:hover {
  background: #000;
  color: #fff;
}

/* --- NEWSLETTER --- */
.zen-newsletter {
  background: #f9f9f9;
}
.glass-form {
  background: #fff;
  padding: 80px;
  max-width: 800px;
  border: 1px solid #eee;
}
.zen-input {
  width: 100%;
  border: none;
  border-bottom: 1px solid #ddd;
  padding: 15px 0;
  outline: none;
  transition: 0.3s;
}
.zen-input:focus {
  border-color: #000;
}
.zen-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}
.zen-grid .full {
  grid-column: span 2;
}
.btn-submit-luxury {
  width: 100%;
  background: #000;
  color: #fff;
  border: none;
  padding: 20px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 3px;
  margin-top: 30px;
}

/* --- ANIMATIONS --- */
/* --- CẬP NHẬT PROMO CARD TO VÀ CĂN GIỮA --- */
.zen-promo-card {
  position: relative;
  width: 100%;
  max-width: 580px; /* Tăng kích thước tối đa của card */
  margin: 0 auto; /* Căn giữa card trong cột */
  overflow: hidden;
  border-radius: 8px; /* Bo nhẹ cho hiện đại */
  transition: transform 0.5s ease;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05); /* Thêm bóng đổ nhẹ cho có khối */
}

.promo-img-zen {
  width: 100%;
  height: auto;
  aspect-ratio: 16 / 9; /* Ép về tỷ lệ khung hình rộng cho chuyên nghiệp */
  object-fit: cover; /* Đảm bảo ảnh không bị méo */
  display: block;
  transition: transform 0.8s scale;
}

.zen-promo-card:hover .promo-img-zen {
  transform: scale(1.03); /* Hiệu ứng zoom nhẹ khi di chuột */
}

/* Badge Limited căn lại cho chuẩn */
.promo-badge {
  position: absolute;
  top: 15px;
  left: 15px;
  background: rgba(0, 0, 0, 0.8);
  color: #fff;
  padding: 5px 15px;
  font-size: 0.7rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 2px;
  z-index: 2;
}

/* --- CĂN GIỮA ICON FEATURES --- */
.zen-feature-card {
  text-align: center; /* Đảm bảo chữ và icon đều vào giữa */
  padding: 20px;
}

.feat-icon {
  font-size: 1.8rem;
  margin-bottom: 10px;
  color: #1a1a1a;
}
.reveal,
.reveal-up {
  opacity: 0;
  transform: translateY(40px);
  transition: 1s cubic-bezier(0.19, 1, 0.22, 1);
}
.reveal.active,
.reveal-up.active {
  opacity: 1;
  transform: translateY(0);
}

@media (max-width: 768px) {
  .zen-modal-grid,
  .zen-grid {
    grid-template-columns: 1fr;
  }
  .zen-grid .full {
    grid-column: span 1;
  }
  .glass-form {
    padding: 40px 20px;
  }
  .hero-zen {
    height: 70vh;
  }
}
</style>
