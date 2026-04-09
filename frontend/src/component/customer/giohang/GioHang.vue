<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router"; // Import cái này vào

const router = useRouter();
// Dữ liệu mẫu chia theo Shop cho giống Shopee
const cartByShop = ref([
  {
    shopName: "Hadupo 1991",
    items: [
      {
        id: 101,
        name: "Áo thun nam nữ NGÔI SAO CHỮ THÁI THÊU & QUẦN HOA SAO...",
        image: "https://via.placeholder.com/80",
        variant: "ÁO NS CHỮ THÁI ĐEN, XL",
        price: 89999,
        quantity: 1,
        selected: false,
      },
    ],
  },
  {
    shopName: "La_store",
    items: [
      {
        id: 201,
        name: "Giày thể thao Sneaker SB Force 58 Light Smoke Burgundy...",
        image: "https://via.placeholder.com/80",
        variant: "SB Mộc Đỏ, 43",
        price: 335000,
        quantity: 1,
        selected: false,
      },
    ],
  },
]);

const goToCheckout = () => {
  // Lọc ra những thằng được tích chọn
  const itemsToPay = cartByShop.value
    .flatMap((shop) => shop.items)
    .filter((i) => i.selected);

  if (itemsToPay.length === 0) {
    alert("Vui lòng chọn ít nhất 1 sản phẩm để thanh toán!");
    return;
  }

  // Lưu tạm vào localStorage để trang ThanhToan.vue có thể lấy ra dùng
  localStorage.setItem("checkout_items", JSON.stringify(itemsToPay));
  localStorage.setItem("checkout_total", totalPrice.value);

  // Chuyển hướng
  router.push("/thanh-toan");
};

const formatPrice = (price) =>
  new Intl.NumberFormat("vi-VN").format(price) + "₫";

// Tính toán tổng số lượng và tiền
const selectedItems = computed(() => {
  return cartByShop.value
    .flatMap((shop) => shop.items)
    .filter((i) => i.selected);
});

const totalPrice = computed(() => {
  return selectedItems.value.reduce((sum, i) => sum + i.price * i.quantity, 0);
});

const isSelectAll = computed({
  get: () => {
    const all = cartByShop.value.flatMap((s) => s.items);
    return all.length > 0 && all.every((i) => i.selected);
  },
  set: (val) => {
    cartByShop.value.forEach((s) => s.items.forEach((i) => (i.selected = val)));
  },
});
//////////////// DONE T34 /////////////
// T35 cập nhất số lượng///

///T36

///T 39 40 41 42
</script>

<template>
  <div class="shopee-cart">
    <div class="container">
      <div class="cart-header shadow-sm">
        <div class="col-check">
          <input type="checkbox" v-model="isSelectAll" />
        </div>
        <div class="col-product">Sản phẩm</div>
        <div class="col-unit-price">Đơn giá</div>
        <div class="col-quantity">Số lượng</div>
        <div class="col-amount">Số tiền</div>
        <div class="col-action">Thao tác</div>
      </div>

      <div
        v-for="shop in cartByShop"
        :key="shop.shopName"
        class="shop-section shadow-sm"
      >
        <div class="shop-header">
          <input type="checkbox" />
          <span class="shop-label">Yêu thích</span>
          <span class="shop-name">{{ shop.shopName }}</span>
          <i class="bi bi-chat-dots text-danger ms-2"></i>
        </div>

        <div v-for="item in shop.items" :key="item.id" class="product-row">
          <div class="col-check">
            <input type="checkbox" v-model="item.selected" />
          </div>
          <div class="col-product">
            <div class="product-content">
              <img :src="item.image" class="p-img" />
              <div class="p-details">
                <p class="p-name">{{ item.name }}</p>
                <div class="p-variant">
                  Phân Loại Hàng: <br />
                  {{ item.variant }} <i class="bi bi-caret-down-fill"></i>
                </div>
              </div>
            </div>
          </div>
          <div class="col-unit-price">{{ formatPrice(item.price) }}</div>
          <div class="col-quantity">
            <div class="q-control">
              <button @click="item.quantity > 1 && item.quantity--">-</button>
              <input type="number" v-model="item.quantity" />
              <button @click="item.quantity++">+</button>
            </div>
          </div>
          <div class="col-amount text-danger">
            {{ formatPrice(item.price * item.quantity) }}
          </div>
          <div class="col-action">
            <p class="mb-0 cursor-pointer">Xóa</p>
            <p class="text-danger mb-0 cursor-pointer small">
              Tìm sản phẩm tương tự
            </p>
          </div>
        </div>
      </div>

      <div class="sticky-bottom-bar shadow">
        <div
          class="container d-flex align-items-center justify-content-between h-100"
        >
          <div class="d-flex align-items-center gap-3">
            <input type="checkbox" v-model="isSelectAll" id="sel-all" />
            <label for="sel-all"
              >Chọn Tất Cả ({{
                cartByShop.flatMap((s) => s.items).length
              }})</label
            >
            <span>Xóa</span>
            <span class="text-danger">Bỏ sản phẩm không hoạt động</span>
          </div>

          <div class="d-flex align-items-center gap-3">
            <div>
              Tổng cộng ({{ selectedItems.length }} sản phẩm):
              <span class="total-text">{{ formatPrice(totalPrice) }}</span>
            </div>
            <button
              class="btn-checkout"
              :disabled="selectedItems.length === 0"
              :style="{
                opacity: selectedItems.length === 0 ? '0.5' : '1',
                cursor: selectedItems.length === 0 ? 'not-allowed' : 'pointer',
              }"
              @click="goToCheckout"
            >
              Mua Hàng
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.shopee-cart {
  background-color: #f5f5f5;
  min-height: 100vh;
  padding-top: 100px; /* Cách cái navbar mày đã làm */
  padding-bottom: 120px;
}

/* Định nghĩa các cột giống hệt table của shopee */
.col-check {
  width: 50px;
  text-align: center;
}
.col-product {
  flex: 1;
}
.col-unit-price {
  width: 120px;
  text-align: center;
}
.col-quantity {
  width: 150px;
  text-align: center;
}
.col-amount {
  width: 120px;
  text-align: center;
}
.col-action {
  width: 150px;
  text-align: center;
  font-size: 14px;
}

.cart-header {
  display: flex;
  background: white;
  padding: 15px 0;
  margin-bottom: 12px;
  border-radius: 3px;
  color: #888;
  font-size: 14px;
}

.shop-section {
  background: white;
  margin-bottom: 15px;
  border-radius: 3px;
}

.shop-header {
  padding: 15px;
  border-bottom: 1px solid #f2f2f2;
  display: flex;
  align-items: center;
  gap: 10px;
}

.shop-label {
  background: #ee4d2d;
  color: white;
  font-size: 10px;
  padding: 2px 4px;
  border-radius: 2px;
}

.product-row {
  display: flex;
  padding: 20px 0;
  align-items: center;
  border-bottom: 1px solid #f2f2f2;
}

.product-content {
  display: flex;
  gap: 10px;
}

.p-img {
  width: 80px;
  height: 80px;
  object-fit: cover;
}
.p-name {
  font-size: 14px;
  line-height: 1.2;
  margin-bottom: 5px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.p-variant {
  font-size: 12px;
  color: #888;
  cursor: pointer;
}

/* Control số lượng */
.q-control {
  display: flex;
  justify-content: center;
}
.q-control button {
  width: 32px;
  height: 32px;
  border: 1px solid #ddd;
  background: white;
}
.q-control input {
  width: 45px;
  height: 32px;
  text-align: center;
  border-top: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
  border-left: none;
  border-right: none;
}

/* Sticky Bar */
.sticky-bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 80px;
  background: white;
  z-index: 1000;
  border-top: 1px dashed #ddd;
}

.total-text {
  color: #ee4d2d;
  font-size: 24px;
  font-weight: 500;
  margin-left: 10px;
}

.btn-checkout {
  background: #ee4d2d;
  color: white;
  border: none;
  padding: 10px 50px;
  font-size: 16px;
  border-radius: 2px;
}

.cursor-pointer {
  cursor: pointer;
}
</style>
