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
      </div>
      <div class="preview">
        <WidgetGallery />
        <WidgetSection />
        <WidgetLink />
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

export default defineComponent({
  components: {
    WidgetGallery,
    WidgetSection,
    WidgetLink,
  },
  computed: {
    exhibit() {
      return {
        id: useRoute().params.id,
        name: "Intel",
        description:
          "Intel Corporation is an American multinational corporation and technology company headquartered in Santa Clara, California, in Silicon Valley.",
        image:
          "https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Intel_logo_%282006-2020%29.svg/1200px-Intel_logo_%282006-2020%29.svg.png",
        link: "https://en.wikipedia.org/wiki/Intel",
      };
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
