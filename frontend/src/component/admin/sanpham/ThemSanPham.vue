<script setup>
import { ref } from "vue";

const emit = defineEmits(["close", "save"]);

const tenSanPham = ref("");
const danhMuc = ref("");
const moTa = ref("");

const bienThe = ref([{ size: "", mau: "", gia: "", soLuong: "" }]);

const themBienThe = () => {
  bienThe.value.push({
    size: "",
    mau: "",
    gia: "",
    soLuong: "",
  });
};

const xoaBienThe = (index) => {
  bienThe.value.splice(index, 1);
};

const luu = () => {
  emit("save", {
    tenSanPham: tenSanPham.value,
    danhMuc: danhMuc.value,
    moTa: moTa.value,
    bienThe: bienThe.value,
  });
};
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-box">
      <h2>Thêm sản phẩm</h2>

      <!-- tên -->
      <div class="form-group">
        <label>Tên sản phẩm</label>
        <input v-model="tenSanPham" />
      </div>

      <!-- danh mục -->
      <div class="form-group">
        <label>Danh mục</label>

        <div class="row">
          <select v-model="danhMuc">
            <option>Thời trang</option>
            <option>Giày dép</option>
          </select>

          <button type="button" class="btn-add-small">+</button>
        </div>
      </div>

      <!-- mô tả -->
      <div class="form-group">
        <label>Mô tả</label>
        <textarea v-model="moTa"></textarea>
      </div>

      <!-- button -->
      <div class="modal-actions">
        <button class="btn-cancel" @click="$emit('close')">Hủy</button>

        <button class="btn-save" @click="luu">thêm</button>
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
  width: 700px;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.form-group {
  margin-bottom: 16px;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  box-sizing: border-box;
  border-radius: 8px;
  border: 1px solid #ddd;
  padding: 10px 12px;
  font-size: 14px;
}

.row select {
  width: 100%;
}

.btn-add-small {
  width: 40px;
  height: 40px;
  border: none;
  background: #3b6cff;
  color: white;
  border-radius: 8px;
  font-size: 18px;
  cursor: pointer;
}

.modal-actions {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.btn-cancel {
  background: #e5e5e5;
  border: none;
  padding: 10px 20px;
  border-radius: 10px;
  cursor: pointer;
}

.btn-save {
  background: #3b6cff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 10px;
  cursor: pointer;
}

.row {
  display: grid;
  grid-template-columns: 1fr 40px;
  align-items: center;
  gap: 10px;
}
</style>
