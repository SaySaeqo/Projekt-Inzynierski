<template>
  <div class="container2">
    <div @click="edit" class="main">
      <img :src="icon" />
      <p>{{ title}}</p>
    </div>
    <div :class="'editor ' + hidden">
      <input type="text" v-model="title" />
      <input type="text" v-model="link" />
      <input type="file" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Exhibit, Widget } from "@/models/Exhibit";
import TheNavigation from "./TheNavigation.vue";

export default defineComponent({
  props: {
    exhibit: {
      type: Exhibit,
      required: true,
    },
    data: {
      type: Widget,
      required: true,
    },
  },
  data() {
    return {
      icon: "",
      title: "",
      link: "",
      hidden: "hidden",
    };
  },
  beforeMount() {
    // get url from firebase
    this.icon = "img/exampleLink.png";
    // get title from firebase
    this.title = this.data.data + "text";
    // get link from firebase
    this.link = "https://www.google.com";
  },
  methods: {
    edit() {
      if (this.hidden == "hidden") this.hidden = "";
      else this.hidden = "hidden";
    },
  },
});
</script>

<style lang="scss" scoped>
.main {
  display: flex;
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
.main:hover {
  background-color: #f0f0f0;
  cursor: pointer;
}
.editor {
  display: flex;
  flex-direction: column;
}

.hidden {
  display: none;
}

.container2 {
  display: flex;
  flex-direction: column;
  border: 1px solid black;
  flex: 1;
  justify-content: flex-start;
  align-items: stretch;
  height: fit-content;
}
</style>
