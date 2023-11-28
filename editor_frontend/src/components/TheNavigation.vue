<template>
  <nav>
    <ul>
      <li>
        <router-link to="/">List</router-link>
      </li>
      <li>
        <router-link to="/login">Login</router-link>
      </li>
    </ul>
  </nav>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({});
</script>

<style lang="scss" scoped>
@use "../styles/colors";

ul {
  margin: 0;
  padding: 0;
  list-style: none;
  display: flex;
}
li {
  display: flex;
}
a {
  padding: 1em;
  flex: 1;
  text-decoration: none;
  color: black;
}
a:hover {
  background-color: colors.$hover;
}
</style>
