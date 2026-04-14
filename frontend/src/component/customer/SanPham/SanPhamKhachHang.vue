<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import {
  getAllSanPham,
  getSanPhamByDanhMuc,
  detailSanPham,
} from "@/api/sanpham/sanPhamApi";
import { getAllDanhMuc } from "@/api/danhmuc/danhMucApi";
import { addGioHang } from "@/api/customer/gioHangApi";

import aophong1 from "@/assets/images/aophong1.png";
import aophong2 from "@/assets/images/aophong2.png";
const fallbackImages = [aophong1, aophong2];

const getRandomImage = () => {
  const index = Math.floor(Math.random() * fallbackImages.length);
  return fallbackImages[index];
};
// ================== STATE ==================
const lstDanhMuc = ref([]);
const danhMucDangChon = ref(null);
const sanPhamList = ref([]);
const selectedProduct = ref(null);
const showModal = ref(false);
const currentPage = ref(1);
const itemsPerPage = 9; // Tăng lên 9 cho cân grid

// ================== GIO HANG ==================
const handleAddToCart = async (product) => {
  try {
    // Nếu có biến thể thì lấy id biến thể đầu tiên, nếu không lấy id sản phẩm
    const targetId = product.chiTietList?.[0]?.id || product.id;
    await addGioHang({
      idSanPhamChiTiet: targetId,
      soLuong: 1,
    });

    let count = Number(localStorage.getItem("cartCount")) || 0;
    count += 1;
    localStorage.setItem("cartCount", count);
    window.dispatchEvent(new Event("update-cart"));

    // Toast xịn thay cho alert
    alert("✅ Added to your collection");
  } catch (err) {
    console.error(err);
  }
};

// ================== LOAD DATA ==================
const loadDanhMuc = async () => {
  try {
    const res = await getAllDanhMuc();
    lstDanhMuc.value = res.data;
    if (lstDanhMuc.value.length > 0) {
      // Mặc định không chọn cái nào để hiện "All" hoặc chọn cái đầu tiên tùy mày
      // Ở đây tao để mặc định load tất cả
      loadSanPham();
    }
  } catch (e) {
    console.error("Lỗi load danh mục", e);
  }
};

const loadSanPham = async () => {
  try {
    const res = await getAllSanPham();
    console.log("DATA SAN PHAM:", res.data);
    sanPhamList.value = res.data;
    danhMucDangChon.value = null;
  } catch (e) {
    console.error("Lỗi load sản phẩm", e);
  }
};

const loadSanPhamByDanhMuc = async (dm) => {
  try {
    danhMucDangChon.value = dm;
    const res = await getSanPhamByDanhMuc(dm.id);
    sanPhamList.value = res.data;
    currentPage.value = 1;
  } catch (e) {
    console.error("Lỗi lọc sản phẩm", e);
  }
};

const goChiTiet = async (id) => {
  try {
    const res = await detailSanPham(id);
    selectedProduct.value = res.data;
    showModal.value = true;
  } catch (err) {
    console.error("Lỗi chi tiết", err);
  }
};

// ================== PAGINATION ==================
const paginatedSanPham = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return sanPhamList.value.slice(start, start + itemsPerPage);
});

const totalPages = computed(() =>
  Math.ceil(sanPhamList.value.length / itemsPerPage),
);

onMounted(() => {
  loadDanhMuc();
  // Khởi tạo animation reveal
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) entry.target.classList.add("active");
      });
    },
    { threshold: 0.1 },
  );
  document.querySelectorAll(".reveal").forEach((el) => observer.observe(el));
});
</script>

<template>
  <main class="shop-page">
    <section class="shop-header reveal">
      <div class="container text-center">
        <span class="eyebrow">Curated Collection</span>
        <h1 class="zen-title">SẢN PHẨM</h1>
        <div class="zen-line"></div>
      </div>
    </section>

    <section class="container py-5">
      <div class="row g-5">
        <aside class="col-lg-3">
          <div class="filter-sidebar sticky-top" style="top: 120px">
            <div class="filter-group">
              <h5 class="filter-label">Danh mục</h5>
              <ul class="filter-list">
                <li :class="{ active: !danhMucDangChon }" @click="loadSanPham">
                  Tất cả sản phẩm
                </li>
                <li
                  v-for="dm in lstDanhMuc"
                  :key="dm.id"
                  :class="{ active: danhMucDangChon?.id === dm.id }"
                  @click="loadSanPhamByDanhMuc(dm)"
                >
                  {{ dm.tenDanhMuc }}
                </li>
              </ul>
            </div>

            <div class="promo-box mt-5">
              <span class="small-text">Khuyến mại mới</span>
              <h6>Giảm tới 20%</h6>
              <p>For inner circle members only.</p>
            </div>
          </div>
        </aside>

        <div class="col-lg-9">
          <div
            class="grid-controls d-flex justify-content-between align-items-center mb-4"
          >
            <span class="results-count">{{ sanPhamList.length }} sản phẩm</span>
          </div>

          <div class="row g-4">
            <div
              class="col-6 col-md-4"
              v-for="item in paginatedSanPham"
              :key="item.id"
            >
              <div class="zen-product-card">
                <div class="product-visual">
                  <img
                    :src="item.hinhAnh ? item.hinhAnh : getRandomImage()"
                    class="product-img"
                    alt=""
                  />
                  <div class="product-actions">
                    <button class="action-btn" @click="goChiTiet(item.id)">
                      <i class="bi bi-eye"></i>
                    </button>
                    <button class="action-btn" @click="handleAddToCart(item)">
                      <i class="bi bi-bag"></i>
                    </button>
                  </div>
                </div>
                <div class="product-info">
                  <h3 class="product-name">{{ item.tenSanPham }}</h3>
                  <p class="product-price">
                    {{ item.giaMin.toLocaleString() }}đ
                  </p>
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
        </div>
      </div>
    </section>

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
            <img :src="selectedProduct.hinhAnh || aoPhong1" class="img-fluid" />
          </div>
          <div class="modal-details">
            <span class="modal-cat">{{ selectedProduct.tenDanhMuc }}</span>
            <h2 class="modal-title">{{ selectedProduct.tenSanPham }}</h2>
            <p class="modal-desc">{{ selectedProduct.moTa }}</p>
            <div class="modal-price">
              <p class="product-price">
                {{ item.giaMin ? item.giaMin.toLocaleString() : "0" }}đ
              </p>
            </div>

            <div class="variant-selector mt-4">
              <p class="small-label">Select Options</p>
              <div class="variant-scroll">
                <div
                  v-for="ct in selectedProduct.chiTietList"
                  :key="ct.id"
                  class="v-card-shop"
                  @click="handleAddToCart(selectedProduct)"
                >
                  <div class="v-info">
                    <span class="fw-bold"
                      >{{ ct.tenMauSac }} / {{ ct.tenKichCo }}</span
                    >
                    <span class="d-block small text-muted"
                      >Stock: {{ ct.soLuong }}</span
                    >
                  </div>
                  <span class="v-price">{{ ct.gia.toLocaleString() }}đ</span>
                </div>
              </div>
            </div>

            <button
              class="btn-zen-primary w-100 mt-4"
              @click="handleAddToCart(selectedProduct)"
            >
              Quick Purchase
            </button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Syncopate:wght@700&family=Plus+Jakarta+Sans:wght@200;400;500;600;700&display=swap");

.shop-page {
  font-family: "Plus Jakarta Sans", sans-serif;
  background: #fff;
  color: #1a1a1a;
  min-height: 100vh;
}

/* --- HEADER --- */
.shop-header {
  padding: 120px 0 60px;
  background: #fcfcfc;
}
.eyebrow {
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 5px;
  color: #aaa;
  display: block;
  margin-bottom: 15px;
}
.zen-title {
  font-family: "Syncopate", sans-serif;
  font-size: 2.5rem;
  text-transform: uppercase;
  letter-spacing: -1px;
}
.zen-line {
  width: 40px;
  height: 2px;
  background: #000;
  margin: 20px auto;
}

/* --- SIDEBAR --- */
.filter-label {
  font-size: 0.8rem;
  text-transform: uppercase;
  letter-spacing: 2px;
  font-weight: 700;
  margin-bottom: 25px;
}
.filter-list {
  list-style: none;
  padding: 0;
}
.filter-list li {
  padding: 12px 0;
  font-size: 0.9rem;
  color: #888;
  cursor: pointer;
  transition: 0.3s;
  border-bottom: 1px solid #f5f5f5;
}
.filter-list li:hover,
.filter-list li.active {
  color: #000;
  padding-left: 10px;
}
.filter-list li.active {
  font-weight: 700;
  border-bottom-color: #000;
}

.promo-box {
  background: #1a1a1a;
  color: #fff;
  padding: 30px;
  border-radius: 4px;
}
.promo-box h6 {
  font-family: "Syncopate", sans-serif;
  font-size: 1rem;
  margin: 10px 0;
}
.promo-box p {
  font-size: 0.75rem;
  opacity: 0.7;
  margin: 0;
}

/* --- PRODUCT CARD (LUXURY VERSION) --- */
.zen-product-card {
  position: relative;
  transition: 0.6s cubic-bezier(0.165, 0.84, 0.44, 1);
}
.product-visual {
  position: relative;
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
.zen-product-card:hover .product-img {
  transform: scale(1.08);
}

.product-actions {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%) translateY(20px);
  display: flex;
  gap: 10px;
  opacity: 0;
  transition: 0.4s;
}
.zen-product-card:hover .product-actions {
  opacity: 1;
  transform: translateX(-50%) translateY(0);
}
.action-btn {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  border: none;
  background: #fff;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.3s;
}
.action-btn:hover {
  background: #000;
  color: #fff;
}

.product-info {
  padding: 20px 0;
  text-align: left;
}
.product-name {
  font-size: 0.8rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: #555;
}
.product-price {
  font-size: 1rem;
  font-weight: 600;
  color: #000;
  margin-top: 5px;
}

/* --- PAGINATION --- */
.zen-pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}
.page-num {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.8rem;
  font-weight: 700;
  cursor: pointer;
  border: 1px solid #eee;
  transition: 0.3s;
}
.page-num.active {
  background: #000;
  color: #fff;
  border-color: #000;
}
.page-arrow {
  background: none;
  border: 1px solid #eee;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
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
  width: 1000px;
  max-width: 95%;
  background: #fff;
  padding: 40px;
  position: relative;
  box-shadow: 0 50px 150px rgba(0, 0, 0, 0.1);
}
.zen-close {
  position: absolute;
  top: 20px;
  right: 20px;
  background: none;
  border: none;
  font-size: 1.2rem;
}
.zen-modal-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}
.modal-title {
  font-family: "Syncopate", sans-serif;
  font-size: 1.8rem;
  margin: 15px 0;
  text-transform: uppercase;
}
.v-card-shop {
  border: 1px solid #eee;
  padding: 15px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: 0.3s;
  cursor: pointer;
  border-radius: 4px;
}
.v-card-shop:hover {
  border-color: #000;
  background: #f9f9f9;
}
.btn-zen-primary {
  background: #000;
  color: #fff;
  border: none;
  padding: 18px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 2px;
}

/* --- ANIMATION --- */
.reveal {
  opacity: 0;
  transform: translateY(30px);
  transition: 1s cubic-bezier(0.2, 0.6, 0.3, 1);
}
.reveal.active {
  opacity: 1;
  transform: translateY(0);
}

@media (max-width: 768px) {
  .zen-modal-grid {
    grid-template-columns: 1fr;
  }
  .shop-header {
    padding: 80px 0 40px;
  }
}
</style>
