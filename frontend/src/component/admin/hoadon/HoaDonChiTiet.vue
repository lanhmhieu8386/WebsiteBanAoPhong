<script setup>
import { ref, computed, onMounted, watch } from "vue";

import iconExcel from "@/assets/icons/excel.png";
import iconPrint from "@/assets/icons/nhapexx.png";
import {
  getDanhSachHoaDonChiTiet,
  getDetailHoaDonChiTiet,
} from "@/api/admin/hoadonchitiet/HoaDonChiTietApi";
import { getLichSuDonHangByHoaDonId } from "@/api/admin/lichsudonhang/LichSuDonHangApi";

const props = defineProps({
  hoaDonId: {
    type: Number,
    default: null,
  },
});

const activeTab = ref("hoaDonChiTiet");

// =========================
// HÓA ĐƠN CHI TIẾT
// =========================
const danhSachChiTiet = ref([]);
const loadingChiTiet = ref(false);
const selectedProduct = ref(null);
const showModal = ref(false);

const formatCurrency = (value) => {
  const number = Number(value || 0);
  return number.toLocaleString("vi-VN") + " đ";
};

const formatDateTime = (value) => {
  if (!value) return "---";
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) return value;

  return date.toLocaleString("vi-VN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  });
};

const tongTien = computed(() => {
  return danhSachChiTiet.value.reduce(
    (sum, item) => sum + Number(item.thanhTien || 0),
    0,
  );
});

const loadChiTiet = async () => {
  if (!props.hoaDonId) {
    danhSachChiTiet.value = [];
    return;
  }

  try {
    loadingChiTiet.value = true;
    const res = await getDanhSachHoaDonChiTiet(props.hoaDonId);

    danhSachChiTiet.value = (res.data || []).map((item) => ({
      ...item,
      thanhTien:
        item.thanhTien != null
          ? Number(item.thanhTien)
          : Number(item.gia || 0) * Number(item.soLuong || 0),
    }));
  } catch (error) {
    console.error("Lỗi load hóa đơn chi tiết:", error);
    danhSachChiTiet.value = [];
  } finally {
    loadingChiTiet.value = false;
  }
};

const exportExcel = () => {
  alert("Chưa làm chức năng xuất Excel");
};

const printInvoice = () => {
  window.print();
};

const viewProduct = async (item) => {
  try {
    const res = await getDetailHoaDonChiTiet(item.id);
    selectedProduct.value = res.data;
    showModal.value = true;
  } catch (error) {
    console.error("Lỗi lấy chi tiết sản phẩm trong hóa đơn:", error);
    alert("Không lấy được chi tiết sản phẩm");
  }
};

const closeModal = () => {
  showModal.value = false;
  selectedProduct.value = null;
};

// =========================
// LỊCH SỬ ĐƠN HÀNG
// =========================
const danhSachLichSu = ref([]);
const loadingLichSu = ref(false);

const getTrangThaiText = (idTrangThai) => {
  const mapTrangThai = {
    1: "Chờ xác nhận",
    2: "Đang chuẩn bị",
    3: "Đang giao",
    4: "Hoàn thành",
    5: "Đã hủy",
  };

  return mapTrangThai[idTrangThai] || `Trạng thái ${idTrangThai ?? "---"}`;
};

const getTrangThaiClass = (idTrangThai) => {
  switch (Number(idTrangThai)) {
    case 1:
      return "status-pending"; // Chờ xác nhận (vàng)
    case 2:
      return "status-confirm"; // Đang chuẩn bị (xanh cyan)
    case 3:
      return "status-shipping"; // Đang giao (xanh dương)
    case 4:
      return "status-active"; // Hoàn thành (xanh lá)
    case 5:
      return "status-cancel"; // Hủy (đỏ)
    default:
      return "status-pending";
  }
};

const loadLichSuDonHang = async () => {
  if (!props.hoaDonId) {
    danhSachLichSu.value = [];
    return;
  }

  try {
    loadingLichSu.value = true;
    const res = await getLichSuDonHangByHoaDonId(props.hoaDonId);
    danhSachLichSu.value = res.data || [];
  } catch (error) {
    console.error("Lỗi load lịch sử đơn hàng:", error);
    danhSachLichSu.value = [];
  } finally {
    loadingLichSu.value = false;
  }
};

const loadAllData = async () => {
  await Promise.all([loadChiTiet(), loadLichSuDonHang()]);
};

onMounted(() => {
  loadAllData();
});

watch(
  () => props.hoaDonId,
  () => {
    loadAllData();
  },
);
</script>

<template>
  <div class="box">
    <div class="box-header">
      <h3>Thông tin hóa đơn</h3>

      <div class="header-actions">
        <div class="tooltip-box">
          <button class="icon-btn" @click="exportExcel">
            <img :src="iconExcel" alt="excel" />
          </button>
          <span class="tooltip-text">Xuất Excel</span>
        </div>

        <div class="tooltip-box">
          <button class="icon-btn print" @click="printInvoice">
            <img :src="iconPrint" alt="print" />
          </button>
          <span class="tooltip-text">In hóa đơn</span>
        </div>
      </div>
    </div>

    <div class="tabs">
      <button
        class="tab-btn"
        :class="{ active: activeTab === 'hoaDonChiTiet' }"
        @click="activeTab = 'hoaDonChiTiet'"
      >
        Hóa đơn chi tiết
      </button>

      <button
        class="tab-btn"
        :class="{ active: activeTab === 'lichSuDonHang' }"
        @click="activeTab = 'lichSuDonHang'"
      >
        Lịch sử đơn hàng
      </button>
    </div>

    <div v-if="activeTab === 'hoaDonChiTiet'">
      <div class="section-title">Hóa đơn chi tiết</div>

      <div v-if="loadingChiTiet" class="empty-data">Đang tải dữ liệu...</div>

      <template v-else>
        <table class="lau-table">
          <thead>
            <tr>
              <th>STT</th>
              <th>Ảnh</th>
              <th>Sản phẩm</th>
              <th>Màu</th>
              <th>Size</th>
              <th>SL</th>
              <th>Giá</th>
              <th>Thành tiền</th>
              <th>Tác động</th>
            </tr>
          </thead>

          <tbody v-if="danhSachChiTiet.length > 0">
            <tr v-for="(item, index) in danhSachChiTiet" :key="item.id">
              <td>{{ index + 1 }}</td>

              <td>
                <img
                  v-if="item.urlAnh"
                  :src="item.urlAnh"
                  alt="Ảnh sản phẩm"
                  class="product-image"
                />
                <div v-else class="no-image">Không có ảnh</div>
              </td>

              <td>
                <div class="text-bold">{{ item.tenSanPham || "---" }}</div>
                <div class="sub-text">{{ item.maSanPhamChiTiet || "---" }}</div>
              </td>

              <td>{{ item.tenMauSac || "---" }}</td>
              <td>{{ item.tenKichCo || "---" }}</td>
              <td>{{ item.soLuong || 0 }}</td>
              <td>{{ formatCurrency(item.gia) }}</td>
              <td class="text-bold">{{ formatCurrency(item.thanhTien) }}</td>

              <td class="text-center">
                <div class="tooltip-box">
                  <button class="icon-btn view" @click="viewProduct(item)">
                    👁
                  </button>
                  <span class="tooltip-text">Xem chi tiết</span>
                </div>
              </td>
            </tr>
          </tbody>

          <tbody v-else>
            <tr>
              <td colspan="9" class="empty-data">Không có hóa đơn chi tiết</td>
            </tr>
          </tbody>
        </table>

        <div class="tong-tien">
          <span>Tổng tiền:</span>
          <strong>{{ formatCurrency(tongTien) }}</strong>
        </div>
      </template>
    </div>

    <div v-if="activeTab === 'lichSuDonHang'">
      <div class="section-title">Lịch sử đơn hàng</div>

      <div v-if="loadingLichSu" class="empty-data">
        Đang tải lịch sử đơn hàng...
      </div>

      <template v-else>
        <table class="lau-table">
          <thead>
            <tr>
              <th>STT</th>
              <th>Mã lịch sử</th>
              <th>ID hóa đơn</th>
              <th>ID đơn hàng</th>
              <th>Trạng thái</th>
              <th>Thời gian</th>
              <th>Ghi chú</th>
            </tr>
          </thead>

          <tbody v-if="danhSachLichSu.length > 0">
            <tr v-for="(item, index) in danhSachLichSu" :key="item.id">
              <td>{{ index + 1 }}</td>
              <td>{{ item.maLichSu || "---" }}</td>
              <td>{{ item.idHoaDon || "---" }}</td>
              <td>{{ item.idDonHang || "---" }}</td>
              <td>
                <span
                  class="status-badge"
                  :class="getTrangThaiClass(item.idTrangThai)"
                >
                  {{ getTrangThaiText(item.idTrangThai) }}
                </span>
              </td>
              <td>{{ formatDateTime(item.thoiGian) }}</td>
              <td>{{ item.ghiChu || "---" }}</td>
            </tr>
          </tbody>

          <tbody v-else>
            <tr>
              <td colspan="7" class="empty-data">Không có lịch sử đơn hàng</td>
            </tr>
          </tbody>
        </table>
      </template>
    </div>

    <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3>Chi tiết sản phẩm</h3>
          <button class="close-btn" @click="closeModal">✕</button>
        </div>

        <div v-if="selectedProduct" class="modal-body">
          <div class="modal-image-wrap">
            <img
              v-if="selectedProduct.urlAnh"
              :src="selectedProduct.urlAnh"
              alt="Ảnh sản phẩm"
              class="modal-image"
            />
            <div v-else class="no-image large">Không có ảnh</div>
          </div>

          <div class="modal-info">
            <div class="info-row">
              <span class="label">Mã hóa đơn chi tiết:</span>
              <span>{{ selectedProduct.maHoaDonChiTiet || "---" }}</span>
            </div>

            <div class="info-row">
              <span class="label">Mã hóa đơn:</span>
              <span>{{ selectedProduct.maHoaDon || "---" }}</span>
            </div>

            <div class="info-row">
              <span class="label">Mã SPCT:</span>
              <span>{{ selectedProduct.maSanPhamChiTiet || "---" }}</span>
            </div>

            <div class="info-row">
              <span class="label">Tên sản phẩm:</span>
              <span>{{ selectedProduct.tenSanPham || "---" }}</span>
            </div>

            <div class="info-row">
              <span class="label">Màu sắc:</span>
              <span>{{ selectedProduct.tenMauSac || "---" }}</span>
            </div>

            <div class="info-row">
              <span class="label">Kích cỡ:</span>
              <span>{{ selectedProduct.tenKichCo || "---" }}</span>
            </div>

            <div class="info-row">
              <span class="label">Số lượng:</span>
              <span>{{ selectedProduct.soLuong || 0 }}</span>
            </div>

            <div class="info-row">
              <span class="label">Giá:</span>
              <span>{{ formatCurrency(selectedProduct.gia) }}</span>
            </div>

            <div class="info-row">
              <span class="label">Thành tiền:</span>
              <span class="text-bold">
                {{ formatCurrency(selectedProduct.thanhTien) }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.box {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.04);
}

.box-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 14px;
}

.header-actions {
  display: flex;
  gap: 8px;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
  border-bottom: 1px solid #eaeaea;
  padding-bottom: 10px;
}

.tab-btn {
  border: none;
  background: #f3f6fb;
  color: #333;
  padding: 10px 16px;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.2s ease;
}

.tab-btn.active {
  background: #1e88e5;
  color: #fff;
}

.section-title {
  font-size: 28px;
  font-weight: 500;
  color: #0f2b46;
  margin-bottom: 16px;
}

.icon-btn {
  border: none;
  background: #f4f6f8;
  padding: 6px 12px;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon-btn img {
  width: 16px;
  height: 16px;
}

.icon-btn.print {
  background: #e8f0ff;
}

.icon-btn.view {
  background: #eef5ff;
  font-size: 14px;
  min-width: 38px;
}

.tooltip-box {
  position: relative;
  display: inline-block;
}

.tooltip-text {
  visibility: hidden;
  position: absolute;
  top: 110%;
  left: 50%;
  transform: translateX(-50%);
  background: #333;
  color: #fff;
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 6px;
  white-space: nowrap;
  z-index: 10;
}

.tooltip-box:hover .tooltip-text {
  visibility: visible;
}

.lau-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.lau-table th {
  background: #e9f2fb;
  padding: 12px 10px;
  text-align: left;
  font-weight: 600;
}

.lau-table td {
  padding: 12px 10px;
  border-bottom: 1px solid #eee;
  vertical-align: middle;
}

.text-bold {
  font-weight: 600;
}

.sub-text {
  color: #888;
  font-size: 12px;
  margin-top: 4px;
}

.product-image {
  width: 52px;
  height: 52px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #eee;
}

.no-image {
  width: 52px;
  height: 52px;
  border-radius: 8px;
  background: #f4f4f4;
  color: #777;
  font-size: 11px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 4px;
}

.no-image.large {
  width: 180px;
  height: 180px;
}

.empty-data {
  text-align: center;
  padding: 20px;
  color: #777;
}

.tong-tien {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  font-size: 16px;
}

.text-center {
  text-align: center;
}

.status-badge {
  display: inline-block;
  padding: 6px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 600;
  color: #fff;
}

.status-active {
  background: #28a745; /* Hoàn thành */
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

.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.35);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.modal-content {
  width: 720px;
  max-width: 95%;
  background: #fff;
  border-radius: 14px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.modal-header {
  padding: 14px 18px;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.close-btn {
  border: none;
  background: transparent;
  font-size: 18px;
  cursor: pointer;
}

.modal-body {
  padding: 18px;
  display: flex;
  gap: 20px;
}

.modal-image-wrap {
  flex: 0 0 200px;
}

.modal-image {
  width: 180px;
  height: 180px;
  object-fit: cover;
  border-radius: 10px;
  border: 1px solid #eee;
}

.modal-info {
  flex: 1;
}

.info-row {
  display: flex;
  gap: 10px;
  padding: 8px 0;
  border-bottom: 1px dashed #eee;
}

.label {
  min-width: 150px;
  font-weight: 600;
  color: #333;
}
</style>
