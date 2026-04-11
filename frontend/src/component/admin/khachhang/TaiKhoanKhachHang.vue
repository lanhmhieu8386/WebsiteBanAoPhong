<script setup>
import { ref, watch, computed } from "vue";
import {
  getTaiKhoanKhachHang,
  addTaiKhoanKhachHang,
  updateTaiKhoanKhachHang,
  deleteTaiKhoanKhachHang,
  getAllVaiTro,
} from "@/api/admin/taikhoan/TaiKhoanApi";

const props = defineProps({
  show: { type: Boolean, default: false },
  khachHang: { type: Object, default: () => ({}) },
  mode: { type: String, default: "update" }, // add | update
});

const emit = defineEmits(["close", "updated"]);

const loading = ref(false);
const danhSachVaiTro = ref([]);

const formTaiKhoan = ref({
  maTaiKhoan: "",
  tenTaiKhoan: "",
  email: "",
  matKhau: "",
  soDienThoai: "",
  diaChi: "",
  idVaiTro: "",
  trangThai: true,
});

const isAddMode = computed(() => props.mode === "add");

const resetForm = () => {
  formTaiKhoan.value = {
    maTaiKhoan: "",
    tenTaiKhoan: "",
    email: props.khachHang?.email || "",
    matKhau: "",
    soDienThoai: props.khachHang?.soDienThoai || "",
    diaChi: "",
    idVaiTro: "",
    trangThai: true,
  };
};

const loadVaiTro = async () => {
  try {
    const res = await getAllVaiTro();
    danhSachVaiTro.value = Array.isArray(res.data) ? res.data : [];
  } catch (error) {
    console.error("Lỗi load vai trò:", error);
    console.error("Response lỗi vai trò:", error?.response?.data);
    danhSachVaiTro.value = [];
  }
};

const loadTaiKhoanChiTiet = async () => {
  if (!props.khachHang?.id) return;

  try {
    loading.value = true;
    const res = await getTaiKhoanKhachHang(props.khachHang.id);
    const data = res.data || {};

    formTaiKhoan.value = {
      maTaiKhoan: data.maTaiKhoan || "",
      tenTaiKhoan: data.tenTaiKhoan || "",
      email: data.email || props.khachHang?.email || "",
      matKhau: "",
      soDienThoai: data.soDienThoai || props.khachHang?.soDienThoai || "",
      diaChi: data.diaChi || "",
      idVaiTro: data.idVaiTro || data.vaiTroId || data.vaiTro?.id || "",
      trangThai: data.trangThai ?? true,
    };
  } catch (error) {
    console.error("Lỗi load tài khoản khách hàng:", error);
    console.error("Response lỗi:", error?.response?.data);
    alert(error?.response?.data || "Không lấy được thông tin tài khoản");
  } finally {
    loading.value = false;
  }
};

watch(
  () => [props.show, props.mode, props.khachHang?.id],
  async ([show]) => {
    if (!show) return;

    await loadVaiTro();

    if (isAddMode.value) {
      resetForm();
    } else {
      await loadTaiKhoanChiTiet();
    }
  },
  { immediate: true },
);

const dong = () => {
  emit("close");
};

const validateForm = () => {
  if (!formTaiKhoan.value.maTaiKhoan?.trim()) {
    alert("Mã tài khoản không được để trống");
    return false;
  }

  if (!formTaiKhoan.value.tenTaiKhoan?.trim()) {
    alert("Tên tài khoản không được để trống");
    return false;
  }

  if (isAddMode.value && !formTaiKhoan.value.matKhau?.trim()) {
    alert("Mật khẩu không được để trống");
    return false;
  }

  if (!formTaiKhoan.value.idVaiTro) {
    alert("Vui lòng chọn vai trò");
    return false;
  }

  return true;
};

const taoPayload = () => {
  const payload = {
    maTaiKhoan: formTaiKhoan.value.maTaiKhoan?.trim(),
    tenTaiKhoan: formTaiKhoan.value.tenTaiKhoan?.trim(),
    email: formTaiKhoan.value.email?.trim() || null,
    soDienThoai: formTaiKhoan.value.soDienThoai?.trim() || null,
    diaChi: formTaiKhoan.value.diaChi?.trim() || null,
    idVaiTro: Number(formTaiKhoan.value.idVaiTro),
    trangThai:
      formTaiKhoan.value.trangThai === true ||
      formTaiKhoan.value.trangThai === "true",
  };

  if (formTaiKhoan.value.matKhau?.trim()) {
    payload.matKhau = formTaiKhoan.value.matKhau.trim();
  }

  return payload;
};

const themTaiKhoan = async () => {
  if (!validateForm()) return;

  try {
    loading.value = true;

    const payload = taoPayload();

    console.log("Payload thêm tài khoản khách hàng:", payload);
    console.log("ID khách hàng:", props.khachHang.id);

    await addTaiKhoanKhachHang(props.khachHang.id, payload);

    alert("Thêm tài khoản thành công");
    emit("updated");
    emit("close");
  } catch (error) {
    console.error("Lỗi thêm tài khoản khách hàng:", error);
    console.error("Status:", error?.response?.status);
    console.error("Response:", error?.response?.data);
    alert(error?.response?.data || "Thêm tài khoản thất bại");
  } finally {
    loading.value = false;
  }
};

const capNhatTaiKhoan = async () => {
  if (!validateForm()) return;

  try {
    loading.value = true;

    const payload = taoPayload();

    console.log("Payload cập nhật tài khoản khách hàng:", payload);
    console.log("ID khách hàng:", props.khachHang.id);

    await updateTaiKhoanKhachHang(props.khachHang.id, payload);

    alert("Cập nhật tài khoản thành công");
    emit("updated");
    emit("close");
  } catch (error) {
    console.error("Lỗi cập nhật tài khoản khách hàng:", error);
    console.error("Status:", error?.response?.status);
    console.error("Response:", error?.response?.data);
    alert(error?.response?.data || "Cập nhật tài khoản thất bại");
  } finally {
    loading.value = false;
  }
};

const xoaTaiKhoan = async () => {
  if (isAddMode.value) return;

  const ok = confirm(
    `Bạn có chắc muốn xóa tài khoản của khách hàng ${props.khachHang?.tenKhachHang || ""}?`,
  );
  if (!ok) return;

  try {
    loading.value = true;

    await deleteTaiKhoanKhachHang(props.khachHang.id);

    alert("Xóa tài khoản thành công");
    emit("updated");
    emit("close");
  } catch (error) {
    console.error("Lỗi xóa tài khoản khách hàng:", error);
    console.error("Status:", error?.response?.status);
    console.error("Response:", error?.response?.data);
    alert(error?.response?.data || "Xóa tài khoản thất bại");
  } finally {
    loading.value = false;
  }
};

const submitForm = async () => {
  if (isAddMode.value) {
    await themTaiKhoan();
  } else {
    await capNhatTaiKhoan();
  }
};
</script>

<template>
  <div v-if="show" class="overlay">
    <div class="modal-box">
      <div class="modal-header">
        <h3>{{ isAddMode ? "Thêm tài khoản" : "Quản lý tài khoản" }}</h3>
        <button class="btn-close" @click="dong">X</button>
      </div>

      <div class="modal-body" v-if="!loading">
        <div class="info-khach-hang">
          <p>
            <strong>Khách hàng:</strong> {{ khachHang?.tenKhachHang || "-" }}
          </p>
          <p><strong>Mã KH:</strong> {{ khachHang?.maKhachHang || "-" }}</p>
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
            <label>Số điện thoại</label>
            <input v-model="formTaiKhoan.soDienThoai" />
          </div>

          <div class="item">
            <label
              >Mật khẩu {{ isAddMode ? "" : "(để trống nếu không đổi)" }}</label
            >
            <input
              type="password"
              v-model="formTaiKhoan.matKhau"
              :placeholder="
                isAddMode ? 'Nhập mật khẩu' : 'Không đổi thì để trống'
              "
            />
          </div>

          <div class="item">
            <label>Vai trò</label>
            <select v-model="formTaiKhoan.idVaiTro">
              <option value="">-- Chọn vai trò --</option>
              <option
                v-for="vt in danhSachVaiTro"
                :key="vt.id"
                :value="Number(vt.id)"
              >
                {{ vt.tenVaiTro }}
              </option>
            </select>
          </div>

          <div class="item">
            <label>Trạng thái</label>
            <select v-model="formTaiKhoan.trangThai">
              <option :value="true">Hoạt động</option>
              <option :value="false">Ngừng hoạt động</option>
            </select>
          </div>

          <div class="item full">
            <label>Địa chỉ</label>
            <textarea v-model="formTaiKhoan.diaChi"></textarea>
          </div>
        </div>
      </div>

      <div v-else class="loading-box">Đang tải dữ liệu...</div>

      <div class="modal-footer">
        <button class="btn-cancel" @click="dong">Đóng</button>

        <button
          v-if="!isAddMode"
          class="btn-delete"
          @click="xoaTaiKhoan"
          :disabled="loading"
        >
          Xóa tài khoản
        </button>

        <button class="btn-save" @click="submitForm" :disabled="loading">
          {{ isAddMode ? "Thêm tài khoản" : "Cập nhật tài khoản" }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}
.modal-box {
  width: 760px;
  max-width: calc(100vw - 40px);
  background: #fff;
  border-radius: 12px;
  padding: 20px;
}
.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 14px;
}
.btn-close {
  border: none;
  background: red;
  color: #fff;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}
.info-khach-hang {
  background: #f5f8ff;
  border: 1px solid #dce7ff;
  border-radius: 8px;
  padding: 10px 12px;
  margin-bottom: 14px;
}
.info-khach-hang p {
  margin: 4px 0;
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
.item select,
.item textarea {
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 9px 10px;
  outline: none;
}
.item textarea {
  min-height: 90px;
  resize: vertical;
}
.full {
  grid-column: 1 / -1;
}
.loading-box {
  padding: 30px 0;
  text-align: center;
  font-size: 15px;
  color: #666;
}
.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 18px;
}
.btn-cancel {
  background: #d9d9d9;
  border: none;
  padding: 9px 14px;
  border-radius: 6px;
  cursor: pointer;
}
.btn-save {
  background: #1677ff;
  color: white;
  border: none;
  padding: 9px 14px;
  border-radius: 6px;
  cursor: pointer;
}
.btn-delete {
  background: #ff4d4f;
  color: white;
  border: none;
  padding: 9px 14px;
  border-radius: 6px;
  cursor: pointer;
}
.btn-save:disabled,
.btn-delete:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
