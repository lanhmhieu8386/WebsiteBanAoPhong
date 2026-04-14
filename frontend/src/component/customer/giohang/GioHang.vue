<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { deleteGioHang, hienThiGioHang, updateGioHang } from "@/api/customer/gioHangApi";
import aoPhong1 from "@/assets/images/aophong1.jpg";

const router = useRouter();
const cartItems = ref([]);
const loading = ref(false);

const formatPrice = (price) =>
  new Intl.NumberFormat("vi-VN").format(price) + "₫";

const getImageUrl = (imageName) => {
  if (!imageName) return aoPhong1;
  if (imageName.startsWith("http")) return imageName;
  try {
    return new URL(`../assets/images/${imageName}`, import.meta.url).href;
  } catch (e) {
    return aoPhong1;
  }
};

const fetchCart = async () => {
  loading.value = true;
  try {
    const res = await hienThiGioHang();

    cartItems.value = res.data.items.map((item) => ({
      ...item,
      selected: false,
    }));

    localStorage.setItem("cartCount", cartItems.value.length);
    window.dispatchEvent(new Event("update-cart"));
  } catch (err) {
    console.error("Lỗi load giỏ hàng:", err);
  } finally {
    loading.value = false;
  }
};
const handleUpdateQuantity = async (item, change) => {
  const newQuantity = item.soLuong + change;
  if (newQuantity < 0) return;

  try {
    const res = await updateGioHang({
      idSanPhamChiTiet: item.idSanPhamChiTiet,
      soLuong: newQuantity,
    });

    cartItems.value = res.data.items.map((i) => ({
      ...i,
      selected: item.selected, 
    }));
  } catch (err) {
    console.error(err);
  }
};

const removeItem = async (id) => {
  if (!confirm("Xóa sản phẩm này?")) return;

  try {
    const res = await deleteGioHang(id);

   
    cartItems.value = res.data.items.map((i) => ({
      ...i,
      selected: false,
    }));
  } catch (err) {
    console.error(err);
  }
};

const selectedItems = computed(() => cartItems.value.filter((i) => i.selected));
const totalPrice = computed(() =>
  selectedItems.value.reduce((sum, i) => sum + i.gia * i.soLuong, 0),
);
const isSelectAll = computed({
  get: () =>
    cartItems.value.length > 0 && cartItems.value.every((i) => i.selected),
  set: (val) => cartItems.value.forEach((i) => (i.selected = val)),
});

const goToCheckout = () => {
  if (selectedItems.value.length === 0) return alert("Chọn sản phẩm đã nhé!");
  localStorage.setItem("checkout_items", JSON.stringify(selectedItems.value));
  localStorage.setItem("checkout_total", totalPrice.value);
  router.push("/thanh-toan");
};

onMounted(() => {
  fetchCart();
});
</script>

<template>
  <div class="cart-page">
    <div class="container py-5">
      <div class="header-section text-center mb-5">
        <span class="eyebrow">Your Selection</span>
        <h1 class="zen-title">Shopping Bag</h1>
        <div class="zen-line"></div>
      </div>

      <div class="cart-table">
        <div class="table-head shadow-sm">
          <div class="c-check">
            <input type="checkbox" v-model="isSelectAll" class="zen-cb" />
          </div>
          <div class="c-info">Sản phẩm</div>
          <div class="c-price">Đơn giá</div>
          <div class="c-qty">Số lượng</div>
          <div class="c-sub">Tổng</div>
          <div class="c-action"></div>
        </div>

        <div v-if="cartItems.length > 0" class="table-body">
          <div
            v-for="item in cartItems"
            :key="item.id"
            class="cart-row shadow-sm"
          >
            <div class="c-check">
              <input type="checkbox" v-model="item.selected" class="zen-cb" />
            </div>
            <div class="c-info">
              <div class="p-card">
                <img :src="getImageUrl(item.hinhAnh)" class="p-img" />
                <div class="p-txt">
                  <p class="p-name">{{ item.tenSanPham }}</p>
                  <p class="p-var">
                    {{ item.tenMauSac }} / {{ item.tenKichCo }}
                  </p>
                </div>
              </div>
            </div>
            <div class="c-price">{{ formatPrice(item.gia) }}</div>
            <div class="c-qty">
              <div class="qty-box">
                <button @click="handleUpdateQuantity(item, -1)">-</button>
                <input type="number" v-model="item.soLuong" readonly />
                <button @click="handleUpdateQuantity(item, 1)">+</button>
              </div>
            </div>
            <div class="c-sub fw-bold">
              {{ formatPrice(item.gia * item.soLuong) }}
            </div>
            <div class="c-action">
              <button class="btn-del"@click="removeItem(item.idSanPhamChiTiet)"">
                <i class="bi bi-x-lg"></i>
              </button>
            </div>
          </div>
        </div>

        <div v-else class="empty-msg text-center py-5">
          <p class="text-muted">Chưa có món nào trong túi đồ của bạn.</p>
          <router-link to="/san-pham" class="btn btn-dark btn-shop"
            >Mua sắm ngay</router-link
          >
        </div>
      </div>

      <div class="cart-footer shadow-lg">
        <div
          class="container d-flex align-items-center justify-content-between h-100"
        >
          <div class="f-left d-flex align-items-center gap-3">
            <input
              type="checkbox"
              v-model="isSelectAll"
              id="all-f"
              class="zen-cb"
            />
            <label for="all-f" class="mb-0"
              >Chọn tất cả ({{ cartItems.length }})</label
            >
          </div>
          <div class="f-right d-flex align-items-center gap-5">
            <div class="total-wrap">
              <span class="t-label">Tổng cộng:</span>
              <span class="t-amount">{{ formatPrice(totalPrice) }}</span>
            </div>
            <button
              class="btn-checkout"
              :disabled="selectedItems.length === 0"
              @click="goToCheckout"
            >
              Thanh toán
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
@import url("https://fonts.googleapis.com/css2?family=Syncopate:wght@700&family=Plus+Jakarta+Sans:wght@300;400;600;700&display=swap");

.cart-page {
  background: #fcfcfc;
  font-family: "Plus Jakarta Sans", sans-serif;
  min-height: 100vh;
  padding-top: 60px;
  color: #1a1a1a;
}

.eyebrow {
  font-size: 0.7rem;
  text-transform: uppercase;
  letter-spacing: 4px;
  color: #999;
}
.zen-title {
  font-family: "Syncopate", sans-serif;
  font-size: 1.8rem;
  text-transform: uppercase;
  margin-top: 10px;
}
.zen-line {
  width: 30px;
  height: 2px;
  background: #000;
  margin: 15px auto;
}

/* TABLE CSS */
.table-head,
.cart-row {
  display: flex;
  align-items: center;
  background: #fff;
  padding: 15px 25px;
  border-radius: 2px;
  margin-bottom: 12px;
}
.table-head {
  font-size: 0.7rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: #aaa;
}

.c-check {
  width: 40px;
}
.c-info {
  flex: 2;
}
.c-price,
.c-qty,
.c-sub {
  width: 140px;
  text-align: center;
}
.c-action {
  width: 60px;
  text-align: right;
}

/* PRODUCT CELL */
.p-card {
  display: flex;
  align-items: center;
  gap: 20px;
}
.p-img {
  width: 70px;
  height: 90px;
  object-fit: cover;
  background: #f5f5f5;
}
.p-name {
  font-size: 0.85rem;
  font-weight: 700;
  margin: 0;
  text-transform: uppercase;
}
.p-var {
  font-size: 0.7rem;
  color: #888;
  margin: 0;
}

/* QTY BOX */
.qty-box {
  display: inline-flex;
  border: 1px solid #eee;
  border-radius: 2px;
}
.qty-box button {
  width: 30px;
  height: 30px;
  border: none;
  background: #fff;
  font-size: 0.8rem;
}
.qty-box input {
  width: 40px;
  border: none;
  text-align: center;
  font-size: 0.85rem;
  font-weight: 600;
}

/* STICKY FOOTER */
.cart-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 90px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-top: 1px solid #eee;
  z-index: 1000;
}
.t-label {
  font-size: 0.75rem;
  color: #888;
  display: block;
  line-height: 1;
}
.t-amount {
  font-size: 1.6rem;
  font-weight: 800;
}

.btn-checkout {
  background: #000;
  color: #fff;
  border: none;
  padding: 15px 40px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 2px;
  transition: 0.3s;
}
.btn-checkout:disabled {
  background: #eee;
  color: #aaa;
  cursor: not-allowed;
}
.btn-checkout:hover:not(:disabled) {
  background: #333;
  transform: translateY(-2px);
}

.zen-cb {
  accent-color: #000;
  width: 18px;
  height: 18px;
  cursor: pointer;
}
.btn-del {
  background: none;
  border: none;
  color: #ccc;
  transition: 0.2s;
}
.btn-del:hover {
  color: #000;
}
.btn-shop {
  padding: 12px 30px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 1px;
  border-radius: 0;
}
</style>
