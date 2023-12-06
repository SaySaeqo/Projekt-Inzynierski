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
import { defineComponent, onUpdated } from "vue";
import ListItem from "./ListItem.vue";
import { query, collection, getDocs, getFirestore } from "firebase/firestore";
import db from "../initfirestore.js";
import dataService from "../services/DataService.js";

export default defineComponent({
  components: {
    ListItem,
  },
  data() {
    return {
      items: [],
    };
  },
  created() {
    this.getExhibit();
  },
  methods: {
    async getExhibit() {
      this.items = await dataService.getAll();
    },
  },
  /*computed: {
    items() {
      return [
        { id: "1", name: "Intel" },
        { id: 2, name: "AMD" },
        { id: 3, name: "Nvidia" },
        { id: 4, name: "Apple" },
        { id: 5, name: "Microsoft" },
        { id: 6, name: "Google" }
      ];
    },
  },*/
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
