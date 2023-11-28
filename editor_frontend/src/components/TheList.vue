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

<script>
import { defineComponent } from "vue";
import ListItem from "./ListItem.vue";
import DataService from "../services/DataService";

export default defineComponent({
  components: {
    ListItem,
  },
  computed: {
    items() {
      let _exhibits = [];
      DataService.getAll()
        .orderBy("name", "asc")
        .forEach((exhibit) =>
          _exhibits.push({ id: exhibit.id, name: exhibit.name })
        );

      return _exhibits;
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
