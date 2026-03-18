<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getChiTietBySanPhamId } from "@/api/admin/sanphamchitiet/sanPhamChiTietApi";
import { getAllDanhMuc } from "@/api/admin/danhmuc/danhMucApi";
import { getAllThuongHieu } from "@/api/admin/thuonghieu/ThuongHieuApi";
import { getAllChatLieu } from "@/api/admin/chatlieu/ChatLieuApi";
import { updateSanPham, getSanPhamById } from "@/api/admin/sanpham/sanPhamApi";

const showEdit = ref(false);
const editIndex = ref(null);

const router = useRouter();
const quayLai = () => {
  router.push("/admin/san-pham");
  // hoặc router.back() nếu muốn quay lại trang trước
};

const route = useRoute();

const sanPham = ref(null);
const chiTietList = ref([]);
const danhMucList = ref([]);
const thuongHieuList = ref([]);
const chatLieuList = ref([]);

const loadSanPham = async () => {
  try {
    const id = parseInt(route.params.id);
    if (!id) return;

    // 👉 gọi API sản phẩm
    const spRes = await getSanPhamById(id);
    const spData = spRes.data?.data || spRes.data;

    sanPham.value = {
      id: spData.id,
      maSanPham: spData.maSanPham,
      ten: spData.tenSanPham,
      idDanhMuc: spData.idDanhMuc || spData.danhMuc?.id,
      idThuongHieu: spData.idThuongHieu || spData.thuongHieu?.id,
      idChatLieu: spData.idChatLieu || spData.chatLieu?.id,
      moTa: spData.moTa,
      trangThai: spData.trangThai,
    };

    // 👉 gọi API chi tiết
    const res = await getChiTietBySanPhamId(id);
    const data = res.data?.data || res.data || [];

    chiTietList.value = data.map((ct) => ({
      id: ct.id,
      mau: ct.tenMauSac,
      size: ct.tenKichCo,
      gia: ct.gia,
      soLuong: ct.soLuong,
      hinh: ct.urlAnh || "https://via.placeholder.com/60",
    }));
  } catch (error) {
    console.error("Lỗi load:", error);
  }
};

const addVariant = () => {
  editIndex.value = null; // phân biệt add vs edit

  formEdit.value = {
    mau: "",
    size: "",
    gia: 0,
    soLuong: 0,
    hinh: "https://via.placeholder.com/60",
  };

  showEdit.value = true;
};

const editVariant = (index) => {
  editIndex.value = index;

  const ct = chiTietList.value[index];

  formEdit.value = { ...ct };

  showEdit.value = true;
};

const saveEdit = async () => {
  try {
    const data = {
      mauSac: formEdit.value.tenMauSac,
      kichCo: formEdit.value.tenKichCo,
      gia: formEdit.value.gia,
      soLuong: formEdit.value.soLuong,
      urlAnh: formEdit.value.hinh,
      sanPhamId: sanPham.value.id,
    };

    if (editIndex.value !== null) {
      // 👉 UPDATE
      const id = chiTietList.value[editIndex.value].id;

      await updateSanPhamChiTiet(id, data);
    } else {
      // 👉 ADD
      await addSanPhamChiTiet(data);
    }

    await loadSanPham(); // reload lại data
    showEdit.value = false;
  } catch (error) {
    console.error(error);
    alert("Lỗi khi lưu biến thể");
  }
};

const onFileChange = (e) => {
  const file = e.target.files[0];

  if (file) {
    formEdit.value.hinh = URL.createObjectURL(file);
  }
};

const saveSanPham = async () => {
  try {
    if (
      !sanPham.value.ten ||
      !sanPham.value.idDanhMuc ||
      !sanPham.value.idThuongHieu ||
      !sanPham.value.idChatLieu
    ) {
      alert("Vui lòng nhập đầy đủ thông tin");
      return;
    }

    const data = {
      maSanPham: sanPham.value.maSanPham,
      tenSanPham: sanPham.value.ten,
      idDanhMuc: Number(sanPham.value.idDanhMuc),
      idThuongHieu: Number(sanPham.value.idThuongHieu),
      idChatLieu: Number(sanPham.value.idChatLieu),
      moTa: sanPham.value.moTa,
      trangThai: sanPham.value.trangThai ?? true,
    };

    await updateSanPham(sanPham.value.id, data);

    alert("Cập nhật thành công");

    // 👉 reload lại data cho chắc
    await loadSanPham();
  } catch (error) {
    console.error(error);
    alert("Lỗi cập nhật");
  }
};

const formEdit = ref({
  mau: "",
  size: "",
  gia: 0,
  soLuong: 0,
  hinh: "",
});

const loadDanhMuc = async () => {
  try {
    const res = await getAllDanhMuc();
    danhMucList.value = res.data || [];
  } catch (e) {
    console.error("Lỗi danh mục", e);
  }
};

const loadThuongHieu = async () => {
  try {
    const res = await getAllThuongHieu();
    thuongHieuList.value = res.data || [];
  } catch (e) {
    console.error("Lỗi thương hiệu", e);
  }
};

const loadChatLieu = async () => {
  try {
    const res = await getAllChatLieu();
    chatLieuList.value = res.data || [];
  } catch (e) {
    console.error("Lỗi chất liệu", e);
  }
};

const themDanhMuc = () => {
  alert("Thêm danh mục");
};

onMounted(async () => {
  await loadDanhMuc();
  await loadThuongHieu();
  await loadChatLieu();
  await loadSanPham();
});
</script>

<template>
  <div class="box" v-if="sanPham">
    <div class="header">
      <h2>Chi tiết sản phẩm</h2>
      <button class="btn-back" @click="quayLai">← Quay lại</button>
    </div>

    <div class="info">
      <div class="form-group">
        <label>Tên sản phẩm</label>
        <input v-model="sanPham.ten" />
      </div>

      <div class="form-group">
        <label>Danh mục</label>

        <div class="select-add">
          <select v-model="sanPham.idDanhMuc">
            <option value="">Chọn danh mục</option>
            <option v-for="dm in danhMucList" :key="dm.id" :value="dm.id">
              {{ dm.tenDanhMuc }}
            </option>
          </select>

          <button class="btn-add-mini" @click="themDanhMuc">+</button>
        </div>
      </div>

      <div class="form-group">
        <label>Chất liệu</label>

        <select v-model="sanPham.idChatLieu">
          <option value="">Chọn chất liệu</option>
          <option v-for="cl in chatLieuList" :key="cl.id" :value="cl.id">
            {{ cl.tenChatLieu }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Thương hiệu</label>

        <select v-model="sanPham.idThuongHieu">
          <option value="">Chọn thương hiệu</option>
          <option v-for="th in thuongHieuList" :key="th.id" :value="th.id">
            {{ th.tenThuongHieu }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Mô tả</label>
        <textarea v-model="sanPham.moTa"></textarea>
      </div>

      <button class="btn-save" @click="saveSanPham">Lưu sản phẩm</button>
    </div>

    <div class="variant-header">
      <h3>Biến thể sản phẩm</h3>

      <button class="btn-save" @click="addVariant">+ Thêm biến thể</button>
    </div>

    <table class="table">
      <thead>
        <tr>
          <th>Ảnh</th>
          <th>Màu</th>
          <th>Size</th>
          <th>Giá</th>
          <th>Số lượng</th>
          <th>Thao tác</th>
        </tr>
      </thead>

      <tbody v-if="chiTietList.length > 0">
        <tr v-for="(ct, index) in chiTietList" :key="ct.id">
          <td>
            <img :src="ct.hinh" class="img" />
          </td>

          <td>{{ ct.mau }}</td>

          <td>{{ ct.size }}</td>

          <td>{{ ct.gia ? Number(ct.gia).toLocaleString() + " đ" : "-" }}</td>

          <td>{{ ct.soLuong }}</td>

          <td>
            <button class="btn-edit" @click="editVariant(index)">Sửa</button>
          </td>
        </tr>
      </tbody>
      <tbody v-else>
        <tr>
          <td colspan="6">Chưa có biến thể sản phẩm</td>
        </tr>
      </tbody>
    </table>

    <div v-if="showEdit" class="modal-overlay">
      <div class="modal-box">
        <h3>Sửa biến thể sản phẩm</h3>
        <div class="modal-form">
          <!-- ẢNH -->
          <div class="img-upload">
            <img :src="formEdit.hinh" class="preview" />
            <input type="file" @change="onFileChange" />
          </div>

          <!-- MÀU -->
          <div class="form-row">
            <label>Màu</label>

            <div class="select-add">
              <select v-model="formEdit.mau">
                <option>Đỏ</option>
                <option>Đen</option>
                <option>Trắng</option>
              </select>

              <button class="btn-add-mini">+</button>
            </div>
          </div>

          <!-- SIZE -->
          <div class="form-row">
            <label>Size</label>

            <div class="select-add">
              <select v-model="formEdit.size">
                <option>S</option>
                <option>M</option>
                <option>L</option>
              </select>

              <button class="btn-add-mini">+</button>
            </div>
          </div>

          <!-- GIÁ -->
          <div class="form-row">
            <label>Giá</label>
            <input type="number" v-model="formEdit.gia" />
          </div>

          <!-- SỐ LƯỢNG -->
          <div class="form-row">
            <label>Số lượng</label>
            <input type="number" v-model="formEdit.soLuong" />
          </div>
          <div class="modal-actions">
            <button class="btn-cancel" @click="showEdit = false">Hủy</button>
            <button class="btn-save" @click="saveEdit">Lưu</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.box {
  background: white;
  padding: 20px;
  border-radius: 10px;
}

.info p {
  margin: 6px 0;
}

.table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.table th {
  background: #e9f2fb;
  padding: 10px;
}

.table td {
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.img {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 6px;
}

.btn-edit {
  background: #f4f6f8;
  border: none;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}

/* input nhỏ lại */
.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  max-width: 500px;
  height: 36px;
  border-radius: 6px;
  border: 1px solid #ddd;
  padding: 5px 10px;
}

/* textarea */
textarea {
  height: 80px;
}

.info {
  max-width: 500px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 14px;
}
/* textarea */
textarea {
  height: 80px;
}

/* save */
.btn-save {
  background: #3b6cff;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  cursor: pointer;
}
h3 {
  margin-top: 20px;
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
  width: 450px;
}

.modal-form input {
  height: 36px;
  padding: 5px 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
}

.img-upload img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  margin-bottom: 5px;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 15px;
  align-items: center;
}

.btn-cancel {
  background: #ddd;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
}
.modal-form {
  display: flex;
  flex-direction: column;
  gap: 12px;
  width: 320px;
}

.form-row {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.form-row input,
.form-row select {
  height: 36px;
  border-radius: 6px;
  border: 1px solid #ddd;
  padding: 5px 10px;
}

.select-add {
  display: flex;
  gap: 6px;
}

.btn-add-mini {
  width: 36px;
  height: 36px;
  border: none;
  border-radius: 6px;
  background: #3b6cff;
  color: white;
  font-size: 18px;
  cursor: pointer;
}

.img-upload {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 10px;
}

.preview {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 6px;
  margin-bottom: 6px;
}

.modal-box {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 350px;
}
.table th,
.table td {
  text-align: center;
}

.table th {
  font-weight: 600;
}
.btn-edit:hover {
  background: #e2e6ea;
}
.modal-form input,
.modal-form select {
  width: 100%;
}
.btn-cancel,
.btn-save {
  padding: 8px 16px;
  border-radius: 8px;
  border: none;
  cursor: pointer;
}

.btn-cancel {
  background: #ddd;
}

.btn-save {
  background: #3b6cff;
  color: white;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.btn-back {
  background: #3b6cff;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
}

.btn-back:hover {
  background: #2f57d9;
}
.variant-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}
</style>
