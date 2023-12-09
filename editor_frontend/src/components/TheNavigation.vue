<template>
  <nav>
    <ul>
      <li>
        <router-link to="/">List</router-link>
      </li>
      <li>
        <div  v-if="isLoggedIn()" class="logout">
          <a @click="logout">Logout</a>
          <p>Logged in as {{ store.state.username }}</p>
        </div>
        <router-link v-else to="/login">Login</router-link>
      </li>
    </ul>
  </nav>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useStore } from "vuex";
import { getAuth, signOut } from "firebase/auth";

export default defineComponent({
  methods: {
    async logout() {
      const auth = getAuth();
      try {
        await signOut(auth);
        this.store.commit("setUsername", "");
        this.$router.push("/login");
      } catch (error) {
        console.error(error);
      }
    },
    isLoggedIn() {
      return this.store.state.username !== "";
    },
  },
  setup() {
    const store = useStore();
    return { store };
  },
});
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
  cursor: pointer;
}

.logout {
  display: flex;
  align-items: stretch;
  gap: 1em;
}
</style>

