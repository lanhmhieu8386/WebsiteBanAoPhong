<script setup>
import { ref, computed, onMounted, watch } from "vue";
import HoaDonChiTiet from "@/component/admin/hoadon/HoaDonChiTiet.vue";

import iconEye from "@/assets/icons/eye.png";
import iconExcel from "@/assets/icons/excel.png";
import iconCalendar from "@/assets/icons/calendar.png";
import iconSearch from "@/assets/icons/search.png";
import iconFirst from "@/assets/icons/first.png";
import iconPrev from "@/assets/icons/prev.png";
import iconNext from "@/assets/icons/next.png";
import iconLast from "@/assets/icons/last.png";

const isModalOpen = ref(false);
const selectedOrder = ref(null);
const activeTab = ref("thong-tin");

const trangThai = ref("");

const pageSize = ref(10);
const currentPage = ref(1);

const danhSachDonHang = ref([]);

const fakeHoaDon = [
  {
    id: 1,
    maHoaDon: "HD001",
    tenKhachHang: "Nguyễn Văn A",
    soDienThoai: "0900000003",
    tenNhanVien: "Trần Văn B",
    ngayTao: "2026-03-15T10:30:00",
    tongTien: 650000,
    trangThai: 1,
  },
  {
    id: 2,
    maHoaDon: "HD002",
    tenKhachHang: "Nguyễn Văn A",
    soDienThoai: "0900000003",
    tenNhanVien: "Trần Văn B",
    ngayTao: "2026-03-16T11:10:00",
    tongTien: 350000,
    trangThai: 0,
  },
  {
    id: 3,
    maHoaDon: "HD003",
    tenKhachHang: "Nguyễn Văn A",
    soDienThoai: "0900000003",
    tenNhanVien: "Trần Văn B",
    ngayTao: "2026-03-17T12:20:00",
    tongTien: 500000,
    trangThai: 3,
  },
  {
    id: 4,
    maHoaDon: "HD004",
    tenKhachHang: "Nguyễn Văn A",
    soDienThoai: "0900000003",
    tenNhanVien: "Trần Văn B",
    ngayTao: "2026-03-18T14:10:00",
    tongTien: 420000,
    trangThai: 4,
  },
];
const convertTrangThai = (status) => {
  switch (status) {
    case 0:
      return "Chờ xác nhận";
    case 1:
      return "Đã xác nhận";
    case 2:
      return "Đang giao";
    case 3:
      return "Hoàn thành";
    case 4:
      return "Hủy";
    default:
      return "Không xác định";
  }
};

const loadHoaDon = () => {
  const res = { data: fakeHoaDon };

  danhSachDonHang.value = res.data.map((item) => ({
    id: item.id,
    maHoaDon: item.maHoaDon,
    khachHang: item.tenKhachHang,
    sdt: item.soDienThoai,
    nhanVien: item.tenNhanVien,
    ngayGio: new Date(item.ngayTao).toLocaleString("vi-VN"),
    tongTien: item.tongTien.toLocaleString("vi-VN") + " đ",
    trangThai: convertTrangThai(item.trangThai),
  }));
};

const totalItems = computed(() => danhSachDonHang.value.length);

const totalPages = computed(
  () => Math.ceil(totalItems.value / pageSize.value) || 1,
);

const danhSachHienThi = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  return danhSachDonHang.value.slice(start, start + pageSize.value);
});

const openOrderDetail = (order) => {
  selectedOrder.value = order;
  isModalOpen.value = true;
};
const closeModal = () => {
  isModalOpen.value = false;
  selectedOrder.value = null;
};

const goFirst = () => (currentPage.value = 1);
const goPrev = () => {
  if (currentPage.value > 1) currentPage.value--;
};

const goNext = () => {
  if (currentPage.value < totalPages.value) currentPage.value++;
};
const goLast = () => (currentPage.value = totalPages.value);

const getStatusClass = (status) => {
  if (status === "Hoàn thành") return "status-active";
  if (status === "Đang giao") return "status-shipping";
  if (status === "Hủy") return "status-cancel";
  if (status === "Đã xác nhận") return "status-confirm";
  return "status-pending";
};

watch(pageSize, () => {
  currentPage.value = 1;
});

onMounted(() => {
  loadHoaDon();
});
</script>

<template>
  <div class="quan-ly-don-hang">
    <div class="box">
      <h2>Quản lý hóa đơn</h2>
      <p>Danh sách hóa đơn trong hệ thống</p>
    </div>

    <!-- FILTER -->
    <div class="filter-box">
      <div class="filter-item">
        <label>Tìm kiếm</label>
        <input v-model="search" placeholder="Tên hoặc SĐT khách hàng..." />
      </div>

      <div class="filter-item">
        <label>Trạng thái</label>
        <select v-model="trangThai">
          <option value="">Tất cả</option>
          <option>Chưa hoàn thành</option>
          <option>Hoàn thành</option>
          <option>Hủy</option>
        </select>
      </div>

      <div class="filter-item">
        <label>Từ ngày</label>
        <div class="input-with-icon">
          <input type="date" />
          <img :src="iconCalendar" class="calendar-icon-blue" />
        </div>
      </div>

      <div class="filter-item">
        <label>Đến ngày</label>
        <div class="input-with-icon">
          <input type="date" />
          <img :src="iconCalendar" class="calendar-icon-blue" />
        </div>
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
        <h3>Danh sách hóa đơn</h3>

        <div class="header-actions">
          <button class="icon-btn">
            <img :src="iconExcel" />
          </button>
        </div>
      </div>

      <table class="lau-table">
        <thead>
          <tr>
            <th>STT</th>
            <th>Mã HĐ</th>
            <th>Khách hàng</th>
            <th>SĐT</th>
            <th>Nhân viên</th>
            <th>Ngày tạo</th>
            <th>Tổng tiền</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(order, index) in danhSachHienThi" :key="order.id">
            <td>{{ (currentPage - 1) * pageSize + index + 1 }}</td>

            <td>{{ order.maHoaDon }}</td>
            <td class="text-bold">{{ order.khachHang }}</td>
            <td>{{ order.sdt }}</td>
            <td>{{ order.nhanVien }}</td>
            <td>{{ order.ngayGio }}</td>
            <td class="text-bold">{{ order.tongTien }}</td>

            <td>
              <span :class="['status-badge', getStatusClass(order.trangThai)]">
                {{ order.trangThai }}
              </span>
            </td>

            <td class="text-center">
              <button class="btn-action-icon" @click="openOrderDetail(order)">
                <img :src="iconEye" class="img-table-icon" />
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="isModalOpen" class="modal-overlay">
        <div class="modal-content">
          <div class="modal-header">
            <h3>Chi tiết hóa đơn {{ selectedOrder.maHoaDon }}</h3>

            <button class="close-btn" @click="closeModal">×</button>
          </div>

          <HoaDonChiTiet v-if="selectedOrder" :hoaDonId="selectedOrder.id" />
        </div>
      </div>

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
</template>

<style scoped>
/* ===== LAYOUT ===== */
.quan-ly-don-hang {
  display: flex;
  flex-direction: column;
  gap: 16px;
  font-family: Arial, Helvetica, sans-serif;
}

/* ===== BOX ===== */
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

.box-header-flex {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: 700;
}

.header-actions {
  display: flex;
  gap: 10px;
}

/* ===== ICON BUTTON ===== */
.icon-btn {
  border: none;
  background: #f4f6f8;
  border-radius: 8px;
  padding: 8px;
  cursor: pointer;
  transition: 0.2s;
}

.icon-btn img {
  width: 18px;
}

.icon-btn:hover {
  background: #e9ecef;
}

.icon-btn.success {
  background: #e8f7ee;
}

/* ===== FILTER ===== */
.filter-box {
  background: #fff;
  padding: 18px;
  border-radius: 10px;
  display: grid;
  grid-template-columns: 1.4fr 1fr 1fr 1fr auto;
  gap: 16px;
  align-items: end;
}

.filter-item {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.filter-item label {
  font-size: 13px;
  font-weight: 600;
  color: #666;
}

.filter-item input,
.filter-item select {
  height: 36px;
  padding: 0 10px;
  border-radius: 8px;
  border: 1px solid #f3b6b6;
}

/* ===== INPUT ICON ===== */
.input-with-icon {
  position: relative;
}

.input-with-icon input {
  width: 100%;
  height: 36px;
  padding: 0 35px 0 10px;
  border-radius: 8px;
  border: 1px solid #f3b6b6;
}

.calendar-icon-blue {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  width: 16px;
  pointer-events: none;
}

/* ===== TABLE ===== */
.lau-table {
  width: 100%;
  border-collapse: collapse;
  background: #fff;
  margin-top: 8px;
  table-layout: fixed;
}

.lau-table th {
  background: #e9f2fb;
  padding: 12px;
  font-size: 14px;
  text-align: left;
}

.lau-table td {
  padding: 12px;
  border-bottom: 1px solid #eee;
  font-size: 14px;
}

.lau-table tr:hover {
  background: #fafafa;
}

.text-bold {
  font-weight: 600;
}

.text-center {
  text-align: center;
}

/* ===== ACTION ICON ===== */
.btn-action-icon {
  border: none;
  background: transparent;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

.img-table-icon {
  width: 20px;
}

/* ===== STATUS ===== */
.status-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  color: #fff;
}

.status-active {
  background: #28a745;
}

.status-pending {
  background: #ffc107;
  color: #000;
}

/* ===== PAGINATION ===== */
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

.page-link img {
  width: 16px;
}

.page-item.active .page-link {
  background: #3b6cff;
  color: #fff;
  box-shadow: 0 4px 10px rgba(59, 108, 255, 0.35);
}

.page-item:not(.active) .page-link:hover {
  background: #f1f3f5;
}

/* ===== MODAL ===== */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-content {
  width: 95%;
  max-width: 1200px;
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.25);
}

.modal-header {
  padding: 14px 20px;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.close-btn {
  border: none;
  background: none;
  font-size: 24px;
  cursor: pointer;
}

/* ===== TABS ===== */
.detail-tabs {
  display: flex;
  border-bottom: 1px solid #eee;
}

.tab-item {
  border: none;
  background: none;
  padding: 12px 20px;
  cursor: pointer;
  font-size: 13px;
}

.tab-item.active {
  font-weight: 600;
  border-bottom: 3px solid #ffb6b6;
}

/* ===== DETAIL GRID ===== */
.detail-grid {
  display: grid;
  grid-template-columns: 240px 1fr 220px;
  gap: 20px;
  padding: 20px;
}

.info-row {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px dashed #eee;
  font-size: 13px;
}

.border-none {
  border: none;
}

.status-pending-text {
  color: #ff4d4f;
}

/* ===== ACTION BUTTON ===== */
.action-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.btn-v {
  border: none;
  padding: 10px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
}

.btn-primary {
  background: #ffb6b6;
}

.btn-gray {
  background: #eee;
}

.btn-red {
  background: #ff4d4f;
  color: #fff;
}
.status-cancel {
  background: #ff4d4f;
}
.pagination-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 16px;
  flex-wrap: wrap;
  gap: 10px;
}
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

.status-confirm {
  background: #17a2b8;
}

.status-shipping {
  background: #007bff;
}

.filter-search {
  display: flex;
  align-items: flex-end;
}

.btn-search {
  height: 36px;
  padding: 0 16px;
  border-radius: 8px;
  border: none;
  background: #3b6cff;
  color: white;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
  transition: 0.2s;
}

.btn-search img {
  width: 16px;
  filter: brightness(0) invert(1);
}

.btn-search:hover {
  background: #2f5ae0;
}
</style>
