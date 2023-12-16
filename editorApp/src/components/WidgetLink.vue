<template>
  <div class="container2">
    <div @click="edit" class="main">
      <img :src="icon" />
      <p>{{ widget.title}}</p>
    </div>
    <div :class="'editor ' + hidden">
      <input type="text" v-model="widget.title" />
      <input type="text" v-model="widget.data" />
      <!-- <input type="file" @change="handleFileUpload" /> -->
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Exhibit, Widget } from "@/models/Exhibit";

export default defineComponent({
  props: {
    exhibit: {
      type: Exhibit,
      required: true,
    },
    widget: {
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
    this.title = this.widget.data + "text";
    // get link from firebase
    this.link = "https://www.google.com";
  },
  methods: {
    edit() {
      if (this.hidden == "hidden") this.hidden = "";
      else this.hidden = "hidden";
    },
    handleFileUpload(event: Event) {
      const file = (event.target as HTMLInputElement).files?.[0];
      // Now you can do whatever you want with the file...
      // example
      if (file) {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.icon = reader.result as string;
        };
      }
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
  height: 3em;
  padding: 1em;
  display: block;
  transition: opacity 0.3s ease;
  object-fit: cover;
  object-position: center;
  width: 3em;
  height: 100%;
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
