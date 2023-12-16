<template>
  <nav>
    <ul>
      <li>
        <router-link to="/">List</router-link>
      </li>
      <li>
        <div v-if="isLoggedIn" class="logout">
          <a @click="logout">Logout</a>
          <p>Logged in as {{ username }}</p>
        </div>
        <router-link v-else to="/login">Login</router-link>
      </li>
    </ul>
  </nav>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import { getAuth, signOut, onAuthStateChanged } from "firebase/auth";

export default defineComponent({
  data() {
    return {
      user: getAuth().currentUser,
    };
  },
  beforeMount() {
    const _this = this;
    onAuthStateChanged(getAuth(), newUser => _this.user = newUser);
  },
  computed: {
    username(): string {
      return this.user?.displayName || this.user?.email || "anonymous";
    },
    isLoggedIn(): boolean {
      return this.user !== null;
    },
  },
  methods: {
    async logout() {
      try {
        await signOut(getAuth());
        this.$router.push("/login");
      } catch (error) {
        console.error(error);
      }
    },
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
