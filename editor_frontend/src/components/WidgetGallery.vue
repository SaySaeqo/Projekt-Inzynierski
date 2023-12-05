<template>
  <div>
    <p>gallery</p>
  </div>
</template>

<script lang="ts">
import { Exhibit } from "@/models/Exhibit";
import { defineComponent } from "vue";

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
  height: 20em;
  border: 1px solid black;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
