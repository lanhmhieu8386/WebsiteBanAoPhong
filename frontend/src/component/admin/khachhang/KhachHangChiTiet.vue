<script setup>
import { ref, watch } from "vue";
import { updateKhachHang } from "@/api/admin/khachhang/khachHangApi";
import TaiKhoanKhachHang from "./TaiKhoanKhachHang.vue";

const props = defineProps({
  data: { type: Object, default: () => ({}) },
  show: { type: Boolean, default: false },
});

const emit = defineEmits(["close", "updated"]);

const form = ref({});
const showTaiKhoan = ref(false);

watch(
  () => props.data,
  (val) => {
    form.value = {
      ...val,
      ngaySinh: val?.ngaySinh ? val.ngaySinh.split("T")[0] : "",
      trangThai: val?.trangThai == 1 ? 1 : 0,
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
      ...form.value,
      trangThai: Number(form.value.trangThai),
    };

    await updateKhachHang(form.value.id, payload);
    alert("Cập nhật thành công");

    emit("updated");
    dong();
  } catch (e) {
    console.error(e);
    alert("Cập nhật thất bại");
  }
};

const xoaKhachHang = async () => {
  const ok = confirm(
    `Bạn có chắc muốn ngừng hoạt động khách hàng ${form.value.tenKhachHang}?`,
  );
  if (!ok) return;

  try {
    const payload = {
      ...form.value,
      trangThai: 0,
    };

    await updateKhachHang(form.value.id, payload);

    alert("Đã ngừng hoạt động");

    emit("updated");
    dong();
  } catch (e) {
    console.error(e);
    alert("Lỗi");
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
        <h3>Chi tiết khách hàng</h3>
        <button class="btn-close" @click="dong">X</button>
      </div>

      <div class="modal-body">
        <div class="grid">
          <div class="item">
            <label>Mã KH</label>
            <input v-model="form.maKhachHang" />
          </div>

          <div class="item">
            <label>Tên KH</label>
            <input v-model="form.tenKhachHang" />
          </div>

          <div class="item">
            <label>Tài khoản</label>
            <input v-model="form.tenTaiKhoan" disabled />
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
            <label>Ngày sinh</label>
            <input type="date" v-model="form.ngaySinh" />
          </div>

          <div class="item full">
            <label>Trạng thái</label>
            <select v-model="form.trangThai">
              <option :value="1">Đang hoạt động</option>
              <option :value="0">Ngừng hoạt động</option>
            </select>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn-account" @click="moQuanLyTaiKhoan">
          Quản lý tài khoản
        </button>

        <button class="btn-cancel" @click="dong">Đóng</button>

        <button class="btn-update" @click="capNhat">Cập nhật</button>

        <button
          class="btn-delete"
          v-if="form.trangThai == 1"
          @click="xoaKhachHang"
        >
          Ngừng hoạt động
        </button>
      </div>
    </div>
  </div>

  <TaiKhoanKhachHang
    :show="showTaiKhoan"
    :khach-hang="form"
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
  background: white;
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

.item input,
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
