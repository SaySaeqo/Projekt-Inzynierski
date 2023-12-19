<template>
  <div class="container">
    <WidgetGallery
      v-if="widget.type === 'gallery'"
      :exhibit="exhibit"
      :widget="widget"
      :changes="changes"
    />
    <WidgetText
      v-if="widget.type === 'text'"
      :exhibit="exhibit"
      :widget="widget"
    />
    <WidgetLink
      v-if="widget.type === 'link'"
      :exhibit="exhibit"
      :widget="widget"
    />
    <div class="control">
      <button @click="$emit('up')">UP</button>
      <button @click="$emit('down')">DOWN</button>
      <button @click="$emit('remove')">REMOVE</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Exhibit, Widget } from "@/models/Exhibit";
import { defineComponent, PropType } from "vue";
import WidgetGallery from "./WidgetGallery.vue";
import WidgetText from "./WidgetText.vue";
import WidgetLink from "./WidgetLink.vue";

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
  components: { WidgetGallery, WidgetText, WidgetLink },
});
</script>

<style lang="scss" scoped>
.control {
  display: flex;
  flex-direction: column;
  align-self: stretch;
  flex-basis: auto;
  position: absolute;
  right: 0;
  top: 0;
}

.container {
  display: flex;
  position: relative;
}
</style>
