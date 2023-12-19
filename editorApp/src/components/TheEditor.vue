<template>
  <main v-if="dataLoaded">
    <section class="top">
        <p>id: {{ exhibit.id }}</p>
        <div class="name">
          <label for="name">Name:</label>
          <input id="name" type="text" v-model="exhibit.name" class="name" />
        </div>
        <div class="saveContainer">
          <button @click="saveExhibit">Save</button>
          <transition name="fade">
            <div v-if="showPopup" class="popup">Saved!</div>
          </transition>
        </div>
    </section>
    <section class="bottom">
      <div class="tools">
        <button @click="consoleLog">Console log</button>
        <button @click="addWidget('gallery')">Add Gallery</button>
        <button @click="addWidget('text')">Add Text</button>
        <button @click="addWidget('link')">Add Link</button>
        <EditorImageUpload v-model:file="iconFile" name="icon" :src="iconSrc" v-model:removed="iconRemoved" />
        <EditorImageUpload v-model:file="locationFile" name="location" :src="locationSrc" v-model:removed="locationRemoved" />
      </div>
      <div class="preview">
        <BaseWidget
          v-for="widget in exhibit.widgets"
          :key="widget.id"
          :exhibit="exhibit"
          :widget="widget"
          :changes="widgetChanges"
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
      widgetChanges: new Map<number, Map<string,Object[]>>(),
      widgetRemoved: [] as Widget[],
      generatedId: 0,
      maxDbWidgetId: 0,
      extra_key: "",
      extra_value: "",
      errorMsg: "",
      iconFile: null as File | null,
      iconSrc: "",
      locationFile: null as File | null,
      locationSrc: "",
      dataLoaded: false,
      showPopup: false,
      iconRemoved: false,
      locationRemoved: false,
    };
  },
  async created() {
    const id = useRoute().params.id as string;
    await this.getExhibit(id);
    this.iconSrc = await dataService.getImage(this.exhibit.icon).catch(() => "");
    this.locationSrc = await dataService
      .getImage(this.exhibit.location)
      .catch(() => "");
    
    this.exhibit.widgets.forEach((widget, index) => widget.id = index);
    this.generatedId = this.exhibit.widgets.length;
    this.maxDbWidgetId = this.exhibit.widgets.length - 1;
    for (const widget of this.exhibit.widgets) {
      this.widgetChanges.set(widget.id, new Map<string,Object[]>());
    }
    this.dataLoaded = true;
  },
  methods: {
    consoleLog() {
      console.log(this.exhibit);
    },
    addWidget(type: string) {
      this.widgetChanges.set(this.generatedId, new Map<string,Object[]>());
      this.exhibit.widgets.push(new Widget(this.generatedId++, type));
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
      if (widget.id <= this.maxDbWidgetId) this.widgetRemoved.push(widget);
      this.widgetChanges.delete(widget.id);
      
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
        } else if (this.iconRemoved) {
          await dataService.removeImage(this.iconSrc);
          this.exhibit.icon = "";
        }
        if (this.locationFile) {
          const name = await dataService.updateImage(
            this.exhibit.id,
            this.locationFile,
            this.locationSrc
          );
          this.exhibit.location = name;
        } else if (this.locationRemoved) {
          await dataService.removeImage(this.locationSrc);
          this.exhibit.location = "";
        }


        for (let removed = this.widgetRemoved.pop(); removed; removed = this.widgetRemoved.pop()) {
          dataService.removeAllImages(removed);
        }
        
        for (const key of this.widgetChanges.keys()) {
          const gallery = this.widgetChanges.get(key) as Map<string, Object[]>;
          const added = gallery.get("added") as File[] || [];
          const removed = gallery.get("removed") as string[] || [];

          for(const file of added) {
            const name = await dataService.addImage(this.exhibit.id, file);
            this.exhibit.widgets.find(widget => widget.id === key)!.addImageURL(name);
          }

          for (const image of removed) {
            if (! await dataService.removeImage(image)) continue;
            this.exhibit.widgets.find(widget => widget.id === key)!.removeImageURL(dataService.getNameFromLink(image) as string);
          }
        }
        await dataService.update(this.exhibit);

        this.$router.go(0);

        // this.showPopup = true;
        // setTimeout(() => {
        //   this.showPopup = false;
        // }, 500); // Change this to control how long the popup is visible
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

  .saveContainer {
    position: relative;
    
    button {
      display: block;
      width: 100%;
      height: 100%;
    }

    .popup {
      position: absolute;
      /* Adjust these to position the popup below the save button */
      top: 100%;
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: #eeeeeebb;
      border: 1px solid black;
      border-radius: 0.5em;
      padding-top: 0.5em;
      margin-top: 0.5em;
    }
  }
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

.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
