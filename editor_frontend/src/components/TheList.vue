<template>
  <main>
    <ul>
      <ListItem
        v-for="item in items"
        :key="item.id"
        :id="item.id"
        :name="item.name"
      />
    </ul>
  </main>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ListItem from "./ListItem.vue";

export default defineComponent({
  components: {
    ListItem,
  },
  computed: {
    items() {
      return [
        { id: 1, name: "Intel" },
        { id: 2, name: "AMD" },
        { id: 3, name: "Nvidia" },
        { id: 4, name: "Apple" },
        { id: 5, name: "Microsoft" },
        { id: 6, name: "Google" },
      ];
    },
  },
});
</script>

<style lang="scss" scoped>
ul {
  display: flex;
  flex-direction: column;
  margin: 0;
  padding: 0;
  list-style: none;
}
</style>
