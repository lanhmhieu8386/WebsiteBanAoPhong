<script setup>
import { ref } from "vue";

const emit = defineEmits(["close", "save"]);

const ma = ref("");
const ten = ref("");
const moTa = ref("");
const trangThai = ref("Hoạt động");

const luu = () => {
  emit("save", {
    ma: ma.value,
    ten: ten.value,
    moTa: moTa.value,
    trangThai: trangThai.value,
  });
};
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-box">
      <h3>Thêm danh mục</h3>

      <div class="form-group">
        <label>Mã danh mục</label>
        <input v-model="ma" />
      </div>

      <div class="form-group">
        <label>Tên danh mục</label>
        <input v-model="ten" />
      </div>

      <div class="form-group">
        <label>Mô tả</label>
        <textarea v-model="moTa"></textarea>
      </div>

      <div class="modal-actions">
        <button class="btn-cancel" @click="$emit('close')">Hủy</button>
        <button class="btn-save" @click="luu">Lưu</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-box {
  background: white;
  width: 420px;
  padding: 20px;
  border-radius: 10px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-bottom: 12px;
}

.form-group input,
.form-group textarea,
.form-group select {
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 8px;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn-cancel {
  background: #ddd;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
}

.btn-save {
  background: #3b6cff;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
}
</style>
