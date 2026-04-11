<script setup>
import { ref, watch, computed } from "vue";
import { updateNhanVien } from "@/api/admin/nhanvien/nhanVienApi";
import TaiKhoanNhanVien from "./TaiKhoanNhanVien.vue";

const props = defineProps({
  data: { type: Object, default: () => ({}) },
  show: { type: Boolean, default: false },
});

const emit = defineEmits(["close", "updated"]);

const form = ref({});
const showTaiKhoan = ref(false);

const daCoTaiKhoan = computed(() => {
  return !!form.value?.idTaiKhoan;
});

watch(
  () => props.data,
  (val) => {
    form.value = {
      ...val,
      ngaySinh: val?.ngaySinh || "",
      trangThai: val?.trangThai === true || val?.trangThai == 1,
      gioiTinh: val?.gioiTinh === true || val?.gioiTinh == 1,
    };
  },
  { immediate: true },
);

const dong = () => {
  emit("close");
};

const capNhat = async () => {
  try {
    const payload = {
      maNhanVien: form.value.maNhanVien,
      idTaiKhoan: form.value.idTaiKhoan || null,
      tenNhanVien: form.value.tenNhanVien,
      soDienThoai: form.value.soDienThoai,
      diaChi: form.value.diaChi,
      ngaySinh: form.value.ngaySinh || null,
      gioiTinh: form.value.gioiTinh,
      email: form.value.email,
      nguoiCapNhat: "admin",
      trangThai: form.value.trangThai,
    };

    await updateNhanVien(form.value.id, payload);
    alert("Cập nhật nhân viên thành công");
    emit("updated");
    dong();
  } catch (e) {
    console.error("Lỗi cập nhật nhân viên:", e);
    alert(e?.response?.data || "Cập nhật thất bại");
  }
};

const ngungLamViec = async () => {
  const ok = confirm(`Ngừng làm việc nhân viên ${form.value.tenNhanVien}?`);
  if (!ok) return;

  try {
    const payload = {
      maNhanVien: form.value.maNhanVien,
      idTaiKhoan: form.value.idTaiKhoan || null,
      tenNhanVien: form.value.tenNhanVien,
      soDienThoai: form.value.soDienThoai,
      diaChi: form.value.diaChi,
      ngaySinh: form.value.ngaySinh || null,
      gioiTinh: form.value.gioiTinh,
      email: form.value.email,
      nguoiCapNhat: "admin",
      trangThai: false,
    };

    await updateNhanVien(form.value.id, payload);
    alert("Đã ngừng làm việc");
    emit("updated");
    dong();
  } catch (e) {
    console.error("Lỗi ngừng làm việc:", e);
    alert(e?.response?.data || "Lỗi");
  }
};

const moQuanLyTaiKhoan = () => {
  showTaiKhoan.value = true;
};

const dongQuanLyTaiKhoan = () => {
  showTaiKhoan.value = false;
};

const capNhatTaiKhoanThanhCong = () => {
  emit("updated");
  showTaiKhoan.value = false;
};
</script>

<template>
  <div v-if="show" class="overlay">
    <div class="modal-box">
      <div class="modal-header">
        <h3>Chi tiết nhân viên</h3>
        <button class="btn-close" @click="dong">X</button>
      </div>

      <div class="modal-body">
        <div class="grid">
          <div class="item">
            <label>Mã NV</label>
            <input v-model="form.maNhanVien" />
          </div>

          <div class="item">
            <label>Tên nhân viên</label>
            <input v-model="form.tenNhanVien" />
          </div>

          <div class="item">
            <label>Tài khoản</label>
            <input :value="form.tenTaiKhoan || 'Chưa có tài khoản'" disabled />
          </div>

          <div class="item">
            <label>SĐT</label>
            <input v-model="form.soDienThoai" />
          </div>

          <div class="item">
            <label>Email</label>
            <input v-model="form.email" />
          </div>

          <div class="item">
            <label>Giới tính</label>
            <select v-model="form.gioiTinh">
              <option :value="true">Nam</option>
              <option :value="false">Nữ</option>
            </select>
          </div>

          <div class="item">
            <label>Ngày sinh</label>
            <input type="date" v-model="form.ngaySinh" />
          </div>

          <div class="item">
            <label>Vai trò</label>
            <input :value="form.tenVaiTro || '-'" disabled />
          </div>

          <div class="item full">
            <label>Địa chỉ</label>
            <textarea v-model="form.diaChi"></textarea>
          </div>

          <div class="item">
            <label>Trạng thái</label>
            <select v-model="form.trangThai">
              <option :value="true">Đang làm việc</option>
              <option :value="false">Ngừng làm việc</option>
            </select>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn-account" @click="moQuanLyTaiKhoan">
          {{ daCoTaiKhoan ? "Quản lý tài khoản" : "Thêm tài khoản" }}
        </button>

        <button class="btn-cancel" @click="dong">Đóng</button>
        <button class="btn-update" @click="capNhat">Cập nhật</button>

        <button class="btn-delete" v-if="form.trangThai" @click="ngungLamViec">
          Ngừng làm việc
        </button>
      </div>
    </div>
  </div>

  <TaiKhoanNhanVien
    :show="showTaiKhoan"
    :nhan-vien="form"
    :mode="daCoTaiKhoan ? 'update' : 'add'"
    @close="dongQuanLyTaiKhoan"
    @updated="capNhatTaiKhoanThanhCong"
  />
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-box {
  width: 700px;
  background: #fff;
  border-radius: 12px;
  padding: 20px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  margin-top: 10px;
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
  border: 1px solid #ddd;
  border-radius: 6px;
}

.full {
  grid-column: 1 / -1;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 16px;
  flex-wrap: wrap;
}

.btn-update {
  background: #3b6cff;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
}

.btn-delete {
  background: red;
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

.btn-account {
  background: #0ea5e9;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  margin-right: auto;
  cursor: pointer;
}

.btn-close {
  border: none;
  background: red;
  color: white;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}
</style>
