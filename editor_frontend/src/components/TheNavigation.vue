<template>
  <nav>
    <ul>
      <li>
        <router-link to="/">Home</router-link>
      </li>
      <li>
        <router-link to="/about">About</router-link>
      </li>
    </ul>
  </nav>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "TheNavigation",
});
</script>

<style lang="scss" scoped>
ul {
  display: flex;
  margin: 0;
  padding: 0;
  list-style: none;
}
li {
  margin: 1em;
}
</style>
