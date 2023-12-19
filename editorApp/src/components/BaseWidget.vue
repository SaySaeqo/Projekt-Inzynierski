<template>
  <div class="container">
    <WidgetGallery
      v-if="widget.type === 'gallery'"
      :widget="widget"
      :changes="changes"
    />
    <WidgetText
      v-if="widget.type === 'text'"
      :widget="widget"
    />
    <WidgetLink
      v-if="widget.type === 'link'"
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
import { Widget } from "@/models/Exhibit";
import { defineComponent, PropType } from "vue";
import WidgetGallery from "./WidgetGallery.vue";
import WidgetText from "./WidgetText.vue";
import WidgetLink from "./WidgetLink.vue";

export default defineComponent({
  props: {
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
  position: absolute;
  right: 0;
  top: 0;
}

.container {
  display: flex;
  position: relative;
}
</style>
