<template>
  <main>
    <ul>
      <li>
        <label for="search">Search:</label>
        <input id="search" v-model="search" type="text" placeholder="Search exhibitions..." />
      </li>
      <ListItem
        v-for="item in filteredItems"
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
import { defineComponent } from "vue";
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
      search: "",
    };
  },
  computed: {
    filteredItems(): Exhibit[] {
      if (!this.search) {
        return this.items;
      }
      return this.items.filter((item) =>
        item.name.toLowerCase().includes(this.search.toLowerCase())
      );
    },
  },
  beforeMount() {
    this.getExhibit();
  },
  methods: {
    async getExhibit() {
      this.items = await dataService.getAll();
    },
    remove(item: Exhibit) {
      dataService.delete(item.id);
      this.items.splice(this.items.indexOf(item), 1);
    },
    async add() {
      const id = await dataService.createEmpty();
      let exhibit = new Exhibit();
      exhibit.id = id;
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

li {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
#search {
  padding: 1em;
  font-size: 1em;
  flex: 1;
}
label[for="search"] {
  padding: 0 1.5em;
  cursor: pointer;
}
</style>
