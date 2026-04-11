<script setup>
import { ref, computed } from "vue";
import router from "@/router";

const address = ref({
  name: "Hiếu Hiếu",
  phone: "(+84) 332 654 198",
  detail:
    "Số 405, Ngõ 68 Đường Phú Diễn, Phường Phú Diễn, Quận Bắc Từ Liêm, Hà Nội",
});

const cartItems = ref([
  {
    id: 101,
    shop: "Hadupo 1991",
    name: "Áo thun nam nữ NGÔI SAO CHỮ THÁI THÊU",
    variant: "ÁO NS CHỮ THÁI",
    price: 89999,
    quantity: 1,
    image: "https://via.placeholder.com/50",
  },
]);

const shippingFee = ref(28700);
const voucherDiscount = ref(0);

const productTotal = computed(() => {
  return cartItems.value.reduce(
    (total, item) => total + item.price * item.quantity,
    0,
  );
});

const finalTotal = computed(() => {
  return productTotal.value + shippingFee.value - voucherDiscount.value;
});

const formatPrice = (price) =>
  new Intl.NumberFormat("vi-VN").format(price) + "₫";

const placeOrder = () => {
  alert("Đặt hàng thành công!");
  router.push("/");
};
</script>

<template>
  <div class="checkout-page">
    <div class="container">
      <div class="checkout-section address-section shadow-sm">
        <div class="address-border"></div>
        <div class="p-4">
          <div class="d-flex align-items-center text-danger mb-2">
            <i class="bi bi-geo-alt-fill me-2"></i>
            <span class="fw-bold">Địa Chỉ Nhận Hàng</span>
          </div>
          <div class="d-flex align-items-center gap-3">
            <span class="fw-bold">{{ address.name }} {{ address.phone }}</span>
            <span class="address-text">{{ address.detail }}</span>
            <span class="badge-default ms-2">Mặc định</span>
            <button class="btn-change ms-auto">Thay Đổi</button>
          </div>
        </div>
      </div>

      <div class="checkout-section shadow-sm">
        <div class="p-header p-3 d-flex border-bottom">
          <div class="col-6 fw-bold">Sản phẩm</div>
          <div class="col-2 text-muted text-center">Đơn giá</div>
          <div class="col-2 text-muted text-center">Số lượng</div>
          <div class="col-2 text-muted text-center">Thành tiền</div>
        </div>

        <div
          v-for="item in cartItems"
          :key="item.id"
          class="p-item p-3 d-flex align-items-center"
        >
          <div class="col-6 d-flex align-items-center gap-2">
            <img :src="item.image" class="checkout-img" />
            <div>
              <p class="mb-0 text-truncate" style="max-width: 300px">
                {{ item.name }}
              </p>
              <small class="text-muted">Phân loại: {{ item.variant }}</small>
            </div>
          </div>
          <div class="col-2 text-center">{{ formatPrice(item.price) }}</div>
          <div class="col-2 text-center">{{ item.quantity }}</div>
          <div class="col-2 text-center">
            {{ formatPrice(item.price * item.quantity) }}
          </div>
        </div>

        <div class="shipping-area p-3 border-top d-flex">
          <div class="col-5 border-end pe-4">
            <div class="d-flex align-items-center gap-2">
              <label class="small">Lời nhắn:</label>
              <input
                type="text"
                class="form-control form-control-sm"
                placeholder="Lưu ý cho người bán..."
              />
            </div>
          </div>
          <div
            class="col-7 ps-4 d-flex justify-content-between align-items-center"
          >
            <div class="d-flex flex-column">
              <span class="text-success small fw-bold"
                >Phương thức vận chuyển:</span
              >
              <span class="small">Nhanh</span>
              <small class="text-muted"
                >Nhận hàng vào 28 Tháng 3 - 31 Tháng 3</small
              >
            </div>
            <div class="text-end">
              <button class="btn-change-ship small">THAY ĐỔI</button>
              <p class="mb-0">{{ formatPrice(shippingFee) }}</p>
            </div>
          </div>
        </div>
        <div class="total-line p-3 text-end border-top">
          <span class="text-muted small"
            >Tổng số tiền ({{ cartItems.length }} sản phẩm):</span
          >
          <span class="total-shop-price ms-3">{{
            formatPrice(productTotal + shippingFee)
          }}</span>
        </div>
      </div>

      <div class="checkout-section shadow-sm p-4">
        <div
          class="d-flex justify-content-between align-items-center mb-4 pb-3 border-bottom"
        >
          <div class="d-flex align-items-center gap-2">
            <i class="bi bi-ticket-perforated text-danger"></i>
            <span>Shopee Voucher</span>
          </div>
          <button class="btn-link-shopee">Chọn Voucher</button>
        </div>

        <div class="payment-method mb-4">
          <div class="d-flex justify-content-between mb-3">
            <span class="fw-bold fs-5">Phương thức thanh toán</span>
            <span class="text-uppercase">Thanh toán khi nhận hàng</span>
            <button class="btn-change">THAY ĐỔI</button>
          </div>
          <div class="text-end summary-final">
            <div class="d-flex justify-content-end mb-2">
              <span class="text-muted">Tổng tiền hàng</span>
              <span class="val">{{ formatPrice(productTotal) }}</span>
            </div>
            <div class="d-flex justify-content-end mb-2">
              <span class="text-muted">Tổng cộng phí vận chuyển</span>
              <span class="val">{{ formatPrice(shippingFee) }}</span>
            </div>
            <div class="d-flex justify-content-end mb-3">
              <span class="text-muted">Tổng thanh toán</span>
              <span class="final-val">{{ formatPrice(finalTotal) }}</span>
            </div>
          </div>
        </div>

        <div class="d-flex flex-column align-items-end border-top pt-4">
          <p class="small text-muted mb-3">
            Nhấn "Đặt hàng" đồng nghĩa với việc bạn đồng ý tuân theo Điều khoản
            Shopee
          </p>
          <button class="btn-order" @click="placeOrder">Đặt hàng</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.checkout-page {
  background-color: #f5f5f5;
  padding-top: 100px;
  padding-bottom: 50px;
  min-height: 100vh;
}

.checkout-section {
  background: white;
  margin-bottom: 15px;
  border-radius: 3px;
  overflow: hidden;
}

/* Phần sọc địa chỉ */
.address-border {
  height: 3px;
  width: 100%;
  background-image: repeating-linear-gradient(
    45deg,
    #6fa6d6,
    #6fa6d6 33px,
    transparent 0,
    transparent 41px,
    #f18d9b 0,
    #f18d9b 74px,
    transparent 0,
    transparent 82px
  );
}

.address-text {
  color: #333;
  font-size: 14px;
}

.badge-default {
  border: 1px solid #ee4d2d;
  color: #ee4d2d;
  font-size: 10px;
  padding: 1px 4px;
}

.btn-change,
.btn-change-ship {
  color: #05a;
  background: none;
  border: none;
  font-size: 14px;
}

.checkout-img {
  width: 50px;
  height: 50px;
  object-fit: cover;
}

.p-item {
  font-size: 14px;
}

.total-shop-price {
  color: #ee4d2d;
  font-size: 18px;
  font-weight: 500;
}

.btn-link-shopee {
  color: #05a;
  background: none;
  border: none;
}

.summary-final .val {
  width: 200px;
  color: #888;
}

.final-val {
  width: 200px;
  color: #ee4d2d;
  font-size: 28px;
}

.btn-order {
  background: #ee4d2d;
  color: white;
  border: none;
  padding: 12px 60px;
  font-size: 18px;
  border-radius: 2px;
  font-weight: 500;
}

.btn-order:hover {
  opacity: 0.9;
}
</style>
