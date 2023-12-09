<template>
  <li>
    <router-link :to="'/exhibit/' + id">
      <div>
        <p>{{ id }}</p>
      </div>
      <div>
        <p>{{ name }}</p>
      </div>
    </router-link>
    <button @click="$emit('remove')">REMOVE</button>
  </li>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    name: String,
    id: String,
  },
});
</script>

<style lang="scss" scoped>
@use "../styles/colors";

a {
  display: flex;
  flex: 1;
  text-decoration: none;
  color: black;
}

a:hover {
  background-color: colors.$hover;
}

div {
  display: flex;
  justify-content: flex-start;
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

button {
  align-self: stretch;
}

</style>
