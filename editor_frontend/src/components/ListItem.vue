<template>
  <li @click="handleClick" class="item">
    <div>
      <p>{{ id }}</p>
    </div>
    <div>
      <p>{{ name }}</p>
    </div>
  </li>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "ListItem",
  props: {
    name: String,
    id: Number,
  },

  methods: {
    handleClick() {
      console.log(`click ${this.id}:${this.name}`);
      //   this.$emit("click", this.id);
    },
  },
});
</script>

<style lang="scss" scoped>
li {
  display: flex;

  div {
    display: flex;
    justify-content: start;
    align-items: center;
    flex: 2;
    border: 1px solid black;
  }

  div:first-child {
    flex: 1;
  }

  p {
    margin: 1em;
    padding: 0;
  }
}
</style>
