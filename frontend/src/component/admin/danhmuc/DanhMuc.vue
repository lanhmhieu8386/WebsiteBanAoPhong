<script setup>
import { ref, computed, onMounted, watch } from "vue";
import axiosClient from "@/auth/axiosClient";

import iconEdit from "@/assets/icons/edit.png";
import iconAdd from "@/assets/icons/add.png";
import iconSearch from "@/assets/icons/search.png";
//import iconDelete from "@/assets/icons/delete.png";

const loaiDanhSach = [
  { key: "danh-muc", label: "Danh mục" },
  { key: "thuong-hieu", label: "Thương hiệu" },
  { key: "chat-lieu", label: "Chất liệu" },
  { key: "kich-co", label: "Kích cỡ" },
  { key: "mau-sac", label: "Màu sắc" },
  { key: "hinh-anh", label: "Hình ảnh" },
];

const configMap = {
  "danh-muc": {
    title: "Danh mục",
    endpoint: "/admin/danh-muc",
    fields: {
      ma: "maDanhMuc",
      ten: "tenDanhMuc",
      moTa: "moTa",
      trangThai: "trangThai",
    },
    hasMa: true,
    hasTen: true,
    hasMoTa: true,
    hasImage: false,
    hasTrangThai: true,
    deleteMethod: "put",
  },

  "thuong-hieu": {
    title: "Thương hiệu",
    endpoint: "/admin/thuong-hieu",
    fields: {
      ma: "maThuongHieu",
      ten: "tenThuongHieu",
      moTa: "moTa",
      trangThai: "trangThai",
    },
    hasMa: true,
    hasTen: true,
    hasMoTa: true,
    hasImage: false,
    hasTrangThai: true,
    deleteMethod: "put",
  },

  "chat-lieu": {
    title: "Chất liệu",
    endpoint: "/admin/chat-lieu",
    fields: {
      ma: "maChatLieu",
      ten: "tenChatLieu",
      moTa: "moTa",
      trangThai: "trangThai",
    },
    hasMa: true,
    hasTen: true,
    hasMoTa: true,
    hasImage: false,
    hasTrangThai: true,
    deleteMethod: "put",
  },

  "kich-co": {
    title: "Kích cỡ",
    endpoint: "/admin/kich-co",
    fields: {
      ma: "maKichCo",
      ten: "tenKichCo",
    },
    hasMa: true,
    hasTen: true,
    hasMoTa: false,
    hasImage: false,
    hasTrangThai: false,
    deleteMethod: "delete",
  },

  "mau-sac": {
    title: "Màu sắc",
    endpoint: "/admin/mau-sac",
    fields: {
      ma: "maMauSac",
      ten: "tenMauSac",
    },
    hasMa: true,
    hasTen: true,
    hasMoTa: false,
    hasImage: false,
    hasTrangThai: false,
    deleteMethod: "delete",
  },

  "hinh-anh": {
    title: "Hình ảnh",
    endpoint: "/admin/hinh-anh",
    fields: {
      urlAnh: "urlAnh",
    },
    hasMa: false,
    hasTen: false,
    hasMoTa: false,
    hasImage: true,
    hasTrangThai: false,
    deleteMethod: "delete",
  },
};

const loaiDangChon = ref("danh-muc");
const search = ref("");
const trangThai = ref("");
const pageSize = ref(10);
const currentPage = ref(1);

const dsGoc = ref([]);
const loading = ref(false);

const showModal = ref(false);
const isEdit = ref(false);

const form = ref({
  id: null,
  ma: "",
  ten: "",
  moTa: "",
  urlAnh: "",
  trangThai: true,
});

const currentConfig = computed(() => configMap[loaiDangChon.value]);

const tieuDeTrang = computed(
  () => `Quản lý ${currentConfig.value.title.toLowerCase()}`,
);

const tieuDeBang = computed(
  () => `Danh sách ${currentConfig.value.title.toLowerCase()}`,
);

const convertTrangThaiText = (value) => {
  return value === true ? "Hoạt động" : "Ngừng";
};

const resetForm = () => {
  form.value = {
    id: null,
    ma: "",
    ten: "",
    moTa: "",
    urlAnh: "",
    trangThai: true,
  };
};

const mapItem = (item) => {
  const cfg = currentConfig.value;

  return {
    id: item.id,
    ma: cfg.hasMa ? (item[cfg.fields.ma] ?? "") : "",
    ten: cfg.hasTen ? (item[cfg.fields.ten] ?? "") : "",
    moTa: cfg.hasMoTa ? (item[cfg.fields.moTa] ?? "") : "",
    urlAnh: cfg.hasImage ? (item[cfg.fields.urlAnh] ?? "") : "",
    trangThai: cfg.hasTrangThai ? item[cfg.fields.trangThai] === true : null,
    raw: item,
  };
};

const buildPayload = () => {
  const cfg = currentConfig.value;
  const payload = {};

  if (cfg.hasMa) {
    payload[cfg.fields.ma] = form.value.ma?.trim();
  }

  if (cfg.hasTen) {
    payload[cfg.fields.ten] = form.value.ten?.trim();
  }

  if (cfg.hasMoTa) {
    payload[cfg.fields.moTa] = form.value.moTa?.trim();
  }

  if (cfg.hasImage) {
    payload[cfg.fields.urlAnh] = form.value.urlAnh?.trim();
  }

  if (cfg.hasTrangThai) {
    payload[cfg.fields.trangThai] = form.value.trangThai;
  }

  return payload;
};

const loadData = async () => {
  loading.value = true;
  currentPage.value = 1;

  try {
    const res = await axiosClient.get(
      `${currentConfig.value.endpoint}/hien-thi`,
    );
    const data = Array.isArray(res.data) ? res.data : [];
    dsGoc.value = data.map(mapItem);
  } catch (error) {
    console.error("Lỗi load dữ liệu:", error);
    dsGoc.value = [];
  } finally {
    loading.value = false;
  }
};

const dsDaLoc = computed(() => {
  return dsGoc.value.filter((item) => {
    const keyword = search.value.trim().toLowerCase();

    const matchSearch =
      !keyword ||
      item.ma.toLowerCase().includes(keyword) ||
      item.ten.toLowerCase().includes(keyword) ||
      item.moTa.toLowerCase().includes(keyword) ||
      item.urlAnh.toLowerCase().includes(keyword);

    const matchTrangThai = !currentConfig.value.hasTrangThai
      ? true
      : trangThai.value === ""
        ? true
        : String(item.trangThai) === trangThai.value;

    return matchSearch && matchTrangThai;
  });
});

const totalItems = computed(() => dsDaLoc.value.length);

const totalPages = computed(() => {
  return Math.ceil(totalItems.value / pageSize.value) || 1;
});

const danhSachHienThi = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  return dsDaLoc.value.slice(start, start + pageSize.value);
});

const moFormThem = () => {
  resetForm();
  isEdit.value = false;
  showModal.value = true;
};

const moFormSua = (item) => {
  form.value = {
    id: item.id,
    ma: item.ma || "",
    ten: item.ten || "",
    moTa: item.moTa || "",
    urlAnh: item.urlAnh || "",
    trangThai: item.trangThai === null ? true : item.trangThai,
  };

  isEdit.value = true;
  showModal.value = true;
};

const dongForm = () => {
  showModal.value = false;
  resetForm();
};

const validateForm = () => {
  if (currentConfig.value.hasMa && !form.value.ma?.trim()) {
    alert("Mã không được để trống");
    return false;
  }

  if (currentConfig.value.hasTen && !form.value.ten?.trim()) {
    alert("Tên không được để trống");
    return false;
  }

  if (currentConfig.value.hasImage && !form.value.urlAnh?.trim()) {
    alert("URL ảnh không được để trống");
    return false;
  }

  return true;
};

const luuDuLieu = async () => {
  if (!validateForm()) return;

  try {
    const payload = buildPayload();

    if (isEdit.value) {
      await axiosClient.put(
        `${currentConfig.value.endpoint}/sua/${form.value.id}`,
        payload,
      );
    } else {
      await axiosClient.post(`${currentConfig.value.endpoint}/them`, payload);
    }

    await loadData();
    dongForm();
    alert(isEdit.value ? "Cập nhật thành công" : "Thêm mới thành công");
  } catch (error) {
    console.error("Lỗi lưu dữ liệu:", error);
    alert(
      error?.response?.data?.message ||
        error?.response?.data ||
        "Lưu dữ liệu thất bại",
    );
  }
};

const xoaDuLieu = async (item) => {
  const ok = confirm(
    `Bạn có chắc muốn xóa ${currentConfig.value.title.toLowerCase()} này không?`,
  );
  if (!ok) return;

  try {
    if (currentConfig.value.deleteMethod === "put") {
      await axiosClient.put(`${currentConfig.value.endpoint}/xoa/${item.id}`);
    } else {
      await axiosClient.delete(
        `${currentConfig.value.endpoint}/xoa/${item.id}`,
      );
    }

    await loadData();
    alert("Xóa thành công");
  } catch (error) {
    console.error("Lỗi xóa:", error);
    alert(
      error?.response?.data?.message || error?.response?.data || "Xóa thất bại",
    );
  }
};

const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--;
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) currentPage.value++;
};

watch(loaiDangChon, async () => {
  search.value = "";
  trangThai.value = "";
  await loadData();
});

watch(pageSize, () => {
  currentPage.value = 1;
});

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="quan-ly-thuoc-tinh">
    <div class="box">
      <h2>{{ tieuDeTrang }}</h2>
      <p>
        Quản lý danh mục, thương hiệu, chất liệu, kích cỡ, màu sắc và hình ảnh
      </p>
    </div>

    <div class="box">
      <div class="tab-list">
        <button
          v-for="item in loaiDanhSach"
          :key="item.key"
          class="tab-btn"
          :class="{ active: loaiDangChon === item.key }"
          @click="loaiDangChon = item.key"
        >
          {{ item.label }}
        </button>
      </div>
    </div>

    <div class="filter-box">
      <div class="filter-item">
        <label>Tìm kiếm</label>
        <input
          v-model="search"
          :placeholder="`Tìm ${currentConfig.title.toLowerCase()}...`"
        />
      </div>

      <div class="filter-item" v-if="currentConfig.hasTrangThai">
        <label>Trạng thái</label>
        <select v-model="trangThai">
          <option value="">Tất cả</option>
          <option value="true">Hoạt động</option>
          <option value="false">Ngừng</option>
        </select>
      </div>

      <div class="filter-item">
        <label>Số dòng</label>
        <select v-model="pageSize">
          <option :value="5">5 / trang</option>
          <option :value="10">10 / trang</option>
          <option :value="20">20 / trang</option>
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
        <h3>{{ tieuDeBang }}</h3>

        <button class="btn-add" @click="moFormThem">
          <img :src="iconAdd" />
          Thêm {{ currentConfig.title.toLowerCase() }}
        </button>
      </div>

      <table class="lau-table">
        <thead>
          <tr>
            <th>STT</th>
            <th v-if="currentConfig.hasMa">Mã</th>
            <th v-if="currentConfig.hasTen">Tên</th>
            <th v-if="currentConfig.hasMoTa">Mô tả</th>
            <th v-if="currentConfig.hasImage">Hình ảnh</th>
            <th v-if="currentConfig.hasTrangThai">Trạng thái</th>
            <th class="action-header">Thao tác</th>
          </tr>
        </thead>

        <tbody v-if="!loading && danhSachHienThi.length">
          <tr v-for="(item, index) in danhSachHienThi" :key="item.id">
            <td>{{ (currentPage - 1) * pageSize + index + 1 }}</td>

            <td v-if="currentConfig.hasMa">{{ item.ma }}</td>
            <td v-if="currentConfig.hasTen" class="text-bold">
              {{ item.ten }}
            </td>
            <td v-if="currentConfig.hasMoTa">{{ item.moTa }}</td>

            <td v-if="currentConfig.hasImage">
              <img
                v-if="item.urlAnh"
                :src="item.urlAnh"
                alt="Ảnh"
                class="preview-img"
              />
              <span v-else>Chưa có ảnh</span>
            </td>

            <td v-if="currentConfig.hasTrangThai">
              <span
                class="status-badge"
                :class="item.trangThai ? 'status-active' : 'status-cancel'"
              >
                {{ convertTrangThaiText(item.trangThai) }}
              </span>
            </td>

            <td class="action-cell">
              <div class="action-wrapper">
                <button class="btn-action-icon" @click="moFormSua(item)">
                  <img :src="iconEdit" />
                </button>

                <button
                  class="btn-action-icon btn-delete"
                  @click="xoaDuLieu(item)"
                >
                  <img :src="iconDelete" />
                </button>
              </div>
            </td>
          </tr>
        </tbody>

        <tbody v-else-if="!loading">
          <tr>
            <td :colspan="7" class="empty-text">Không có dữ liệu</td>
          </tr>
        </tbody>

        <tbody v-else>
          <tr>
            <td :colspan="7" class="empty-text">Đang tải dữ liệu...</td>
          </tr>
        </tbody>
      </table>

      <div class="pagination">
        <button
          class="btn-page"
          :disabled="currentPage === 1"
          @click="prevPage"
        >
          « Trước
        </button>

        <span>Trang {{ currentPage }} / {{ totalPages }}</span>

        <button
          class="btn-page"
          :disabled="currentPage === totalPages"
          @click="nextPage"
        >
          Sau »
        </button>
      </div>
    </div>

    <div v-if="showModal" class="modal-overlay">
      <div class="modal-box">
        <h3>
          {{
            isEdit
              ? `Sửa ${currentConfig.title.toLowerCase()}`
              : `Thêm ${currentConfig.title.toLowerCase()}`
          }}
        </h3>

        <div v-if="currentConfig.hasMa" class="form-group">
          <label>Mã</label>
          <input v-model="form.ma" />
        </div>

        <div v-if="currentConfig.hasTen" class="form-group">
          <label>Tên</label>
          <input v-model="form.ten" />
        </div>

        <div v-if="currentConfig.hasMoTa" class="form-group">
          <label>Mô tả</label>
          <textarea v-model="form.moTa"></textarea>
        </div>

        <div v-if="currentConfig.hasImage" class="form-group">
          <label>URL ảnh</label>
          <input v-model="form.urlAnh" />
        </div>

        <div v-if="currentConfig.hasImage && form.urlAnh" class="form-group">
          <label>Xem trước</label>
          <img :src="form.urlAnh" class="preview-large" />
        </div>

        <div v-if="currentConfig.hasTrangThai" class="form-group">
          <label>Trạng thái</label>
          <select v-model="form.trangThai">
            <option :value="true">Hoạt động</option>
            <option :value="false">Ngừng</option>
          </select>
        </div>

        <div class="modal-actions">
          <button class="btn-cancel" @click="dongForm">Hủy</button>
          <button class="btn-save" @click="luuDuLieu">Lưu</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.quan-ly-thuoc-tinh {
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

.tab-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.tab-btn {
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  background: #eef2ff;
  cursor: pointer;
  font-weight: 600;
}

.tab-btn.active {
  background: #3b6cff;
  color: white;
}

.box-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
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
.filter-item select,
.form-group input,
.form-group textarea,
.form-group select {
  height: 38px;
  border-radius: 8px;
  border: 1px solid #ddd;
  padding: 0 10px;
}

.form-group textarea {
  min-height: 80px;
  padding-top: 10px;
}

.filter-search {
  display: flex;
  align-items: flex-end;
}

.btn-search {
  height: 38px;
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

.lau-table td {
  padding: 12px;
  border-bottom: 1px solid #eee;
  vertical-align: middle;
}

.text-bold {
  font-weight: 600;
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

.action-cell {
  text-align: center;
  width: 120px;
}

.action-header {
  text-align: center;
  width: 120px;
}

.action-wrapper {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.btn-action-icon {
  width: 34px;
  height: 34px;
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

.btn-delete {
  background: #fff1f0;
}

.preview-img {
  width: 50px;
  height: 50px;
  border-radius: 6px;
  object-fit: cover;
  border: 1px solid #eee;
}

.preview-large {
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #eee;
}

.empty-text {
  text-align: center;
  padding: 20px;
  color: #888;
}

.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  align-items: center;
}

.btn-page {
  border: none;
  background: #3b6cff;
  color: white;
  padding: 8px 12px;
  border-radius: 6px;
  cursor: pointer;
}

.btn-page:disabled {
  background: #c7d2fe;
  cursor: not-allowed;
}

.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.35);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-box {
  width: 420px;
  background: white;
  border-radius: 12px;
  padding: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-bottom: 12px;
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
  cursor: pointer;
}

.btn-save {
  background: #3b6cff;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
}
</style>
