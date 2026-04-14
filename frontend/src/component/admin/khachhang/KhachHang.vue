<script setup>
import { ref, computed, watch, onMounted } from "vue";
import {
  getAllKhachHang,
  deleteKhachHang,
  getDetailKhachHang,
  addKhachHang,
} from "@/api/admin/khachhang/khachHangApi";

import KhachHangChiTiet from "@/component/admin/khachhang/KhachHangChiTiet.vue";

import iconEye from "@/assets/icons/eye.png";
import iconAdd from "@/assets/icons/add.png";
import iconSearch from "@/assets/icons/search.png";
import iconExcel from "@/assets/icons/excel.png";
import iconFirst from "@/assets/icons/first.png";
import iconPrev from "@/assets/icons/prev.png";
import iconNext from "@/assets/icons/next.png";
import iconLast from "@/assets/icons/last.png";

const search = ref("");
const trangThai = ref("");
const pageSize = ref(5);
const currentPage = ref(1);

const danhSachKhachHang = ref([]);
const totalItems = ref(0);

const showDetail = ref(false);
const dataDetail = ref(null);

const showAdd = ref(false);
const formAdd = ref({
  maKhachHang: "",
  tenKhachHang: "",
  ngaySinh: "",
  soDienThoai: "",
  email: "",
  nguoiTao: "admin",
  nguoiCapNhat: "",
  trangThai: 1,
});

const resetFormAdd = () => {
  formAdd.value = {
    maKhachHang: "",
    tenKhachHang: "",
    ngaySinh: "",
    soDienThoai: "",
    email: "",
    nguoiTao: "admin",
    nguoiCapNhat: "",
    trangThai: 1,
  };
};

const moFormThem = () => {
  resetFormAdd();
  showAdd.value = true;
};

const dongFormThem = () => {
  showAdd.value = false;
};

const themKhachHang = async () => {
  try {
    const payload = {
      ...formAdd.value,
      ngaySinh: formAdd.value.ngaySinh
        ? `${formAdd.value.ngaySinh}T00:00:00`
        : null,
      trangThai: Number(formAdd.value.trangThai),
    };

    console.log("Payload thêm khách hàng:", payload);

    await addKhachHang(payload);
    alert("Thêm khách hàng thành công");
    showAdd.value = false;
    loadKhachHang();
  } catch (error) {
    console.error("Lỗi thêm khách hàng:", error);
    console.error("Response lỗi:", error?.response?.data);
    alert(error?.response?.data || "Thêm khách hàng thất bại");
  }
};

const loadKhachHang = async () => {
  try {
    const res = await getAllKhachHang({
      keyword: search.value,
      trangThai: trangThai.value === "" ? null : Number(trangThai.value),
      page: currentPage.value - 1,
      size: pageSize.value,
    });

    danhSachKhachHang.value = res.data?.content || [];
    totalItems.value = res.data?.totalElements || 0;
  } catch (error) {
    console.error("Lỗi load khách hàng:", error);
  }
};

const totalPages = computed(
  () => Math.ceil(totalItems.value / pageSize.value) || 1,
);

const danhSachHienThi = computed(() => danhSachKhachHang.value);

const goFirst = () => {
  currentPage.value = 1;
};

const goLast = () => {
  currentPage.value = totalPages.value;
};

const goNext = () => {
  if (currentPage.value < totalPages.value) currentPage.value++;
};

const goPrev = () => {
  if (currentPage.value > 1) currentPage.value--;
};

const xemChiTiet = async (kh) => {
  try {
    const res = await getDetailKhachHang(kh.id);
    dataDetail.value = res.data;
    showDetail.value = true;
  } catch (error) {
    console.error(error);
    alert("Không lấy được chi tiết khách hàng");
  }
};

const xoaKhachHang = async (kh) => {
  if (!confirm(`Bạn có chắc muốn xóa khách hàng ${kh.tenKhachHang}?`)) return;
  try {
    await deleteKhachHang(kh.id);
    alert("Xóa thành công");
    loadKhachHang();
  } catch (error) {
    console.error(error);
    alert("Xóa thất bại");
  }
};

watch([search, trangThai, pageSize], () => {
  currentPage.value = 1;
  loadKhachHang();
});

watch(currentPage, () => {
  loadKhachHang();
});

onMounted(() => {
  loadKhachHang();
});
</script>

<template>
  <div class="quan-ly">
    <div class="box">
      <h2>Quản lý khách hàng</h2>
      <p>Danh sách khách hàng trong hệ thống</p>
    </div>

    <div class="filter-box">
      <div class="filter-item">
        <label>Tìm kiếm</label>
        <input v-model="search" placeholder="Mã, tên, SĐT, email..." />
      </div>

      <div class="filter-item">
        <label>Trạng thái</label>
        <select v-model="trangThai">
          <option value="">Tất cả</option>
          <option value="1">Đang hoạt động</option>
          <option value="0">Ngừng hoạt động</option>
        </select>
      </div>

      <div class="filter-search">
        <button class="btn-search">
          <img :src="iconSearch" />
          Tìm kiếm
        </button>
      </div>
    </div>

    <div class="box">
      <div class="box-header">
        <h3>Danh sách khách hàng</h3>

        <div class="header-actions">
          <button class="icon-btn">
            <img :src="iconExcel" />
          </button>

          <button class="btn-add" @click="moFormThem">
            <img :src="iconAdd" />
            Thêm khách hàng
          </button>
        </div>
      </div>

      <table class="lau-table">
        <thead>
          <tr>
            <th>STT</th>
            <th>Mã KH</th>
            <th>Tên khách hàng</th>
            <th>Số điện thoại</th>
            <th>Email</th>
            <th>Ngày sinh</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(kh, index) in danhSachHienThi" :key="kh.id">
            <td>{{ (currentPage - 1) * pageSize + index + 1 }}</td>
            <td class="text-bold">{{ kh.maKhachHang }}</td>
            <td>{{ kh.tenKhachHang }}</td>
            <td>{{ kh.soDienThoai || "-" }}</td>
            <td>{{ kh.email || "-" }}</td>
            <td>
              {{
                kh.ngaySinh ? new Date(kh.ngaySinh).toLocaleDateString() : "-"
              }}
            </td>
            <td>
              <span
                class="status-badge"
                :class="kh.trangThai === 1 ? 'status-active' : 'status-cancel'"
              >
                {{ kh.trangThai === 1 ? "Đang hoạt động" : "Ngừng hoạt động" }}
              </span>
            </td>
            <td class="text-center">
              <div class="action-group">
                <button class="btn-action-icon" @click="xemChiTiet(kh)">
                  <img :src="iconEye" />
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

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
            <button class="page-link"><img :src="iconFirst" /></button>
          </li>
          <li class="page-item" @click="goPrev">
            <button class="page-link"><img :src="iconPrev" /></button>
          </li>
          <li class="page-item active">
            <button class="page-link">{{ currentPage }}</button>
          </li>
          <li class="page-item" @click="goNext">
            <button class="page-link"><img :src="iconNext" /></button>
          </li>
          <li class="page-item" @click="goLast">
            <button class="page-link"><img :src="iconLast" /></button>
          </li>
        </ul>

        <span>
          Hiển thị {{ danhSachHienThi.length }} / {{ totalItems }} khách hàng
        </span>
      </div>
    </div>
  </div>

  <KhachHangChiTiet
    :show="showDetail"
    :data="dataDetail"
    @close="showDetail = false"
    @updated="loadKhachHang"
  />

  <div v-if="showAdd" class="overlay">
    <div class="modal-box add-box">
      <div class="modal-header">
        <h3>Thêm khách hàng</h3>
        <button class="btn-close" @click="dongFormThem">X</button>
      </div>

      <div class="modal-body">
        <div class="grid">
          <div class="item">
            <label>Mã KH</label>
            <input v-model="formAdd.maKhachHang" />
          </div>

          <div class="item">
            <label>Tên khách hàng</label>
            <input v-model="formAdd.tenKhachHang" />
          </div>

          <div class="item">
            <label>Số điện thoại</label>
            <input v-model="formAdd.soDienThoai" />
          </div>

          <div class="item">
            <label>Email</label>
            <input v-model="formAdd.email" />
          </div>

          <div class="item">
            <label>Ngày sinh</label>
            <input type="date" v-model="formAdd.ngaySinh" />
          </div>

          <div class="item full">
            <label>Trạng thái</label>
            <select v-model="formAdd.trangThai">
              <option :value="1">Đang hoạt động</option>
              <option :value="0">Ngừng hoạt động</option>
            </select>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn-cancel" @click="dongFormThem">Đóng</button>
        <button class="btn-update" @click="themKhachHang">Thêm</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.quan-ly {
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
  background: #da1432;
  color: #fff;
  border: none;
  padding: 8px 14px;
  border-radius: 8px;
  cursor: pointer;
}
.btn-add img,
.btn-search img {
  width: 16px;
  filter: brightness(0) invert(1);
}
.filter-box {
  background: #fff;
  padding: 18px;
  border-radius: 10px;
  display: grid;
  grid-template-columns: 2fr 1fr auto;
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
  background: #da1432;
  color: #fff;
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
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
.action-group {
  display: inline-flex;
  gap: 6px;
}
.lau-table td:last-child,
.lau-table th:last-child {
  text-align: center;
}
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 99999;
}
.modal-box {
  background: #fff;
  border-radius: 12px;
  width: 700px;
  padding: 20px;
  position: relative;
  z-index: 100000;
}
.add-box {
  width: 760px;
}
.modal-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
}
.btn-close {
  border: none;
  background: red;
  color: white;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}
.modal-body {
  margin-top: 10px;
}
.grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 14px;
}
.item {
  display: flex;
  flex-direction: column;
}
.item label {
  font-weight: 600;
  margin-bottom: 4px;
}
.item input,
.item textarea,
.item select {
  padding: 8px;
  border-radius: 6px;
  border: 1px solid #ddd;
  background: #fff;
}
.full {
  grid-column: 1 / -1;
}
.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 16px;
}
.btn-update {
  background: #da1432;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
}
.btn-cancel {
  background: #ccc;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
}
</style>
