<template>
  <div class="container">
    <TheNavigation />
    <router-view />
    <footer>
      Credits: <br />
      download icons PNG Designed By 588ku from https://pngtree.com/freepng/cartoon-link-icon-download_4489920.html?sol=downref&id=bef<br />
      <a href="https://www.flaticon.com/free-icons/trash" title="trash icons">Trash icons created by Freepik - Flaticon</a><br />
      <a href="https://www.flaticon.com/free-icons/register" title="register icons">Register icons created by Irfansusanto20 - Flaticon</a>
    </footer>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TheList from "./components/TheList.vue";
import TheNavigation from "./components/TheNavigation.vue";

export default defineComponent({
  components: {
    TheList,
    TheNavigation,
  },
});
</script>

<style lang="scss" scoped>
.container {
  flex: 1;
  display: flex;
  flex-direction: column;
}
main {
  flex: 1;
}
</style>
