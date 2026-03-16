<script setup>
import { ref, computed, onMounted } from "vue";

import iconExcel from "@/assets/icons/excel.png";
import iconPrint from "@/assets/icons/nhapexx.png";

const props = defineProps({
  hoaDonId: Number,
});

const danhSachChiTiet = ref([]);

const fakeChiTiet = [
  {
    id: 1,
    sanPham: "Áo thun nam",
    danhMuc: "Thời trang",
    thuongHieu: "Nike",
    chatLieu: "Cotton",
    mau: "Đen",
    size: "L",
    soLuong: 2,
    gia: 250000,
    trangThai: "Còn hàng",
  },
  {
    id: 2,
    sanPham: "Áo hoodie",
    danhMuc: "Thời trang",
    thuongHieu: "Adidas",
    chatLieu: "Nỉ",
    mau: "Trắng",
    size: "M",
    soLuong: 1,
    gia: 450000,
    trangThai: "Còn hàng",
  },
];

const loadChiTiet = () => {
  danhSachChiTiet.value = fakeChiTiet.map((i) => ({
    ...i,
    thanhTien: i.soLuong * i.gia,
  }));
};

const tongTien = computed(() => {
  return danhSachChiTiet.value.reduce((sum, i) => sum + i.thanhTien, 0);
});

const exportExcel = () => {
  alert("Xuất Excel");
};

const printInvoice = () => {
  window.print();
};

const viewProduct = (item) => {
  alert("Xem chi tiết sản phẩm: " + item.sanPham);
};

onMounted(() => {
  loadChiTiet();
});
</script>

<template>
  <div class="box">
    <div class="box-header">
      <h3>Chi tiết hóa đơn</h3>

      <div class="header-actions">
        <!-- Xuất Excel -->
        <div class="tooltip-box">
          <button class="icon-btn" @click="exportExcel">
            <img :src="iconExcel" />
          </button>
          <span class="tooltip-text">Xuất Excel</span>
        </div>

        <!-- In hóa đơn -->
        <div class="tooltip-box">
          <button class="icon-btn print" @click="printInvoice">
            <img :src="iconPrint" />
          </button>
          <span class="tooltip-text">In hóa đơn</span>
        </div>
      </div>
    </div>

    <table class="lau-table">
      <thead>
        <tr>
          <th>STT</th>
          <th>Sản phẩm</th>
          <th>Danh mục</th>
          <th>Thương hiệu</th>
          <th>Chất liệu</th>
          <th>Màu</th>
          <th>Size</th>
          <th>SL</th>
          <th>Giá</th>
          <th>Thành tiền</th>
          <th>Trạng thái</th>
          <th>Tác động</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="(item, index) in danhSachChiTiet" :key="item.id">
          <td>{{ index + 1 }}</td>

          <td class="text-bold">{{ item.sanPham }}</td>

          <td>{{ item.danhMuc }}</td>

          <td>{{ item.thuongHieu }}</td>

          <td>{{ item.chatLieu }}</td>

          <td>{{ item.mau }}</td>

          <td>{{ item.size }}</td>

          <td>{{ item.soLuong }}</td>

          <td>{{ item.gia.toLocaleString() }} đ</td>

          <td class="text-bold">{{ item.thanhTien.toLocaleString() }} đ</td>

          <td>
            <span class="status-badge status-active">
              {{ item.trangThai }}
            </span>
          </td>
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
    </table>

    <div class="tong-tien">
      <span>Tổng tiền:</span>
      <strong>{{ tongTien.toLocaleString() }} đ</strong>
    </div>
  </div>
</template>

<style scoped>
.lau-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.lau-table th {
  background: #e9f2fb;
  padding: 10px;
  text-align: left;
}

.lau-table td {
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.text-bold {
  font-weight: 600;
}

.tong-tien {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  font-size: 16px;
}
.box-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.icon-btn {
  border: none;
  background: #f4f6f8;
  padding: 6px 12px;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 6px;
}

.icon-btn img {
  width: 16px;
}

.icon-btn.print {
  background: #e8f0ff;
}

.status-badge {
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
  color: white;
}

.status-active {
  background: #28a745;
}

.header-actions {
  display: flex;
  gap: 8px;
}

.tooltip-box {
  position: relative;
  display: inline-block;
}

.tooltip-text {
  visibility: hidden;
  position: absolute;

  top: 110%; /* nằm dưới icon */
  left: 50%;
  transform: translateX(-50%);

  background: #333;
  color: #fff;
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 6px;
  white-space: nowrap;
}

.tooltip-box:hover .tooltip-text {
  visibility: visible;
}
.icon-btn.view {
  background: #eef5ff;
  font-size: 14px;
}
</style>
