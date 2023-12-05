<template>
  <main>
    <section>
      <ul>
        <p>id: {{ exhibit.id }}</p>
        <p class="name">name: {{ exhibit.name }}</p>
        <button>Save</button>
      </ul>
    </section>
    <section>
      <div class="tools">
        <h1>Tools:</h1>
        <button @click="addGallery">Add Gallery</button>
        <button @click="addParagraph">Add Paragraph</button>
        <button @click="addLink">Add Link</button>
      </div>
      <div class="preview">
        <template v-for="widget in exhibit.widgets" :key="widget.data">
          <WidgetGallery v-if="widget.type === 'gallery'" />
          <WidgetSection v-if="widget.type === 'paragraph'" />
          <WidgetLink v-if="widget.type === 'link'" />
        </template>
      </div>
    </section>
  </main>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useRoute } from "vue-router";
import WidgetGallery from "./WidgetGallery.vue";
import WidgetSection from "./WidgetSection.vue";
import WidgetLink from "./WidgetLink.vue";
import {Exhibit} from "@/models/Exhibit";

export default defineComponent({
  components: {
    WidgetGallery,
    WidgetSection,
    WidgetLink,
  },
  data() {
    return {
      exhibit: new Exhibit()
    };
  },
  methods: {
    addGallery() {
      this.exhibit.widgets.push({
        type: "gallery",
        data: ""
      });
    },
    addParagraph() {
      this.exhibit.widgets.push({
        type: "paragraph",
        data: ""
      });
    },
    addLink() {
      this.exhibit.widgets.push({
        type: "link",
        data: ""
      });
    },
  },
  beforeMount() {
    // let id = useRoute().params.id
    // this.exhibit = fromDb(id);
  },

});
</script>

<style lang="scss" scoped>
main {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  border: 1px solid black;
}
section {
  display: flex;
}
ul {
  flex: 1;
  display: flex;
  margin: 0;
  padding: 0;
  list-style: none;
}
p {
  margin: 0;
  padding: 1em;
  text-align: center;
}

.name {
  font-weight: bold;
  flex: 1;
}

button {
  padding: 1em;
}
.preview {
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 1em;
  align-items: stretch;
  border: 1px solid black;
  padding: 1em;
}

.tools {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  padding: 1em;
}
</style>
