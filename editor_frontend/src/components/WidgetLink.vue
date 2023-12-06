<template>
  <div>

    <img :src="icon" />
    <p>{{ title}}</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Exhibit } from "@/models/Exhibit";
import TheNavigation from "./TheNavigation.vue";

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
  data() {
    return {
      icon: "",
      title: "",
      link: "",
    };
  },
  beforeMount() {
    // get url from firebase
    this.icon = "img/exampleLink.png";
    // get title from firebase
    this.title = "Link";
    // get link from firebase
    this.link = "https://www.google.com";
  },
});
</script>

<style lang="scss" scoped>
div {
  border: 1px solid black;
  display: flex;
  justify-content: stretch;
  align-items: center;
  height: 4em;
  flex: 1;
}
img {
  justify-self: flex-start;
  object-fit: contain;
  height: 3em;
  padding: 1em;
}
p {
  display: flex;
  align-items: center;
  flex: 1;
  font-size: 1.2em;
}
</style>
