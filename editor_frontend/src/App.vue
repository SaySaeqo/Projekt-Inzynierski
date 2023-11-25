<template>
  <div class="container">
    <TheNavigation />
    <router-view />
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
