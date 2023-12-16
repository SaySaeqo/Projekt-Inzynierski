<template>
  <main v-if="dataLoaded">
    <section class="top">
        <p>id: {{ exhibit.id }}</p>
        <div class="name">
          <label for="name">Name:</label>
          <input id="name" type="text" v-model="exhibit.name" class="name" />
        </div>
        <button @click="saveExhibit">Save</button>
    </section>
    <section class="bottom">
      <div class="tools">
        <button @click="consoleLog">Console log</button>
        <button @click="addGallery">Add Gallery</button>
        <button @click="addText">Add Text</button>
        <button @click="addLink">Add Link</button>
        <EditorImageUpload v-model="iconFile" name="icon" :src="iconSrc" />
        <EditorImageUpload v-model="locationFile" name="location" :src="locationSrc" />
      </div>
      <div class="preview">
        <BaseWidget
          v-for="widget in exhibit.widgets"
          :key="widget.id"
          :exhibit="exhibit"
          :widget="widget"
          @up="moveWidgetUp(widget)"
          @down="moveWidgetDown(widget)"
          @remove="removeWidget(widget)"
        />
      </div>
      <EditorExtra v-model="exhibit.extra" />
    </section>
  </main>
  <main v-else>
    <p>Loading...</p>
  </main>
</template>

<script lang="ts">
import { defineComponent, nextTick } from "vue";
import BaseWidget from "./BaseWidget.vue";
import { useRoute } from "vue-router";
import dataService from "../services/DataService";
import EditorExtra from "./EditorExtra.vue";
import EditorImageUpload from "./EditorImageUpload.vue";
import { ExhibitPair, Widget, Exhibit } from "@/models/Exhibit";
export default defineComponent({
  components: {
    BaseWidget,
    EditorExtra,
    EditorImageUpload,
  },
  data() {
    return {
      exhibit: new Exhibit(),
      generatedId: 0,
      extra_key: "",
      extra_value: "",
      errorMsg: "",
      iconFile: null as File | null,
      iconSrc: "",
      locationFile: null as File | null,
      locationSrc: "",
      dataLoaded: false,
    };
  },
  async created() {
    const id = useRoute().params.id as string;
    await this.getExhibit(id);
    this.iconSrc = await dataService.getImage(this.exhibit.icon).catch(() => {
      return "";
    });
    this.locationSrc = await dataService
      .getImage(this.exhibit.location)
      .catch(() => {
        return "";
      });
    this.generatedId = this.exhibit.widgets.length;
    this.dataLoaded = true;
  },
  methods: {
    consoleLog() {
      console.log(this.exhibit);
    },
    addGallery() {
      this.exhibit.widgets.push(new Widget(this.generatedId++, "gallery"));
    },
    addText() {
      this.exhibit.widgets.push(new Widget(this.generatedId++, "text"));
    },
    addLink() {
      this.exhibit.widgets.push(new Widget(this.generatedId++, "link"));
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
      dataService.removeAllImages(widget);
    },
    async getExhibit(id: string) {
      this.exhibit = await dataService.getOne(id);
    },
    async saveExhibit() {
      if (!this.exhibit.name) this.errorMsg = "Exhibit has to have a name";
      else {
        this.exhibit.description = "";
        if (this.iconFile) {
          const name = await dataService.updateImage(
            this.exhibit.id,
            this.iconFile,
            this.iconSrc
          );
          this.exhibit.icon = name;
        }
        if (this.locationFile) {
          const name = await dataService.updateImage(
            this.exhibit.id,
            this.locationFile,
            this.locationSrc
          );
          this.exhibit.location = name;
        }
        dataService.update(this.exhibit);
      }
    },
  },
});
</script>

<style lang="scss" scoped>
main {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  border: 1px solid black;
  gap: 1em;
  padding: 1em;
}
section {
  display: grid;
}
.top {
  grid-template-columns: 1fr 4fr 1fr;
  justify-items: stretch;
}

.bottom {
  grid-template-columns: 2fr 3fr 2fr;
  justify-items: center;
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
.preview {
  justify-self: center;
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

  button {
    padding: 1em;
  }
}
</style>
