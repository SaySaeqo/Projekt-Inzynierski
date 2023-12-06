<template>
  <main>
    <ul>
      <ListItem
        v-for="item in items"
        :key="item.id"
        :id="item.id"
        :name="item.name"
        @remove="remove(item)"
      />
      <button @click="add">ADD</button>
    </ul>
  </main>
</template>

<script lang="ts">
import { defineComponent, onUpdated } from "vue";
import ListItem from "./ListItem.vue";
import { Exhibit } from "@/models/Exhibit";
import dataService from "../services/DataService";

export default defineComponent({
  components: {
    ListItem,
  },
  data() {
    return {
      items: [] as Exhibit[],
    };
  },
  created() {
    this.getExhibit();
  },
  methods: {
    async getExhibit() {
      this.items = await dataService.getAll();
    },
    remove(item: Exhibit) {
      this.items.splice(this.items.indexOf(item), 1);
    },
    add() {
      let exhibit = new Exhibit();
      exhibit.id = "0";
      exhibit.name = "New Exhibit";
      this.items.push(exhibit);
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
