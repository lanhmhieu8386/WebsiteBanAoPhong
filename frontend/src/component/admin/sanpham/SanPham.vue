<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import ThemSanPham from "@/component/admin/sanpham/ThemSanPham.vue";

import iconEye from "@/assets/icons/eye.png";
import iconEdit from "@/assets/icons/edit.png";
import iconAdd from "@/assets/icons/add.png";
import iconSearch from "@/assets/icons/search.png";
import iconExcel from "@/assets/icons/excel.png";
import iconFirst from "@/assets/icons/first.png";
import iconPrev from "@/assets/icons/prev.png";
import iconNext from "@/assets/icons/next.png";
import iconLast from "@/assets/icons/last.png";

const showModal = ref(false);
const moFormThem = () => {
  showModal.value = true;
};

const dongForm = () => {
  showModal.value = false;
};

const router = useRouter();

const search = ref("");
const danhMuc = ref("");
const trangThai = ref("");

const pageSize = ref(10);
const currentPage = ref(1);

const danhSachSanPham = ref([]);

const fakeSanPham = [
  {
    id: 1,
    ten: "Áo thun nam",
    danhMuc: "Thời trang",
    trangThai: "Đang bán",
    hinh: "https://via.placeholder.com/40",
    chiTiet: [
      { size: "M", mau: "Đỏ", gia: 250000, soLuong: 20 },
      { size: "L", mau: "Đỏ", gia: 250000, soLuong: 30 },
    ],
  },
  {
    id: 2,
    ten: "áo nè",
    danhMuc: "nè nè",
    trangThai: "Đang bán",
    hinh: "https://via.placeholder.com/40",
    chiTiet: [
      { size: "40", mau: "Trắng", gia: 850000, soLuong: 10 },
      { size: "41", mau: "Đen", gia: 850000, soLuong: 20 },
    ],
  },
  {
    id: 3,
    ten: "Áo hoodie",
    danhMuc: "Thời trang",
    trangThai: "Hết hàng",
    hinh: "https://via.placeholder.com/40",
    chiTiet: [],
  },
];

const loadSanPham = () => {
  danhSachSanPham.value = fakeSanPham;
};

const totalItems = computed(() => danhSachSanPham.value.length);

const totalPages = computed(
  () => Math.ceil(totalItems.value / pageSize.value) || 1,
);

const danhSachHienThi = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  return danhSachSanPham.value.slice(start, start + pageSize.value);
});

const xemChiTiet = (sp) => {
  router.push(`/admin/san-pham/${sp.id}`);
};

const mauList = (sp) => {
  if (!sp.chiTiet) return [];

  const mau = sp.chiTiet.map((ct) => ct.mau);

  return [...new Set(mau)];
};

const sizeList = (sp) => {
  if (!sp.chiTiet) return [];

  const size = sp.chiTiet.map((ct) => ct.size);

  return [...new Set(size)];
};

const giaMin = (sp) => {
  if (!sp.chiTiet || sp.chiTiet.length === 0) return 0;
  return Math.min(...sp.chiTiet.map((ct) => ct.gia));
};

const tongSoLuong = (sp) => {
  if (!sp.chiTiet) return 0;
  return sp.chiTiet.reduce((sum, ct) => sum + ct.soLuong, 0);
};

onMounted(() => {
  loadSanPham();
});
</script>

<template>
  <div class="quan-ly-san-pham">
    <!-- HEADER -->

    <div class="box">
      <h2>Quản lý sản phẩm</h2>
      <p>Danh sách sản phẩm trong hệ thống</p>
    </div>

    <!-- FILTER -->

    <div class="filter-box">
      <div class="filter-item">
        <label>Tìm kiếm</label>
        <input v-model="search" placeholder="Tên sản phẩm..." />
      </div>

      <div class="filter-item">
        <label>Danh mục</label>
        <select v-model="danhMuc">
          <option value="">Tất cả</option>
          <option>Thời trang</option>
          <option>Giày dép</option>
        </select>
      </div>

      <div class="filter-item">
        <label>Trạng thái</label>
        <select v-model="trangThai">
          <option value="">Tất cả</option>
          <option>Đang bán</option>
          <option>Hết hàng</option>
        </select>
      </div>

      <div class="filter-search">
        <button class="btn-search">
          <img :src="iconSearch" />
          Tìm kiếm
        </button>
      </div>
    </div>

    <!-- TABLE -->

    <div class="box">
      <div class="box-header">
        <h3>Danh sách sản phẩm</h3>

        <div class="header-actions">
          <button class="icon-btn">
            <img :src="iconExcel" />
          </button>

          <button class="btn-add" @click="moFormThem">
            <img :src="iconAdd" />
            Thêm sản phẩm
          </button>
        </div>
      </div>

      <table class="lau-table">
        <thead>
          <tr>
            <th>STT</th>
            <th>Ảnh</th>
            <th>Tên sản phẩm</th>
            <th>Danh mục</th>
            <th>Màu</th>
            <th>Size</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(sp, index) in danhSachHienThi" :key="sp.id">
            <td>{{ index + 1 }}</td>

            <td>
              <img :src="sp.hinh" class="img-product" />
            </td>

            <td class="text-bold">{{ sp.ten }}</td>

            <td>{{ sp.danhMuc }}</td>
            <td>
              <span v-if="mauList(sp).length">
                <span v-for="(mau, index) in mauList(sp)" :key="index">
                  {{ mau }}
                  <span v-if="index < mauList(sp).length - 1">, </span>
                </span>
              </span>

              <span v-else>-</span>
            </td>

            <td>
              <span v-if="sizeList(sp).length">
                <span v-for="(size, index) in sizeList(sp)" :key="index">
                  {{ size }}
                  <span v-if="index < sizeList(sp).length - 1">, </span>
                </span>
              </span>

              <span v-else>-</span>
            </td>

            <td>
              {{ giaMin(sp) ? giaMin(sp).toLocaleString() + " đ" : "-" }}
            </td>

            <td>
              {{ tongSoLuong(sp) }}
            </td>

            <td>
              <span
                class="status-badge"
                :class="
                  sp.trangThai == 'Đang bán' ? 'status-active' : 'status-cancel'
                "
              >
                {{ sp.trangThai }}
              </span>
            </td>

            <td class="text-center">
              <div class="action-group">
                <button class="btn-action-icon" @click="xemChiTiet(sp)">
                  <img :src="iconEye" />
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- PAGINATION -->

      <div class="pagination-wrapper">
        <div class="page-size-box">
          <span>Hiển thị</span>

          <select v-model.number="pageSize" class="page-size-select">
            <option :value="5">5</option>
            <option :value="10">10</option>
            <option :value="20">20</option>
          </select>

          <span>bản ghi</span>
        </div>

        <ul class="pagination">
          <li class="page-item" @click="goFirst">
            <button class="page-link">
              <img :src="iconFirst" />
            </button>
          </li>

          <li class="page-item" @click="goPrev">
            <button class="page-link">
              <img :src="iconPrev" />
            </button>
          </li>

          <li class="page-item active">
            <button class="page-link">
              {{ currentPage }}
            </button>
          </li>

          <li class="page-item" @click="goNext">
            <button class="page-link">
              <img :src="iconNext" />
            </button>
          </li>

          <li class="page-item" @click="goLast">
            <button class="page-link">
              <img :src="iconLast" />
            </button>
          </li>
        </ul>

        <span>
          Hiển thị {{ danhSachHienThi.length }} / {{ totalItems }} sản
          phẩm</span
        >
      </div>
    </div>
  </div>
  <ThemSanPham v-if="showModal" @close="dongForm" />
</template>

<style scoped>
.quan-ly-san-pham {
  display: flex;
  flex-direction: column;
  gap: 16px;
  font-family: Arial;
}

.box {
  background: #fff;
  border-radius: 10px;
  padding: 18px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.05);
}

.box-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.icon-btn {
  border: none;
  background: #f4f6f8;
  border-radius: 8px;
  padding: 8px;
  cursor: pointer;
}

.icon-btn img {
  width: 18px;
}

.btn-add {
  display: flex;
  align-items: center;
  gap: 6px;
  background: #3b6cff;
  color: #fff;
  border: none;
  padding: 8px 14px;
  border-radius: 8px;
  cursor: pointer;
}

.btn-add img {
  width: 16px;
  filter: brightness(0) invert(1);
}

.filter-box {
  background: #fff;
  padding: 18px;
  border-radius: 10px;
  display: grid;
  grid-template-columns: 1.5fr 1fr 1fr auto;
  gap: 16px;
  align-items: end;
}

.filter-item {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.filter-item input,
.filter-item select {
  height: 36px;
  border-radius: 8px;
  border: 1px solid #f3b6b6;
  padding: 0 10px;
}

.filter-search {
  display: flex;
  align-items: flex-end;
}

.btn-search {
  height: 36px;
  padding: 0 16px;
  border: none;
  border-radius: 8px;
  background: #3b6cff;
  color: #fff;
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
}

.btn-search img {
  width: 16px;
  filter: brightness(0) invert(1);
}

.lau-table {
  width: 100%;
  border-collapse: collapse;
}

.lau-table th {
  background: #e9f2fb;
  padding: 12px;
  text-align: left;
}

.lau-table td {
  padding: 12px;
  border-bottom: 1px solid #eee;
}

.img-product {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 6px;
}

.status-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  color: #fff;
}

.status-active {
  background: #28a745;
}

.status-cancel {
  background: #ff4d4f;
}

.text-bold {
  font-weight: 600;
}

.text-center {
  text-align: center;
}

.btn-action-icon {
  border: none;
  background: #f4f6f8;
  cursor: pointer;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 32px;
  height: 32px;
  border-radius: 8px;
  margin: 0 3px;
  transition: 0.2s;
}

.btn-action-icon:hover {
  background: #e9f2fb;
}

.btn-action-icon img {
  width: 16px;
}
/* ===== PAGINATION WRAPPER ===== */
.pagination-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 16px;
  flex-wrap: wrap;
  gap: 10px;
}

/* ===== PAGE SIZE ===== */
.page-size-box {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
}

.page-size-select {
  height: 32px;
  padding: 0 10px;
  border-radius: 8px;
  border: 1px solid #ddd;
  background: #fff;
  cursor: pointer;
}

.page-size-select:focus {
  outline: none;
  border-color: #3b6cff;
}

/* ===== PAGINATION BUTTON ===== */
.pagination {
  background: #f8f4f5;
  border-radius: 10px;
  padding: 6px;
  display: flex;
  gap: 4px;
}

.page-item .page-link {
  min-width: 30px;
  height: 30px;
  border: none;
  background: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  cursor: pointer;
}

/* ===== ICON ===== */
.page-link img {
  width: 16px;
}

/* ===== ACTIVE PAGE ===== */
.page-item.active .page-link {
  background: #3b6cff;
  color: #fff;
  box-shadow: 0 4px 10px rgba(59, 108, 255, 0.35);
}

/* ===== HOVER ===== */
.page-item:not(.active) .page-link:hover {
  background: #f1f3f5;
}

.action-group {
  display: inline-flex;
  gap: 6px;
}

.lau-table td:last-child,
.lau-table th:last-child {
  text-align: center;
}
</style>
