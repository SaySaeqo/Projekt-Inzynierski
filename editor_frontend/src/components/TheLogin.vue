<template>
  <div>
    <form @submit.prevent="submitForm(username, password)">
      <div>
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script lang="ts">
export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    submitForm(username: string, password: string) {
      // Handle form submission here
      console.log(`Username: ${username}, Password: ${password}`);
    },
  },
};
</script>

<style lang="scss" scoped>
form {
  display: flex;
  flex-direction: column;
  width: 50%;
  margin: 0 auto;
}
div {
  display: flex;
  flex-direction: column;
  margin-bottom: 1em;
}
label {
  margin-bottom: 0.5em;
}
input {
  padding: 0.5em;
  border: 1px solid black;
}
</style>
