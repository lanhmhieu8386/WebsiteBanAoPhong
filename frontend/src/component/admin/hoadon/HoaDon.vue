<script setup>
import { ref, computed, onMounted, watch } from "vue";
import HoaDonChiTiet from "@/component/admin/hoadon/HoaDonChiTiet.vue";

import {
  getDanhSachDonHang,
  getDanhSachHoaDon,
  getDetailHoaDon,
  capNhatTrangThaiDonHang,
  capNhatTrangThaiHoaDon,
} from "@/api/admin/hoadon/HoaDonApi";

import iconEye from "@/assets/icons/eye.png";
import iconExcel from "@/assets/icons/excel.png";
import iconCalendar from "@/assets/icons/calendar.png";
import iconSearch from "@/assets/icons/search.png";
import iconFirst from "@/assets/icons/first.png";
import iconPrev from "@/assets/icons/prev.png";
import iconNext from "@/assets/icons/next.png";
import iconLast from "@/assets/icons/last.png";

const isModalOpen = ref(false);
const isStatusModalOpen = ref(false);

const selectedOrder = ref(null);
const selectedStatusOrder = ref(null);

const activeTab = ref("don-hang");

const search = ref("");
const trangThai = ref("");
const tuNgay = ref("");
const denNgay = ref("");

const pageSize = ref(10);
const currentPage = ref(1);

const danhSachDonHang = ref([]);
const loading = ref(false);

const convertTrangThai = (status) => {
  switch (Number(status)) {
    case 1:
      return "Chờ xác nhận";
    case 2:
      return "Đã xác nhận";
    case 3:
      return "Đang giao";
    case 4:
      return "Hoàn thành";
    case 5:
      return "Hủy";
    default:
      return "Không xác định";
  }
};

const formatCurrency = (value) => {
  return Number(value || 0).toLocaleString("vi-VN") + " đ";
};

const mapHoaDon = (item) => {
  return {
    id: item.id,
    maHoaDon: item.maHoaDon,
    khachHang: item.tenKhachHang || "Khách lẻ",
    sdt: item.soDienThoai || "",
    nhanVien: item.tenNhanVien || "",
    diaChiGiaoHang: item.diaChiGiaoHang || "",
    ngayTaoRaw: item.ngayTao,
    ngayCapNhatRaw: item.ngayCapNhat,
    ngayGio: item.ngayTao ? new Date(item.ngayTao).toLocaleString("vi-VN") : "",
    tongTienRaw: item.tongTien || 0,
    tongTien: formatCurrency(item.tongTien),
    trangThaiCode: Number(item.trangThai),
    trangThai: convertTrangThai(item.trangThai),
    raw: item,
  };
};

const loadHoaDon = async () => {
  try {
    loading.value = true;

    let res;
    if (activeTab.value === "don-hang") {
      res = await getDanhSachDonHang();
    } else {
      res = await getDanhSachHoaDon();
    }

    const data = Array.isArray(res?.data) ? res.data : [];
    danhSachDonHang.value = data.map(mapHoaDon);
  } catch (error) {
    console.error("Lỗi load hóa đơn:", error);
    danhSachDonHang.value = [];
  } finally {
    loading.value = false;
  }
};

const danhSachSauLoc = computed(() => {
  let data = [...danhSachDonHang.value];

  if (search.value.trim()) {
    const keyword = search.value.trim().toLowerCase();
    data = data.filter(
      (item) =>
        item.khachHang?.toLowerCase().includes(keyword) ||
        item.sdt?.toLowerCase().includes(keyword) ||
        item.maHoaDon?.toLowerCase().includes(keyword),
    );
  }

  if (trangThai.value) {
    data = data.filter((item) => item.trangThai === trangThai.value);
  }

  if (tuNgay.value) {
    const fromDate = new Date(tuNgay.value);
    fromDate.setHours(0, 0, 0, 0);
    data = data.filter((item) => {
      if (!item.ngayTaoRaw) return false;
      return new Date(item.ngayTaoRaw) >= fromDate;
    });
  }

  if (denNgay.value) {
    const toDate = new Date(denNgay.value);
    toDate.setHours(23, 59, 59, 999);
    data = data.filter((item) => {
      if (!item.ngayTaoRaw) return false;
      return new Date(item.ngayTaoRaw) <= toDate;
    });
  }

  return data;
});

const totalItems = computed(() => danhSachSauLoc.value.length);

const totalPages = computed(
  () => Math.ceil(totalItems.value / pageSize.value) || 1,
);

const danhSachHienThi = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  return danhSachSauLoc.value.slice(start, start + pageSize.value);
});

const danhSachTrangThai = computed(() => {
  if (activeTab.value === "don-hang") {
    return ["Chờ xác nhận", "Đã xác nhận", "Đang giao"];
  }
  return ["Hoàn thành", "Hủy"];
});

const openOrderDetail = async (order) => {
  try {
    const res = await getDetailHoaDon(order.id);
    const detail = res?.data || order.raw || order;

    selectedOrder.value = {
      ...order,
      id: detail.id ?? order.id,
      maHoaDon: detail.maHoaDon ?? order.maHoaDon,
      khachHang: detail.tenKhachHang ?? order.khachHang,
      sdt: detail.soDienThoai ?? order.sdt,
      nhanVien: detail.tenNhanVien ?? order.nhanVien,
      diaChiGiaoHang: detail.diaChiGiaoHang ?? order.diaChiGiaoHang,
      ngayTaoRaw: detail.ngayTao ?? order.ngayTaoRaw,
      ngayCapNhatRaw: detail.ngayCapNhat ?? order.ngayCapNhatRaw,
      ngayGio:
        detail.ngayTao != null
          ? new Date(detail.ngayTao).toLocaleString("vi-VN")
          : order.ngayGio,
      tongTienRaw: detail.tongTien ?? order.tongTienRaw,
      tongTien: formatCurrency(detail.tongTien ?? order.tongTienRaw),
      trangThaiCode: Number(detail.trangThai ?? order.trangThaiCode),
      trangThai: convertTrangThai(detail.trangThai ?? order.trangThaiCode),
      raw: detail,
    };
    isModalOpen.value = true;
  } catch (error) {
    console.error("Lỗi lấy chi tiết hóa đơn:", error);
    selectedOrder.value = order;
    isModalOpen.value = true;
  }
};

const closeModal = () => {
  isModalOpen.value = false;
  selectedOrder.value = null;
};

const openStatusModal = (order) => {
  selectedStatusOrder.value = { ...order };
  isStatusModalOpen.value = true;
};

const closeStatusModal = () => {
  isStatusModalOpen.value = false;
  selectedStatusOrder.value = null;
};

const resetFilter = () => {
  currentPage.value = 1;
  trangThai.value = "";
  search.value = "";
  tuNgay.value = "";
  denNgay.value = "";
};

const changeTab = async (tab) => {
  activeTab.value = tab;
  resetFilter();
  await loadHoaDon();
};

const handleSearch = () => {
  currentPage.value = 1;
};

const goFirst = () => {
  currentPage.value = 1;
};

const goPrev = () => {
  if (currentPage.value > 1) currentPage.value--;
};

const goNext = () => {
  if (currentPage.value < totalPages.value) currentPage.value++;
};

const goLast = () => {
  currentPage.value = totalPages.value;
};

const getStatusClass = (status) => {
  if (status === "Hoàn thành") return "status-active";
  if (status === "Đang giao") return "status-shipping";
  if (status === "Hủy") return "status-cancel";
  if (status === "Đã xác nhận") return "status-confirm";
  return "status-pending";
};

const getNextStatusText = (order) => {
  const status = Number(order?.trangThaiCode);

  if (activeTab.value === "don-hang") {
    if (status === 1) return "Xác nhận";
    if (status === 2) return "Giao hàng";
    if (status === 3) return "Hoàn thành";
  }

  return "";
};

const getNextStatusValue = (order) => {
  const status = Number(order?.trangThaiCode);

  if (activeTab.value === "don-hang") {
    if (status === 1) return 2;
    if (status === 2) return 3;
    if (status === 3) return 4;
  }

  return null;
};

const coTheCapNhat = (order) => {
  return getNextStatusValue(order) !== null;
};

const getCurrentStep = (order) => {
  const status = Number(order?.trangThaiCode);

  if (status === 1) return 1;
  if (status === 2) return 2;
  if (status === 3) return 3;
  if (status === 4) return 4;
  return 0;
};

const capNhatTrangThaiTrongPopup = async () => {
  try {
    if (!selectedStatusOrder.value) return;

    const trangThaiMoi = getNextStatusValue(selectedStatusOrder.value);
    if (!trangThaiMoi) return;

    await capNhatTrangThaiDonHang(
      selectedStatusOrder.value.id,
      trangThaiMoi,
      "Admin",
    );

    closeStatusModal();
    await loadHoaDon();
  } catch (error) {
    console.error("Lỗi cập nhật trạng thái:", error);
  }
};

const huyDonTrongPopup = async () => {
  try {
    if (!selectedStatusOrder.value) return;

    if (activeTab.value === "don-hang") {
      await capNhatTrangThaiDonHang(selectedStatusOrder.value.id, 5, "Admin");
    } else if (
      activeTab.value === "hoa-don" &&
      Number(selectedStatusOrder.value.trangThaiCode) === 4
    ) {
      await capNhatTrangThaiHoaDon(selectedStatusOrder.value.id, 5, "Admin");
    } else {
      return;
    }

    closeStatusModal();
    await loadHoaDon();
  } catch (error) {
    console.error("Lỗi hủy đơn:", error);
  }
};

watch([pageSize, search, trangThai, tuNgay, denNgay], () => {
  currentPage.value = 1;
});

onMounted(async () => {
  await loadHoaDon();
});
</script>

<template>
  <div class="quan-ly-don-hang">
    <div class="box">
      <h2>Quản lý đơn hàng / hóa đơn</h2>
      <p>Danh sách đơn hàng và hóa đơn trong hệ thống</p>
    </div>

    <div class="box tab-box">
      <button
        class="tab-btn"
        :class="{ active: activeTab === 'don-hang' }"
        @click="changeTab('don-hang')"
      >
        Đơn hàng
      </button>

      <button
        class="tab-btn"
        :class="{ active: activeTab === 'hoa-don' }"
        @click="changeTab('hoa-don')"
      >
        Hóa đơn
      </button>
    </div>

    <div class="filter-box">
      <div class="filter-item">
        <label>Tìm kiếm</label>
        <input v-model="search" placeholder="Tên hoặc SĐT khách hàng..." />
      </div>

      <div class="filter-item">
        <label>Trạng thái</label>
        <select v-model="trangThai">
          <option value="">Tất cả</option>
          <option v-for="item in danhSachTrangThai" :key="item" :value="item">
            {{ item }}
          </option>
        </select>
      </div>

      <div class="filter-item">
        <label>Từ ngày</label>
        <div class="input-with-icon">
          <input v-model="tuNgay" type="date" />
          <img :src="iconCalendar" class="calendar-icon-blue" />
        </div>
      </div>

      <div class="filter-item">
        <label>Đến ngày</label>
        <div class="input-with-icon">
          <input v-model="denNgay" type="date" />
          <img :src="iconCalendar" class="calendar-icon-blue" />
        </div>
      </div>

      <div class="filter-search">
        <button class="btn-search" @click="handleSearch">
          <img :src="iconSearch" />
          Tìm kiếm
        </button>
      </div>
    </div>

    <div class="box">
      <div class="box-header">
        <h3>
          {{
            activeTab === "don-hang"
              ? "Danh sách đơn hàng"
              : "Danh sách hóa đơn"
          }}
        </h3>

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
          <tr v-if="loading">
            <td colspan="9" class="empty-text">Đang tải dữ liệu...</td>
          </tr>

          <tr v-else-if="danhSachHienThi.length === 0">
            <td colspan="9" class="empty-text">Không có dữ liệu</td>
          </tr>

          <tr v-for="(order, index) in danhSachHienThi" :key="order.id">
            <td>{{ (currentPage - 1) * pageSize + index + 1 }}</td>
            <td>{{ order.maHoaDon }}</td>
            <td class="text-bold">{{ order.khachHang }}</td>
            <td>{{ order.sdt }}</td>
            <td>{{ order.nhanVien }}</td>
            <td>{{ order.ngayGio }}</td>
            <td class="text-bold">{{ order.tongTien }}</td>

            <td>
              <button
                class="status-trigger"
                :class="['status-badge', getStatusClass(order.trangThai)]"
                @click="openStatusModal(order)"
              >
                {{ order.trangThai }}
              </button>
            </td>

            <td class="text-center">
              <div class="action-group">
                <button class="btn-action-icon" @click="openOrderDetail(order)">
                  <img :src="iconEye" class="img-table-icon" />
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <div v-if="isModalOpen" class="modal-overlay">
        <div class="modal-content">
          <div class="modal-header">
            <h3>
              Chi tiết {{ activeTab === "don-hang" ? "đơn hàng" : "hóa đơn" }}
              {{ selectedOrder?.maHoaDon }}
            </h3>

            <button class="close-btn" @click="closeModal">×</button>
          </div>

          <div v-if="selectedOrder" class="detail-summary">
            <div class="detail-row">
              <span class="detail-label">Mã hóa đơn:</span>
              <span class="detail-value">{{ selectedOrder.maHoaDon }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Khách hàng:</span>
              <span class="detail-value">{{ selectedOrder.khachHang }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">SĐT:</span>
              <span class="detail-value">{{ selectedOrder.sdt || "-" }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Nhân viên:</span>
              <span class="detail-value">{{
                selectedOrder.nhanVien || "-"
              }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Địa chỉ giao hàng:</span>
              <span class="detail-value">
                {{ selectedOrder.diaChiGiaoHang || "-" }}
              </span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Ngày tạo:</span>
              <span class="detail-value">{{
                selectedOrder.ngayGio || "-"
              }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Tổng tiền:</span>
              <span class="detail-value text-bold">
                {{ selectedOrder.tongTien }}
              </span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Trạng thái:</span>
              <span
                :class="[
                  'status-badge',
                  getStatusClass(selectedOrder.trangThai),
                ]"
              >
                {{ selectedOrder.trangThai }}
              </span>
            </div>
          </div>

          <HoaDonChiTiet v-if="selectedOrder" :hoaDonId="selectedOrder.id" />
        </div>
      </div>

      <div v-if="isStatusModalOpen" class="modal-overlay">
        <div class="status-modal-content">
          <div class="modal-header">
            <h3>Thay đổi trạng thái - {{ selectedStatusOrder?.maHoaDon }}</h3>
            <button class="close-btn" @click="closeStatusModal">×</button>
          </div>

          <div class="status-modal-body">
            <div class="status-current">
              <span class="status-current-label">Trạng thái hiện tại:</span>
              <span
                :class="[
                  'status-badge',
                  getStatusClass(selectedStatusOrder?.trangThai),
                ]"
              >
                {{ selectedStatusOrder?.trangThai }}
              </span>
            </div>

            <div
              class="status-flow-wrapper"
              v-if="Number(selectedStatusOrder?.trangThaiCode) !== 5"
            >
              <div class="status-flow">
                <div
                  class="status-step"
                  :class="{ active: getCurrentStep(selectedStatusOrder) >= 1 }"
                >
                  <div class="step-circle">1</div>
                  <div class="step-label">Chờ xác nhận</div>
                </div>

                <div class="status-line"></div>

                <div
                  class="status-step"
                  :class="{ active: getCurrentStep(selectedStatusOrder) >= 2 }"
                >
                  <div class="step-circle">2</div>
                  <div class="step-label">Đã xác nhận</div>
                </div>

                <div class="status-line"></div>

                <div
                  class="status-step"
                  :class="{ active: getCurrentStep(selectedStatusOrder) >= 3 }"
                >
                  <div class="step-circle">3</div>
                  <div class="step-label">Đang giao</div>
                </div>

                <div class="status-line"></div>

                <div
                  class="status-step"
                  :class="{ active: getCurrentStep(selectedStatusOrder) >= 4 }"
                >
                  <div class="step-circle">4</div>
                  <div class="step-label">Hoàn thành</div>
                </div>
              </div>
            </div>

            <div v-else class="cancel-only-box">
              <div class="cancel-icon">✕</div>
              <div class="cancel-text">Đơn này đã bị hủy</div>
            </div>

            <div class="status-action-group">
              <button
                v-if="
                  activeTab === 'don-hang' && coTheCapNhat(selectedStatusOrder)
                "
                class="btn-status-primary"
                @click="capNhatTrangThaiTrongPopup"
              >
                {{ getNextStatusText(selectedStatusOrder) }}
              </button>

              <button
                v-if="
                  (activeTab === 'don-hang' &&
                    Number(selectedStatusOrder?.trangThaiCode) !== 5) ||
                  (activeTab === 'hoa-don' &&
                    Number(selectedStatusOrder?.trangThaiCode) === 4)
                "
                class="btn-status-danger"
                @click="huyDonTrongPopup"
              >
                Hủy
              </button>
            </div>
          </div>
        </div>
      </div>

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
          Hiển thị {{ danhSachHienThi.length }} / {{ totalItems }}
          {{ activeTab === "don-hang" ? "đơn hàng" : "hóa đơn" }}
        </span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.quan-ly-don-hang {
  display: flex;
  flex-direction: column;
  gap: 16px;
  font-family: Arial, Helvetica, sans-serif;
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

.tab-box {
  display: flex;
  gap: 12px;
  padding: 12px 18px;
}

.tab-btn {
  border: none;
  background: #f1f3f5;
  color: #333;
  padding: 10px 18px;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.2s;
}

.tab-btn.active {
  background: #da1432;
  color: #fff;
}

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

.filter-search {
  display: flex;
  align-items: flex-end;
}

.btn-search {
  height: 36px;
  padding: 0 16px;
  border-radius: 8px;
  border: none;
  background: #da1432;
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

.empty-text {
  text-align: center;
  color: #888;
  font-style: italic;
}

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

.status-badge {
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 700;
  color: #fff;
  display: inline-block;
  border: none;
}

.status-trigger {
  cursor: pointer;
}

.status-active {
  background: #28a745;
}

.status-pending {
  background: #ffc107;
  color: #000;
}

.status-cancel {
  background: #ff4d4f;
}

.status-confirm {
  background: #17a2b8;
}

.status-shipping {
  background: #007bff;
}

.action-group {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 6px;
  flex-wrap: wrap;
}

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
  max-height: 90vh;
  overflow-y: auto;
}

.status-modal-content {
  width: 92%;
  max-width: 760px;
  background: #fff;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 18px 60px rgba(0, 0, 0, 0.25);
}

.modal-header {
  padding: 14px 20px;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
}

.close-btn {
  border: none;
  background: none;
  font-size: 24px;
  cursor: pointer;
}

.detail-summary {
  display: grid;
  grid-template-columns: repeat(2, minmax(240px, 1fr));
  gap: 12px 20px;
  padding: 18px 20px 8px;
  border-bottom: 1px solid #eee;
}

.detail-row {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.detail-label {
  font-size: 13px;
  color: #666;
  font-weight: 600;
}

.detail-value {
  font-size: 14px;
  color: #222;
}

.status-modal-body {
  padding: 24px;
}

.status-current {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 26px;
  flex-wrap: wrap;
}

.status-current-label {
  font-size: 14px;
  font-weight: 600;
  color: #444;
}

.status-flow-wrapper {
  background: linear-gradient(135deg, #f11919, #f123237b);
  border-radius: 24px;
  padding: 26px;
  margin-bottom: 22px;
}

.status-flow {
  background: #fff;
  border-radius: 20px;
  padding: 26px 18px 18px;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 8px;
}

.status-step {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 100px;
  position: relative;
}

.step-circle {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #d5d7f7;
  color: #fff;
  font-size: 14px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
}

.status-step.active .step-circle {
  background: #f62b2b;
}

.step-label {
  margin-top: 10px;
  font-size: 12px;
  text-align: center;
  color: #666;
  font-weight: 600;
  line-height: 1.4;
}

.status-step.active .step-label {
  color: #222;
}

.status-line {
  flex: 1;
  height: 3px;
  background: #d8daf4;
  margin-top: 14px;
  min-width: 30px;
}

.cancel-only-box {
  background: #fff5f5;
  border: 1px solid #ffd2d2;
  color: #d9363e;
  border-radius: 16px;
  padding: 24px;
  text-align: center;
  margin-bottom: 22px;
}

.cancel-icon {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 8px;
}

.cancel-text {
  font-size: 15px;
  font-weight: 600;
}

.status-action-group {
  display: flex;
  justify-content: center;
  gap: 12px;
  flex-wrap: wrap;
}

.btn-status-primary,
.btn-status-danger {
  min-width: 140px;
  border: none;
  padding: 12px 18px;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition: 0.2s;
}

.btn-status-primary {
  background: #3b6cff;
  color: #fff;
}

.btn-status-primary:hover {
  background: #2f5ae0;
}

.btn-status-danger {
  background: #ff4d4f;
  color: #fff;
}

.btn-status-danger:hover {
  opacity: 0.92;
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
  background: #da1432;
  color: #fff;
  box-shadow: 0 4px 10px rgba(59, 108, 255, 0.35);
}

.page-item:not(.active) .page-link:hover {
  background: #f1f3f5;
}

@media (max-width: 1200px) {
  .filter-box {
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 900px) {
  .status-flow {
    flex-wrap: wrap;
    justify-content: center;
  }

  .status-line {
    display: none;
  }
}

@media (max-width: 768px) {
  .filter-box {
    grid-template-columns: 1fr;
  }

  .detail-summary {
    grid-template-columns: 1fr;
  }

  .pagination-wrapper {
    flex-direction: column;
    align-items: flex-start;
  }

  .status-modal-body {
    padding: 16px;
  }

  .status-flow-wrapper {
    padding: 16px;
  }

  .status-flow {
    padding: 18px 10px 12px;
  }
}
</style>
