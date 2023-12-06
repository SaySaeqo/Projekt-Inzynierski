<template>
  <main>
    <section>
      <ul>
        <p>id: {{ exhibit.id }}</p>
        <div class="name">
          <label for="name">Name:</label>
          <input id="name" type="text" v-model="exhibit.name" class="name" />
        </div>
        
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
        <BaseWidget v-for="widget in exhibit.widgets" :key="widget.data"
          :exhibit="exhibit" :widget="widget"
          @up="moveWidgetUp(widget)"
          @down="moveWidgetDown(widget)" 
          @remove="removeWidget(widget)" />
      </div>
    </section>
  </main>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import {Exhibit, Widget} from "@/models/Exhibit";
import BaseWidget from "./BaseWidget.vue";
import { useRoute } from "vue-router";

export default defineComponent({
  components: {
    BaseWidget
},
  data() {
    return {
      exhibit: new Exhibit(),
      generatedId: 0,
    };
  },
  methods: {
    addGallery() {
      this.exhibit.widgets.push({
        id: this.generatedId++,
        type: "gallery",
        data: ""
      });
    },
    addParagraph() {
      this.exhibit.widgets.push({
        id: this.generatedId++,
        type: "paragraph",
        data: ""
      });
    },
    addLink() {
      this.exhibit.widgets.push({
        id: this.generatedId++,
        type: "link",
        data: ""
      });
    },
    moveWidgetUp(widget: Widget) {
      let index = this.exhibit.widgets.indexOf(widget);
      if (index > 0) {
        this.exhibit.widgets.splice(index, 1);
        this.exhibit.widgets.splice(index - 1, 0, widget);
      }
    },
    moveWidgetDown(widget: Widget) {
      let index = this.exhibit.widgets.indexOf(widget);
      if (index < this.exhibit.widgets.length - 1) {
        this.exhibit.widgets.splice(index, 1);
        this.exhibit.widgets.splice(index + 1, 0, widget);
      }
    },
    removeWidget(widget: Widget) {
      let index = this.exhibit.widgets.indexOf(widget);
      this.exhibit.widgets.splice(index, 1);
    },
  },
  beforeMount() {
    let id = useRoute().params.id;
    // this.exhibit = fromDb(id);
    this.exhibit.id = id as string;
    this.generatedId = this.exhibit.widgets.length;
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
  justify-content: center;
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
  display: flex;
  flex: 1;
  align-items: center;
  padding: 0 1.5em;
  gap: 1em;

  label {
    font-weight: bold;
  }
  input {
    flex: 1;
    align-self: stretch;
    font-size: 1.2em;
  }
}

button {
  padding: 1em;
}
.preview {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 1em;
  align-items: stretch;
  border: 1px solid black;
  padding: 1em;
  width: 30em;
  height: 40em;
  overflow: auto;
}

.tools {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  padding: 1em;
}
</style>
