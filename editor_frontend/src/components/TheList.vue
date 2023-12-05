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
import { query, collection, getDocs, getFirestore } from "firebase/firestore";
import db from "../initfirestore";
import { Exhibit } from "@/models/Exhibit";

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
      let db1 = getFirestore(db);
      const querySnap = await getDocs(query(collection(db1, "exhibits")));

      // add each doc to 'countries' array
      querySnap.forEach((doc) => {
        let exhibit = new Exhibit();
        exhibit.id = doc.id;
        exhibit.name = doc.data().name;
        this.items.push(exhibit);
      });
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
