<script setup>
import { ref, computed, onMounted } from "vue";
import {
  getAllSanPham,
  getSanPhamByDanhMuc,
  detailSanPham,
} from "@/api/sanpham/sanPhamApi";
import { getAllDanhMuc } from "@/api/danhmuc/danhMucApi";
import aoPhong1 from "@/assets/images/aophong1.jpg";
import aoPhong2 from "@/assets/images/aophong2.jpg";
import aoPhong3 from "@/assets/images/aophong3.jpg";
import aoPhong4 from "@/assets/images/aophong4.jpg";
import aoPhong5 from "@/assets/images/aophong5.jpg";
import aoPhong6 from "@/assets/images/aophong6.jpg";

/////////////////API //////////////
import { addGioHang } from "@/api/customer/gioHangApi";

///////////////// GIO HANG /////////////////
const handleAddToCart = async (item) => {
  try {
    await addGioHang({
      idSanPhamChiTiet: item.id,
      soLuong: 1,
    });

    let count = Number(localStorage.getItem("cartCount")) || 0;
    count += 1;
    localStorage.setItem("cartCount", count);
    window.dispatchEvent(new Event("update-cart"));
    alert("✅ Đã thêm vào giỏ");
  } catch (err) {
    console.error(err);
  }
};

const localImages = [
  aoPhong1,
  aoPhong2,
  aoPhong3,
  aoPhong4,
  aoPhong5,
  aoPhong6,
];
const getRandomImage = (index) => {
  return localImages[index % localImages.length];
};
const lstDanhMuc = ref([]);
const danhMucDangChon = ref(null);
const sanPhamList = ref([]);

// ================== LOAD ==================
const loadDanhMuc = async () => {
  try {
    const res = await getAllDanhMuc();
    lstDanhMuc.value = res.data;

    if (lstDanhMuc.value.length > 0) {
      danhMucDangChon.value = lstDanhMuc.value[0];
      loadSanPhamByDanhMuc(danhMucDangChon.value.id);
    }
  } catch (e) {
    console.error("Lỗi load danh mục", e);
  }
};

const loadSanPham = async () => {
  try {
    const res = await getAllSanPham();
    sanPhamList.value = res.data;
  } catch (e) {
    console.error("Lỗi load sản phẩm", e);
  }
};

const loadSanPhamByDanhMuc = async (id) => {
  try {
    const res = await getSanPhamByDanhMuc(id);
    sanPhamList.value = res.data;
    currentPage.value = 1;
  } catch (e) {
    console.error("Lỗi lọc sản phẩm", e);
  }
};

// ================== CHỌN DANH MỤC ==================
const chonDanhMuc = (dm) => {
  danhMucDangChon.value = dm;
  loadSanPhamByDanhMuc(dm.id);
};

// ================== MODAL ==================
const selectedProduct = ref(null);
const showModal = ref(false);

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
const currentPage = ref(1);
const itemsPerPage = 6;

const paginatedSanPham = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return sanPhamList.value.slice(start, start + itemsPerPage);
});

const totalPages = computed(() => {
  return Math.ceil(sanPhamList.value.length / itemsPerPage);
});

// ================== INIT ==================
onMounted(() => {
  loadDanhMuc();
});
</script>

<template>
  <main class="home-body pt-5">
    <header class="text-center mb-5 reveal">
      <h2 class="section-title">Bộ Sưu Tập Áo Phông</h2>
      <div class="luxury-line"></div>
    </header>

    <section class="py-5 container">
      <div class="row g-4">
        <!-- SIDEBAR -->
        <aside class="col-lg-3 reveal-left">
          <div
            class="sidebar-container p-2 shadow-sm border rounded-4 bg-white sticky-top"
            style="top: 100px"
          >
            <div class="sidebar-header text-center py-3 mb-2 border-bottom">
              <h5 class="section-title" style="font-size: 1.2rem">DANH MỤC</h5>
            </div>

            <div class="category-list">
              <div
                v-for="dm in lstDanhMuc"
                :key="dm.id"
                class="category-item-sidebar p-3 mb-1 rounded-3 fw-bold"
                :class="{ active: danhMucDangChon?.id === dm.id }"
                @click="chonDanhMuc(dm)"
              >
                {{ dm.tenDanhMuc }}
              </div>
            </div>
          </div>
        </aside>

        <!-- PRODUCTS -->
        <div class="col-lg-9">
          <div
            class="reveal-right d-flex justify-content-between align-items-center mb-4 pb-3 border-bottom"
          ></div>

          <div class="row g-4">
            <div
              class="col-6 col-md-4"
              v-for="(item, index) in paginatedSanPham"
              :key="item.id"
            >
              <div
                class="product-card p-3 border rounded-4 h-100 d-flex flex-column"
              >
                <img
                  :src="item.hinhAnh ? item.hinhAnh : getRandomImage(index)"
                  class="product-img hover-zoom"
                  :alt="item.tenSanPham"
                />
                <h5 class="product-title text-uppercase">
                  {{ item.tenSanPham }}
                </h5>

                <p class="product-price text-danger fw-bold mt-auto">
                  {{ item.giaMin.toLocaleString() }}đ
                </p>

                <button
                  class="btn btn-sm btn-add rounded-3"
                  @click="handleAddToCart(item)"
                >
                  MUA NGAY
                </button>
                <button
                  class="btn btn-sm btn-outline-secondary rounded-3 mt-2"
                  @click="goChiTiet(item.id)"
                >
                  Chi tiết
                </button>
              </div>
            </div>
          </div>

          <!-- PAGINATION -->
          <div class="d-flex justify-content-center mt-5">
            <ul class="pagination-custom d-flex gap-2 list-unstyled">
              <li>
                <button
                  class="page-link-custom"
                  @click="currentPage--"
                  :disabled="currentPage === 1"
                >
                  ‹
                </button>
              </li>

              <li
                v-for="page in totalPages"
                :key="page"
                :class="{ active: page === currentPage }"
              >
                <button class="page-link-custom" @click="currentPage = page">
                  {{ page }}
                </button>
              </li>

              <li>
                <button
                  class="page-link-custom"
                  @click="currentPage++"
                  :disabled="currentPage === totalPages"
                >
                  ›
                </button>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>
  </main>
  <div v-if="showModal" class="modal-overlay" @click.self="showModal = false">
    <div class="modal-box modern">
      <button class="btn-close" @click="showModal = false">×</button>
      <img :src="selectedProduct.hinhAnh || aoPhong1" class="modal-img" />
      <div v-if="selectedProduct">
        <h3 class="fw-bold text-danger">
          {{ selectedProduct.tenSanPham }}
        </h3>

        <p class="text-muted">
          {{ selectedProduct.moTa }}
        </p>

        <h4 class="text-danger">{{ selectedProduct.giaMin }}đ</h4>
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
/* giữ nguyên style cũ của ca ca */
.home-body {
  background: linear-gradient(180deg, #fff, #fff6f6);
}

.section-title {
  font-weight: 800;
  background: linear-gradient(45deg, #b80000, #ff3c3c);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.category-item-sidebar {
  cursor: pointer;
  transition: 0.3s;
}
.category-item-sidebar:hover {
  background: #fff5f5;
  color: #dc3545;
}
.category-item-sidebar.active {
  background: linear-gradient(45deg, #b80000, #ff3c3c);
  color: white;
}

.product-card {
  transition: 0.3s;
}
.product-card:hover {
  transform: translateY(-8px);
}

.btn-add {
  border: 1px solid #dc3545;
  color: #dc3545;
}
.btn-add:hover {
  background: #dc3545;
  color: white;
}

.pagination-custom {
  padding: 10px;
  background: white;
  border-radius: 50px;
}

.page-link-custom {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
</style>
