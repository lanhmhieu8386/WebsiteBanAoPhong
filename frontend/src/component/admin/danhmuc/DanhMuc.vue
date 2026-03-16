<script setup>
import { ref, computed, onMounted } from "vue";
import ThemDanhMuc from "@/component/admin/danhmuc/ThemDanhMuc.vue";

import iconEye from "@/assets/icons/eye.png";
import iconEdit from "@/assets/icons/edit.png";
import iconAdd from "@/assets/icons/add.png";
import iconSearch from "@/assets/icons/search.png";

const showEdit = ref(false);
const editDanhMuc = ref(null);

const search = ref("");
const trangThai = ref("");

const pageSize = ref(10);
const currentPage = ref(1);

const danhSachDanhMuc = ref([]);

const fakeDanhMuc = [
  {
    id: 1,
    ma: "DM01",
    ten: "Thời trang",
    moTa: "Danh mục quần áo",
    trangThai: "Hoạt động",
  },
  {
    id: 2,
    ma: "DM02",
    ten: "Giày dép",
    moTa: "Danh mục giày",
    trangThai: "Hoạt động",
  },
  {
    id: 3,
    ma: "DM03",
    ten: "Phụ kiện",
    moTa: "Danh mục phụ kiện",
    trangThai: "Ngừng",
  },
];

const loadDanhMuc = () => {
  danhSachDanhMuc.value = fakeDanhMuc;
};

const totalItems = computed(() => danhSachDanhMuc.value.length);

const totalPages = computed(
  () => Math.ceil(totalItems.value / pageSize.value) || 1,
);

const danhSachHienThi = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  return danhSachDanhMuc.value.slice(start, start + pageSize.value);
});

const showForm = ref(false);

const moForm = () => {
  showForm.value = true;
};

const dongForm = () => {
  showForm.value = false;
};

const themDanhMuc = (data) => {
  danhSachDanhMuc.value.push({
    id: Date.now(),
    ...data,
  });
  showForm.value = false;
};

const suaDanhMuc = (dm) => {
  editDanhMuc.value = { ...dm };
  showEdit.value = true;
};

const saveEdit = () => {
  const index = danhSachDanhMuc.value.findIndex(
    (d) => d.id === editDanhMuc.value.id,
  );

  if (index !== -1) {
    danhSachDanhMuc.value[index] = { ...editDanhMuc.value };
  }

  showEdit.value = false;
};

onMounted(() => {
  loadDanhMuc();
});
</script>

<template>
  <div class="quan-ly-danh-muc">
    <div class="box">
      <h2>Quản lý danh mục</h2>
      <p>Danh sách danh mục sản phẩm</p>
    </div>

    <!-- FILTER -->
    <div class="filter-box">
      <div class="filter-item">
        <label>Tìm kiếm</label>
        <input v-model="search" placeholder="Tên danh mục..." />
      </div>

      <div class="filter-item">
        <label>Trạng thái</label>
        <select v-model="trangThai">
          <option value="">Tất cả</option>
          <option>Hoạt động</option>
          <option>Ngừng</option>
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
        <h3>Danh sách danh mục</h3>

        <button class="btn-add" @click="moForm">
          <img :src="iconAdd" />
          Thêm danh mục
        </button>
      </div>

      <table class="lau-table">
        <thead>
          <tr>
            <th>STT</th>
            <th>Mã</th>
            <th>Tên danh mục</th>
            <th>Mô tả</th>
            <th>Trạng thái</th>
            <th class="action-header">Thao tác</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(dm, index) in danhSachHienThi" :key="dm.id">
            <td>{{ index + 1 }}</td>

            <td>{{ dm.ma }}</td>

            <td class="text-bold">{{ dm.ten }}</td>

            <td>{{ dm.moTa }}</td>

            <td>
              <span
                class="status-badge"
                :class="
                  dm.trangThai == 'Hoạt động'
                    ? 'status-active'
                    : 'status-cancel'
                "
              >
                {{ dm.trangThai }}
              </span>
            </td>

            <td class="action-cell">
              <div class="action-wrapper">
                <button class="btn-action-icon" @click="suaDanhMuc(dm)">
                  <img :src="iconEye" />
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <!-- MODAL SỬA DANH MỤC -->

  <div v-if="showEdit" class="modal-overlay">
    <div class="modal-box">
      <h3>Sửa danh mục</h3>

      <div class="form-group">
        <label>Mã</label>
        <input v-model="editDanhMuc.ma" />
      </div>

      <div class="form-group">
        <label>Tên danh mục</label>
        <input v-model="editDanhMuc.ten" />
      </div>

      <div class="form-group">
        <label>Mô tả</label>
        <textarea v-model="editDanhMuc.moTa"></textarea>
      </div>

      <div class="form-group">
        <label>Trạng thái</label>

        <select v-model="editDanhMuc.trangThai">
          <option>Hoạt động</option>
          <option>Ngừng</option>
        </select>
      </div>

      <div class="modal-actions">
        <button class="btn-cancel" @click="showEdit = false">Hủy</button>

        <button class="btn-save" @click="saveEdit">Lưu</button>
      </div>
    </div>
  </div>
  <ThemDanhMuc v-if="showForm" @close="dongForm" @save="themDanhMuc" />
</template>

<style scoped>
.quan-ly-danh-muc {
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
  table-layout: fixed;
}

.lau-table th {
  background: #e9f2fb;
  padding: 12px;
  text-align: left;
}

.lau-table th.action-header {
  text-align: center;
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
  width: 32px;
  height: 32px;
  border: none;
  background: #f5f6f8;
  border-radius: 6px;
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-action-icon img {
  width: 16px;
  height: 16px;
}

.action-cell {
  text-align: center;
  width: 120px;
}
.action-cell button {
  margin: 0 4px;
}
.action-header {
  text-align: center;
  width: 120px;
}
.action-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
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
  z-index: 1000;
}

.modal-box {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 380px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 12px;
}

.form-group input,
.form-group textarea,
.form-group select {
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 6px 10px;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 10px;
}

.btn-cancel {
  background: #ddd;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
}

.btn-save {
  background: #3b6cff;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
}
</style>
