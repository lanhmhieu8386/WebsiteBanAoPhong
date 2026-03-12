<template>
  <Header />

  <section class="hero"></section>

  <router-view />

  <Footer />
</template>

<script setup>
import Header from "@/component/customer/GioiThieu/Header.vue";
import Footer from "@/component/customer/GioiThieu/Footer.vue";
</script>

<style scoped>
.hero {
  margin-top: 60px;
}
body {
  padding-top: 70px; /* hoặc 72–80px tùy navbar */
}
</style>
