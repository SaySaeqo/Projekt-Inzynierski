<template>
  <div>
    <p>link</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Exhibit } from "@/models/Exhibit";

export default defineComponent({
  props: {
    exhibit: {
      type: Exhibit,
      required: true,
    },
    data: {
      type: String,
      required: true,
    },
  },
});
</script>

<style lang="scss" scoped>
div {
  border: 1px solid black;
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1;
}
</style>
