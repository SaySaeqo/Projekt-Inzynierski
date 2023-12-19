<template>
  <div class="container2">
    <div class="main">
      <img :src="gallery[0] || ''" class="big" />
      <div class="gallery">
        <img src="../assets/plus.png" @click="add" class="plus" />
        <div class="item-wrapper" v-for="image in gallery" :key="image" @click="remove(image)">
          <img class="item" :src="image" />
          <img class="bin-icon" src="../assets/bin.png" />
        </div>
      </div>
      <input type="file" ref="fileInput" @change="handleFileUpload" style="display: none" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType, watch } from "vue";
import { Exhibit, Widget } from "@/models/Exhibit";
import dataService from "../services/DataService";

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
    changes: {
      type: Object as PropType<Map<number, Map<string,Object[]>>>,
      required: true,
    },
  },
  data() {
    return {
      gallery: [] as string[],
      addedUrls: new Map<string, File>(),
    };
  },
  async created() {
    for (const image of this.widget.imagesURLs) {
      this.gallery.push(await dataService.getImage(image));
    }
  },
  unmounted() {
    for (const url of this.addedUrls.keys()) {
      URL.revokeObjectURL(url);
    }
  },
  methods: {
    remove(image: string) {
      const myChanges = this.changes.get(this.widget.id)!;

      // if added in this session, remove from added
      const added = myChanges.get("added") as File[] ?? [] as File[];
      const file = this.addedUrls.get(image);
      if (file) {
        added.splice(added.indexOf(file), 1);
        myChanges.set("added", added);
        this.gallery.splice(this.gallery.indexOf(image), 1);
        URL.revokeObjectURL(image);
        this.addedUrls.delete(image);
        return;
      }

      const removed = myChanges.get("removed") ?? [] as string[];
      removed.push(image);
      myChanges.set("removed", removed);
      this.gallery.splice(this.gallery.indexOf(image), 1);
    },
    add() {
      (this.$refs.fileInput as HTMLInputElement).click();
    },
    handleFileUpload(event: Event) {
      const file = (event.target as HTMLInputElement).files?.[0];
      if (!file) return;

      const myChanges = this.changes.get(this.widget.id)!;
      const added = myChanges.get("added") ?? [] as File[];
      added.push(file);
      myChanges.set("added", added);
      
      const url = URL.createObjectURL(file);
      this.gallery.push(url);
      this.addedUrls.set(url, file);
    },
  },
});
</script>

<style lang="scss" scoped>
.main {
  display: flex;
  flex-direction: column;
  height: 20em;
  padding: 1em;
  gap: 1em;
}

img {
  object-fit: contain;
  max-height: 3em;
  max-width: 100%;
}
.big {
  max-height: 15em;
  min-height: 15em;
}

.gallery {
  flex: 1;
  display: flex;
  overflow: auto;
  width: 22em;
}

.item-wrapper {
  position: relative;
  display: inline-block;
  max-width: 3em;
}

.item {
  display: block;
  transition: opacity 0.3s ease;
  object-fit: cover;
  object-position: center;
  width: 3em;
  height: 100%;
}

.bin-icon {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  object-fit: contain;
}

.item-wrapper:hover .item {
  opacity: 0.3;
}

.item-wrapper:hover .bin-icon {
  opacity: 1;
  cursor: pointer;
}

img.plus {
  opacity: 0.6;
  padding: 0.5em;
  box-sizing: border-box;
}

img.plus:hover {
  opacity: 1;
  cursor: pointer;
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
