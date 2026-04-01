<script setup>
import { ref, watch } from "vue";
import {
  getTaiKhoanKhachHang,
  addTaiKhoanKhachHang,
  updateTaiKhoanKhachHang,
  deleteTaiKhoanKhachHang,
} from "@/api/admin/khachhang/khachHangApi";

const props = defineProps({
  show: { type: Boolean, default: false },
  khachHang: { type: Object, default: () => ({}) },
});

const emit = defineEmits(["close", "updated"]);

const hasTaiKhoan = ref(false);

const formTaiKhoan = ref({
  maTaiKhoan: "",
  tenTaiKhoan: "",
  email: "",
  matKhau: "",
  soDienThoai: "",
  diaChi: "",
  idVaiTro: 3,
});

const resetFormTaiKhoan = () => {
  formTaiKhoan.value = {
    maTaiKhoan: "",
    tenTaiKhoan: "",
    email: "",
    matKhau: "",
    soDienThoai: "",
    diaChi: "",
    idVaiTro: 3,
  };
};

const loadTaiKhoan = async () => {
  if (!props.khachHang?.id) {
    hasTaiKhoan.value = false;
    resetFormTaiKhoan();
    return;
  }

  try {
    const res = await getTaiKhoanKhachHang(props.khachHang.id);

    if (res?.data) {
      hasTaiKhoan.value = true;
      formTaiKhoan.value = {
        maTaiKhoan: res.data.maTaiKhoan || "",
        tenTaiKhoan: res.data.tenTaiKhoan || "",
        email: res.data.email || "",
        matKhau: res.data.matKhau || "",
        soDienThoai: res.data.soDienThoai || "",
        diaChi: res.data.diaChi || "",
        idVaiTro: 3,
      };
    } else {
      hasTaiKhoan.value = false;
      resetFormTaiKhoan();
    }
  } catch (error) {
    hasTaiKhoan.value = false;
    resetFormTaiKhoan();
  }
};

watch(
  () => props.show,
  async (val) => {
    if (val) {
      await loadTaiKhoan();
    }
  },
);

watch(
  () => props.khachHang,
  async (val) => {
    if (props.show && val?.id) {
      await loadTaiKhoan();
    }
  },
  { deep: true },
);

const dong = () => {
  emit("close");
};

const luuTaiKhoan = async () => {
  try {
    const payload = {
      ...formTaiKhoan.value,
      idVaiTro: 3,
    };

    if (hasTaiKhoan.value) {
      await updateTaiKhoanKhachHang(props.khachHang.id, payload);
      alert("Cập nhật tài khoản thành công");
    } else {
      await addTaiKhoanKhachHang(props.khachHang.id, payload);
      alert("Thêm tài khoản thành công");
    }

    emit("updated");
  } catch (error) {
    console.error(error);
    alert("Lưu tài khoản thất bại");
  }
};

const xoaTaiKhoan = async () => {
  const ok = confirm("Bạn có chắc muốn xóa tài khoản khách hàng này?");
  if (!ok) return;

  try {
    await deleteTaiKhoanKhachHang(props.khachHang.id);
    alert("Xóa tài khoản thành công");
    hasTaiKhoan.value = false;
    resetFormTaiKhoan();
    emit("updated");
  } catch (error) {
    console.error(error);
    alert("Xóa tài khoản thất bại");
  }
};
</script>

<template>
  <div v-if="show" class="overlay">
    <div class="modal-box">
      <div class="modal-header">
        <h3>
          {{
            hasTaiKhoan
              ? "Cập nhật tài khoản khách hàng"
              : "Thêm tài khoản khách hàng"
          }}
        </h3>
        <button class="btn-close" @click="dong">X</button>
      </div>

      <div class="modal-body">
        <div class="info-box">
          <p><strong>Mã KH:</strong> {{ khachHang?.maKhachHang || "" }}</p>
          <p><strong>Tên KH:</strong> {{ khachHang?.tenKhachHang || "" }}</p>
        </div>

        <div class="grid">
          <div class="item">
            <label>Mã tài khoản</label>
            <input v-model="formTaiKhoan.maTaiKhoan" />
          </div>

          <div class="item">
            <label>Tên tài khoản</label>
            <input v-model="formTaiKhoan.tenTaiKhoan" />
          </div>

          <div class="item">
            <label>Email</label>
            <input v-model="formTaiKhoan.email" />
          </div>

          <div class="item">
            <label>Mật khẩu</label>
            <input v-model="formTaiKhoan.matKhau" />
          </div>

          <div class="item">
            <label>Số điện thoại</label>
            <input v-model="formTaiKhoan.soDienThoai" />
          </div>

          <div class="item">
            <label>Vai trò</label>
            <input value="Khách hàng" disabled />
          </div>

          <div class="item full">
            <label>Địa chỉ</label>
            <textarea v-model="formTaiKhoan.diaChi"></textarea>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn-cancel" @click="dong">Đóng</button>

        <button class="btn-update" @click="luuTaiKhoan">
          {{ hasTaiKhoan ? "Cập nhật tài khoản" : "Thêm tài khoản" }}
        </button>

        <button class="btn-delete" v-if="hasTaiKhoan" @click="xoaTaiKhoan">
          Xóa tài khoản
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100001;
}

.modal-box {
  width: 760px;
  background: #fff;
  border-radius: 12px;
  padding: 20px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-body {
  margin-top: 10px;
}

.info-box {
  background: #f8fafc;
  border: 1px solid #e5e7eb;
  padding: 10px 12px;
  border-radius: 8px;
  margin-bottom: 12px;
}

.info-box p {
  margin: 4px 0;
}

.grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.item {
  display: flex;
  flex-direction: column;
}

.item input,
.item textarea {
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

.btn-close {
  border: none;
  background: red;
  color: white;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}
</style>
